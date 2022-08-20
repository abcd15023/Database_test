/*
 * Copyright 2016 Yan Zhenjie
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
package com.example.database_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.database_test.utils.Utils;
import com.yanzhenjie.recyclerview.touch.OnItemMoveListener;

import java.util.Collections;

/**
 * <p>
 * 拖拽Item + 侧滑删除，默认侧滑删除只支持List形式。
 * </p>
 * Created by Yan Zhenjie on 2016/8/3.
 */
public class DragSwipeListActivity extends BaseDragActivity {

    View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mHeaderView = getLayoutInflater().inflate(R.layout.layout_header_switch, mRecyclerView, false);
//        mRecyclerView.addHeaderView(mHeaderView);
//
//        SwitchCompat switchCompat = mHeaderView.findViewById(R.id.switch_compat);
//        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // 控制是否可以侧滑删除。
//                mRecyclerView.setItemViewSwipeEnabled(isChecked);
//            }
//        });

        mRecyclerView.setLongPressDragEnabled(true); // 长按拖拽，false关闭,true打开。
        mRecyclerView.setItemViewSwipeEnabled(false); // 滑动删除，false关闭,true打开。
        Log.i("zun","DragSwipeListActivity");
    }

    @Override   //拖拽排序监听
    protected OnItemMoveListener getItemMoveListener() {
        // 监听拖拽和侧滑删除，更新UI和数据源。
        return new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                // 不同的ViewType不能拖拽换位置。
                if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

                // 真实的Position：通过ViewHolder拿到的position都需要减掉HeadView的数量。
                int fromPosition = srcHolder.getAdapterPosition() - mRecyclerView.getHeaderCount();
                int toPosition = targetHolder.getAdapterPosition() - mRecyclerView.getHeaderCount();
                Collections.swap(Utils.mDataList, fromPosition, toPosition);
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                Log.i("zunxxx","OnItemMoveListener()："+Utils.mDataList);

                //DragChangeDb();//拖动操作直接改变数据库，但是这里不能调用，因为拖拽每跨过一个item都会调用DragChangeDb()方法，造成重复错误插入数据

                return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
                int adapterPosition = srcHolder.getAdapterPosition();
                int position = adapterPosition - mRecyclerView.getHeaderCount();

                if (mRecyclerView.getHeaderCount() > 0 && adapterPosition == 0) { // HeaderView。
                    mRecyclerView.removeHeaderView(mHeaderView);
                    Toast.makeText(DragSwipeListActivity.this, "HeaderView被删除。", Toast.LENGTH_SHORT).show();
                } else { // 普通Item。
                    Utils.mDataList.remove(position);
                    mAdapter.notifyItemRemoved(position);
                    Toast.makeText(DragSwipeListActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}