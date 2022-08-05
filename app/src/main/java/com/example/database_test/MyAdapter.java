package com.example.database_test;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<nianhui_info> mdataList;

    //内部类ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text1,text2,text3;
        //构造函数传入View参数，
        public ViewHolder(View view){
            super(view);
            text1 = view.findViewById(R.id.item1_text1);
            text2 = view.findViewById(R.id.item1_text2);
            text3 = view.findViewById(R.id.item1_text3);
        }
    }
    //MyAdapter构造函数，用于把要展示的数据源传进来，赋值给全局变量mdataList。
    public MyAdapter(List<nianhui_info> dataList){
        mdataList = dataList;
    }

    @NonNull
    @Override  //创建ViewHolder实例,将item布局加载到构造函数中，最后把ViewHolder实例返回。
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item1,parent,false);
        ViewHolder holder = new ViewHolder(view);

        //RecyclerView下的 行布局控件 都可以单独做点击事件
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mdataList.get(position);
                Toast.makeText(v.getContext(),String.valueOf(nh.getId()),Toast.LENGTH_SHORT).show();
            }
        });
        holder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mdataList.get(position);
                Toast.makeText(v.getContext(),nh.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                nianhui_info nh = mdataList.get(position);
                Toast.makeText(v.getContext(),nh.getSize(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override  //对子项数据进行赋值初始化。
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        nianhui_info nh = mdataList.get(position);
        Log.i("zun","1");
        String str = String.valueOf(nh.getId());
        holder.text1.setText(str);
        Log.i("zun","2");
        holder.text2.setText(nh.getName());
        holder.text3.setText(nh.getSize());
    }

    @Override   //返回数据源的长度。
    public int getItemCount() {
        return mdataList.size();
    }

    public void clearList(){
        mdataList.clear();
    }
}
