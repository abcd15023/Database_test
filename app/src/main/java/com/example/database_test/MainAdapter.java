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
import android.widget.LinearLayout;
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
                inflate(R.layout.item3,parent,false);
        ViewHolder holder = new ViewHolder(view);

        //RecyclerView下的 行布局控件 都可以单独做点击事件
//        holder.text_id.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                nianhui_info nh = mDataList.get(position);
//                Toast.makeText(v.getContext(),String.valueOf(nh.getId()),Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.text_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mDataList.get(position);
                Toast.makeText(v.getContext(),nh.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.text_purchasingPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mDataList.get(position);
                Toast.makeText(v.getContext(),nh.getPurchasingPrice(),Toast.LENGTH_SHORT).show();
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
        //holder.text_id.setText(str);
        holder.text_remark.setText(nh.getRemark());
        holder.text_name.setText(nh.getName());
        holder.text_name.setVisibility(View.VISIBLE);
        holder.text_size.setText(nh.getSize());
        holder.text_size.setVisibility(View.VISIBLE);
        holder.text_sizePlus.setText(nh.getSizePlus());
        Log.i("zunq","text_name："+nh.getName()+",text_size："+nh.getSize()+",text_sizePlus："+nh.getSizePlus());

        holder.text_sellingPrice.setText(nh.getSellingPrice());
        holder.text_purchasingPrice.setText(nh.getPurchasingPrice());
        holder.text_time.setText(nh.getTime());
        holder.text_supplier.setText(nh.getSupplier());

        if(!nh.getRemark().isEmpty()){
            holder.text_remark.setVisibility(View.VISIBLE);
        }
        if(!nh.getSize().trim().isEmpty()){
            holder.text_size.setVisibility(View.VISIBLE);
        }
        if(!nh.getSizePlus().trim().isEmpty()){
            holder.text_sizePlus.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        TextView tvTitle;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//        }
        TextView text_id,text_remark,text_name,text_size,text_sizePlus,text_sellingPrice,text_purchasingPrice,text_time,text_supplier;

        //构造函数传入View参数，
        public ViewHolder(View view){
            super(view);

            text_remark = view.findViewById(R.id.item_remark);
            text_name = view.findViewById(R.id.item_name);
            text_size = view.findViewById(R.id.item_size);
            text_sizePlus = view.findViewById(R.id.item_sizePlus);
            text_sellingPrice = view.findViewById(R.id.item_sellingPrice);
            text_purchasingPrice = view.findViewById(R.id.item_purchasingPrice);
            text_time = view.findViewById(R.id.item_time);
            text_supplier = view.findViewById(R.id.item_supplier);
        }

        public void setData(String title) {
            this.text_id.setText(title);
            this.text_remark.setText(title);
            this.text_name.setText(title);
            this.text_size.setText(title);
            this.text_sizePlus.setText(title);
            this.text_sellingPrice.setText(title);
            this.text_purchasingPrice.setText(title);
            this.text_time.setText(title);
            this.text_supplier.setText(title);
        }
    }
    public void clearList(){
        mDataList.clear();
    }

}