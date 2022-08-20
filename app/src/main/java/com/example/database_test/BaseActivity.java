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
package com.example.database_test;

import android.Manifest;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.database_test.adapter.MainAdapter;
import com.example.database_test.bean.NianHuiBean;
import com.example.database_test.db.DatabaseHelper;
import com.example.database_test.weight.PopMain;
import com.example.database_test.utils.Utils;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.List;

/**
 * Created by YanZhenjie on 2017/7/21.
 */
public class BaseActivity extends AppCompatActivity implements OnItemClickListener {

    protected Toolbar mToolbar;
    protected ActionBar mActionBar;
    protected SwipeRecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;
    protected BaseAdapter mAdapter;

    private EditText mGlobalSearchEt;
    private LinearLayout mActivityMainLinearLayout;
    private ImageView mClearIv,mAddIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //在程序中动态获取写入权限，此操作将向系统索要权限，系统敏感权限有弹窗
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        //获取ReyeclerView对象
        mRecyclerView = findViewById(R.id.recycler_view);

        initView();
        Utils.initDatabase(this);

        mLayoutManager = createLayoutManager();
        mItemDecoration = createItemDecoration();
        //mDataList = createDataList();
        mAdapter = createAdapter();
        Utils.mAdapter = mAdapter;

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);

        //创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        Utils.dbNullReplace();
        Utils.getdbMaxId();

        mGlobalSearchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mActivityMainLinearLayout.setVisibility(View.GONE);
                }
            }
        });

        mGlobalSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String newText = String.valueOf(s);
                Utils.mTempSql = Utils.getSql(newText); //用于滑动菜单删除时更新列表，需要这个全局变量
                if(!newText.trim().isEmpty()){
                    Utils.mTempNewText = newText;
                    //如果字符串去掉前后空格不为空 则调用搜索
                    Utils.dbETSearch(Utils.getSql(newText));
                }else{
                    //搜索框为空则清空mDataList
                    Utils.mTempSql = Utils.getSql(""); //在搜索框为空时清空tempNewText,避免别处调用dbETSearch(BaseActivity.tempNewText)空指针
                    if(Utils.mDataList != null){
                        Utils.mTempNewText = null;
                        Utils.mDataList.clear();
                        mAdapter.notifyDataSetChanged(Utils.mDataList);
                    }
                }
            }
        });
        mClearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGlobalSearchEt.setText("");
            }
        });
        mAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义下拉弹出窗口
                PopMain pop = new PopMain(BaseActivity.this);
                pop.showPopupWindow(mAddIv);
            }
        });
    }

    protected int getContentView() {
        return R.layout.activity_base;
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
    }


    protected BaseAdapter createAdapter() {
        return new MainAdapter(this);
    }

    @Override  //item行的点击事件
    public void onItemClick(View itemView, int position) {
        //Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    protected boolean displayHomeAsUpEnabled() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    private void initView(){
        mGlobalSearchEt = findViewById(R.id.global_search_et);
        mClearIv = findViewById(R.id.clear_iv);
        mAddIv = findViewById(R.id.add_iv);
        mActivityMainLinearLayout = findViewById(R.id.activity_main_linear_layout);
    }
}