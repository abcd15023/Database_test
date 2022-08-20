package com.zun.database_test.weight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.database_test.R;
import com.zun.database_test.ui.AddActivity;
import com.zun.database_test.utils.Utils;

//下拉式菜单
public class PopMain extends PopupWindow {

    private View conentView;  //声明一个视图
    private RelativeLayout mAddRelativeLayout,mImportRelativeLayout,mExportRelativeLayout;  //声明菜单项，且为相对布局

    //构造器，带上下文参数。负责初始化和传入Activity参数
    public PopMain(final Activity context){
        //反射器
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //反射布局
        conentView = inflater.inflate(R.layout.pop_main,null);

        //设置PopupWindow的View
        this.setContentView(conentView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow可点击
        this.setFocusable(true); //获取焦点，变得可点击
        this.setOutsideTouchable(true); //获取外侧点击许可
        //刷新状态
        this.update();
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        //点击返回键或其他地方使PopupWindow消失，设置了这个才能触发onDismisslistener
        this.setBackgroundDrawable(dw);

        //设置PopupWindow的弹出和收起速度的动画
       //this.setAnimationStyle(R.style.);

        //实例化菜单项
        mAddRelativeLayout = (RelativeLayout) conentView.findViewById(R.id.add_relative_layout);
        mImportRelativeLayout = (RelativeLayout) conentView.findViewById(R.id.import_relative_layout);
        mExportRelativeLayout = (RelativeLayout) conentView.findViewById(R.id.export_relative_layout);
        //设置菜单项的监听
        mAddRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddActivity.class);
                context.startActivity(intent);  //因为本类不是Activity类，这里需要引用context调用Activity类的方法
                //Toast.makeText(context,"新增商品",Toast.LENGTH_SHORT).show();
                PopMain.this.dismiss(); //点击完菜单消失
            }
        });
        mImportRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMain.this.dismiss();

                new AlertDialog.Builder(context)
                        .setTitle("导入数据")
                        .setMessage("确定导入数据吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.excelToDb(context);
                            }
                        }).show();
            }
        });
        mExportRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopMain.this.dismiss();

                new AlertDialog.Builder(context)
                        .setTitle("导出备份")
                        .setMessage("确定导出备份吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.dbToExcel(context);
                            }
                        }).show();
            }
        });
    }

    //显示PopupWindow，必不可少，在MainActivity.java中以组件的点击事件来调用
    public void showPopupWindow(View parent){
        //if没有显示，则下拉显示，else在显示，则关闭下拉菜单
        if(!this.isShowing()){
            //以下拉方式显示PopupWindow。parent是锚点，即弹出位置。0,0是xy方向的偏移量
            this.showAsDropDown(parent,0,0);
        }else {
            this.dismiss();
        }
    }
}
