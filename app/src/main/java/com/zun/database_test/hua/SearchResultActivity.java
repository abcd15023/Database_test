package com.zun.database_test.hua;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.database_test.R;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;
import com.zun.database_test.BaseActivity;
import com.zun.database_test.BaseAdapter;
import com.zun.database_test.adapter.MainAdapter;
import com.zun.database_test.utils.Utils;
import com.zun.database_test.weight.PopMain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
 *Author: Zun
 *Date: 2022-08-20 16:01
 *Description: 测试，合并状态的Activity
 */public class SearchResultActivity extends Activity {

    private SwipeRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.ItemDecoration mItemDecoration;
    private BaseAdapter mAdapter;

    private EditText mGlobalSearchEt;
    private ImageView mClearIv;
    private ImageView mAddIv;

    private TextView mAppNameTv;
    private TextView mAuthorTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //在程序中动态获取写入权限，此操作将向系统索要权限，系统敏感权限有弹窗
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        initView();//初始化View
        initData();//初始化数据
        initEvent();//初始化事件，如点击事件
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mGlobalSearchEt = findViewById(R.id.global_search_et);
        mClearIv = findViewById(R.id.clear_iv);
        mAddIv = findViewById(R.id.add_iv);
        mAppNameTv = findViewById(R.id.activity_main_app_name_tv);
        mAuthorTv = findViewById(R.id.activity_main_author_tv);

        mLayoutManager = new LinearLayoutManager(this);//创建一个LinearLayoutManager对象（线性布局），设置到ReyeclerView对象中
        mItemDecoration = new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
        mAdapter = new MainAdapter(this);

        //设置mRecyclerView参数
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
    }

    private void initData() {
        Utils.initDatabase(this);
        Utils.mAdapter = mAdapter;
        Utils.dbNullReplace();
        Utils.getdbMaxId();
    }

    private void initEvent() {
        mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {
                //Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });

        mGlobalSearchEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideAboutTv();
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
                if (!newText.trim().isEmpty()) {
                    Utils.mTempNewText = newText;
                    //如果字符串去掉前后空格不为空 则调用搜索
                    Utils.dbETSearch(Utils.getSql(newText));
                } else {
                    //搜索框为空则清空mDataList
                    Utils.mTempSql = Utils.getSql(""); //在搜索框为空时清空tempNewText,避免别处调用dbETSearch(BaseActivity.tempNewText)空指针
                    if (Utils.mDataList != null) {
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
                PopMain pop = new PopMain(SearchResultActivity.this);
                pop.showPopupWindow(mAddIv);
            }
        });
    }

    private void hideAboutTv() {
        mAppNameTv.setVisibility(View.GONE);
        mAuthorTv.setVisibility(View.GONE);
    }

}
