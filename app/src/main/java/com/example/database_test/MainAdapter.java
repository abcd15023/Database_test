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

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainAdapter extends BaseAdapter<MainAdapter.ViewHolder> {

    private List<nianhui_info> mDataList;

    public MainAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<nianhui_info> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item1,parent,false);
        ViewHolder holder = new ViewHolder(view);

        //RecyclerView下的 行布局控件 都可以单独做点击事件
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mDataList.get(position);
                Toast.makeText(v.getContext(),String.valueOf(nh.getId()),Toast.LENGTH_SHORT).show();
            }
        });
        holder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mDataList.get(position);
                Toast.makeText(v.getContext(),nh.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mDataList.get(position);
                Toast.makeText(v.getContext(),nh.getSize(),Toast.LENGTH_SHORT).show();
            }
        });
        //return new ViewHolder(getInflater().inflate(R.layout.item1, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.setData(mDataList.get(position));

        nianhui_info nh = mDataList.get(position);
        Log.i("zun","1");
        String str = String.valueOf(nh.getId());
        holder.text1.setText(str);
        Log.i("zun","2");
        holder.text2.setText(nh.getName());
        holder.text3.setText(nh.getSize());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        TextView tvTitle;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//        }

        TextView text1,text2,text3;
        //构造函数传入View参数，
        public ViewHolder(View view){
            super(view);
            text1 = view.findViewById(R.id.item1_text1);
            text2 = view.findViewById(R.id.item1_text2);
            text3 = view.findViewById(R.id.item1_text3);
        }

        public void setData(String title) {
            this.text1.setText(title);
            this.text2.setText(title);
            this.text3.setText(title);

        }
    }
    public void clearList(){
        mDataList.clear();
    }
}