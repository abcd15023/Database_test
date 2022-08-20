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
package com.zun.database_test.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zun.database_test.BaseAdapter;
import com.example.database_test.R;
import com.zun.database_test.bean.NianHuiBean;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainAdapter extends BaseAdapter<MainAdapter.ViewHolder> {

    private List<NianHuiBean> mDataList;

    public MainAdapter(Context context) {
        super(context);
    }

    //靠notifyDataSetChanged()方法给mDataList传值，更新Adapter
    public void notifyDataSetChanged(List<NianHuiBean> dataList) {
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
                inflate(R.layout.item,parent,false);
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
//        holder.text_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                nianhui_info nh = mDataList.get(position);
//                Toast.makeText(v.getContext(),nh.getName(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.text_purchasingPrice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                nianhui_info nh = mDataList.get(position);
//                Toast.makeText(v.getContext(),nh.getPurchasingPrice(),Toast.LENGTH_SHORT).show();
//            }
//        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.setData(mDataList.get(position));

        NianHuiBean nh = mDataList.get(position);
        Log.i("zun","1");
        String str = String.valueOf(nh.getId());
        //holder.text_id.setText(str);
        holder.remarkTv.setText(nh.getRemark());
        holder.nameTv.setText(nh.getName());
        holder.nameTv.setVisibility(View.VISIBLE);
        holder.sizeTv.setText(nh.getSize());
        holder.sizeTv.setVisibility(View.VISIBLE);
        holder.sizePlusTv.setText(nh.getSizePlus());
        Log.i("zunq","text_name："+nh.getName()+",text_size："+nh.getSize()+",text_sizePlus："+nh.getSizePlus());

        holder.sellingPriceTv.setText(nh.getSellingPrice());
        holder.purchasingPriceTv.setText(nh.getPurchasingPrice());
        holder.timeTv.setText(nh.getTime());
        holder.supplierTv.setText(nh.getSupplier());

        if(!nh.getRemark().isEmpty()){
            holder.remarkTv.setVisibility(View.VISIBLE);
        }
        if(!nh.getSize().trim().isEmpty()){
            holder.sizeTv.setVisibility(View.VISIBLE);
        }
        if(!nh.getSizePlus().trim().isEmpty()){
            holder.sizePlusTv.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

//        TextView tvTitle;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//        }
        TextView idTv,remarkTv,nameTv,sizeTv,sizePlusTv,sellingPriceTv,purchasingPriceTv,timeTv,supplierTv;

        //构造函数传入View参数，
        public ViewHolder(View view){
            super(view);

            remarkTv = view.findViewById(R.id.remark_tv);
            nameTv = view.findViewById(R.id.name_tv);
            sizeTv = view.findViewById(R.id.size_tv);
            sizePlusTv = view.findViewById(R.id.sizePlus_tv);
            sellingPriceTv = view.findViewById(R.id.sellingPrice_tv);
            purchasingPriceTv = view.findViewById(R.id.purchasingPrice_tv);
            timeTv = view.findViewById(R.id.time_tv);
            supplierTv = view.findViewById(R.id.supplier_tv);
        }

        public void setData(String title) {
            this.idTv.setText(title);
            this.remarkTv.setText(title);
            this.nameTv.setText(title);
            this.sizeTv.setText(title);
            this.sizePlusTv.setText(title);
            this.sellingPriceTv.setText(title);
            this.purchasingPriceTv.setText(title);
            this.timeTv.setText(title);
            this.supplierTv.setText(title);
        }
    }
}