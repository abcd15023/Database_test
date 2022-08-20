/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zun.database_test;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_test.R;
import com.zun.database_test.ui.EditActivity;
import com.zun.database_test.utils.Utils;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.touch.OnItemStateChangedListener;

import java.util.Collections;

/**
 * <p>
 * Item拖拽排序，Item侧滑删除基础通用。
 * </p>
 * Created by YanZhenjie on 2017/7/22.
 */
public abstract class BaseDragActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener); // Item的Menu点击。
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator); // 菜单创建器。

        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged(mDataList);

        mRecyclerView.setOnItemMoveListener(getItemMoveListener());// 监听拖拽和侧滑删除，更新UI和数据源。
        mRecyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener); // 监听Item的手指状态，拖拽、侧滑、松开。
    }

    protected abstract OnItemMoveListener getItemMoveListener();

    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
                //mActionBar.setSubtitle("状态：拖拽");

                // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景。
                viewHolder.itemView.setBackgroundColor(
                    ContextCompat.getColor(BaseDragActivity.this, R.color.white_pressed));
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
                //mActionBar.setSubtitle("状态：滑动删除");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
                //mActionBar.setSubtitle("状态：手指松开");

                Utils.delDragList(); //先删除当前搜索结果下的mDataList对应数据库的数据
                Utils.DragChangeDb(); //只在拖动松手后调用，把排好序的mDataList插入数据库
                Log.i("zunxxx","BaseDragActivity："+Utils.mDataList);

                // 在手松开的时候还原背景。
                ViewCompat.setBackground(viewHolder.itemView,
                    ContextCompat.getDrawable(BaseDragActivity.this, R.color.toumingse));
            }
        }
    };

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT; //如果自定义图片+文字高于item,则并不会与item同高，就需要自定义高度
            //int height = 100;  //自定义高度

            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(BaseDragActivity.this).setBackground(
                        R.drawable.selector_green)
                        //.setImage(R.drawable.ic_action_add)
                        .setText("复制")
                        .setWidth(width).setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。

//                SwipeMenuItem closeItem = new SwipeMenuItem(BaseDragActivity.this).setBackground(
//                    R.drawable.selector_red).setImage(R.drawable.ic_action_close).setWidth(width).setHeight(height);
//
//                swipeLeftMenu.addMenuItem(closeItem); // 添加一个按钮到左侧菜单。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem closeItem = new SwipeMenuItem(BaseDragActivity.this).setBackground(
                        R.drawable.selector_purple)
                        //.setImage(R.drawable.ic_action_close)
                        .setText("修改")
                        .setWidth(width).setHeight(height);
                swipeRightMenu.addMenuItem(closeItem);// 添加一个按钮到右侧侧菜单。

                SwipeMenuItem deleteItem = new SwipeMenuItem(BaseDragActivity.this).setBackground(
                        R.drawable.selector_red)
                        //.setImage(R.drawable.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            //右边菜单两个按钮的事件
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                //Toast.makeText(BaseDragActivity.this, "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                if (menuPosition == 0){
                    Intent intent = new Intent();
                    intent.setClass(BaseDragActivity.this, EditActivity.class);
                    int id = Utils.mDataList.get(position).getId();
                    String id2 = String.valueOf(id);
                    String remark = String.valueOf(Utils.mDataList.get(position).getRemark());//通过Position得到item在数据库的元素
                    String name = String.valueOf(Utils.mDataList.get(position).getName());
                    String size = String.valueOf(Utils.mDataList.get(position).getSize());
                    String sizePlus = String.valueOf(Utils.mDataList.get(position).getSizePlus());
                    String sellingPrice = String.valueOf(Utils.mDataList.get(position).getSellingPrice());
                    String purchasingPrice = String.valueOf(Utils.mDataList.get(position).getPurchasingPrice());
                    String time = String.valueOf(Utils.mDataList.get(position).getTime());
                    String supplier = String.valueOf(Utils.mDataList.get(position).getSupplier());
                    intent.putExtra("id",id2);
                    intent.putExtra("remark",remark);
                    intent.putExtra("name",name);
                    intent.putExtra("size",size);
                    intent.putExtra("sizePlus",sizePlus);
                    intent.putExtra("sellingPrice",sellingPrice);
                    intent.putExtra("purchasingPrice",purchasingPrice);
                    intent.putExtra("time",time);
                    intent.putExtra("supplier",supplier);
                    startActivity(intent);
                }else{
                    Utils.dbDelItem(position);
                    Utils.dbETSearch(Utils.mTempSql); //返回到上一界面后再调用一次搜索刷新list
                }
            }
            //左边菜单的按钮事件
            else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Utils.dbCopyItem(position);
                Utils.dbETSearch(Utils.mTempSql); //返回到上一界面后再调用一次搜索刷新list

                //复制项先是插在底部，位置是mDataList.size()-1，为了使其插在被复制项的下一行 position+1 ，等于是照搬拖拽排序的方法
                Collections.swap(Utils.mDataList, Utils.mDataList.size()-1, position+1);
                mAdapter.notifyItemMoved(Utils.mDataList.size()-1, position+1);
                Utils.delDragList();  //删除老表
                Utils.DragChangeDb(); //插入新表

                //Toast.makeText(BaseDragActivity.this, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                Log.i("zunxxx","复制按钮点击事件："+Utils.mDataList);
           }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}