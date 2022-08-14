package com.example.database_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    EditText etUpdateRemark,etUpdateName,etUpdateSize,etUpdateSizePlus,etUpdateSellingPrice,etUpdatePurchasingPrice,etUpdateTime,etUpdateSupplier;
    Button btnupdate;
    String id, remark, name, size, sizePlus, sellingPrice, purchasingPrice, time, supplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();

        id = getValue("id");
        remark = getValue("remark");
        name = getValue("name");
        size = getValue("size");
        sizePlus = getValue("sizePlus");
        sellingPrice = getValue("sellingPrice");
        purchasingPrice = getValue("purchasingPrice");
        time = getValue("time");
        supplier = getValue("supplier");

        setIntentText();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText上的值更新到数据库
                Utils.dbUpdate(id, etUpdateRemark.getText().toString(), etUpdateName.getText().toString(), etUpdateSize.getText().toString(),
                        etUpdateSizePlus.getText().toString(), etUpdateSellingPrice.getText().toString(), etUpdatePurchasingPrice.getText().toString(),
                        etUpdateTime.getText().toString(), etUpdateSupplier.getText().toString());
                onBackPressed(); //调用系统返回方法，效果如同返回键，不带刷新
                Utils.dbETSearch(BaseActivity.tempNewtext); //返回到上一界面后再调用一次搜索刷新list
            }
        });
    }
    public String getValue(String str){
        if(getIntent().getStringExtra(str).equals("")){
            return ""; //如不做判断，空值将显示为字符串“Null”
        }else{
            return getIntent().getStringExtra(str);
        }
    }
    public void initView(){
        etUpdateRemark = findViewById(R.id.ETUpdateRemark);
        etUpdateName = findViewById(R.id.ETUpdateName);
        etUpdateSize  = findViewById(R.id.ETUpdateSize);
        etUpdateSizePlus = findViewById(R.id.ETUpdateSizePlus);
        etUpdateSellingPrice = findViewById(R.id.ETUpdateSellingPrice);
        etUpdatePurchasingPrice = findViewById(R.id.ETUpdatePurchasingPrice);
        etUpdateTime = findViewById(R.id.ETUpdateTime);
        etUpdateSupplier = findViewById(R.id.ETUpdateSupplier);
        btnupdate = findViewById(R.id.btnUpdate);
    }
    //把传过来的值赋值到各个EditText中
    public void setIntentText(){
        etUpdateRemark.setText(remark);
        etUpdateName.setText(name);
        etUpdateSize.setText(size);
        etUpdateSizePlus.setText(sizePlus);
        etUpdateSellingPrice.setText(sellingPrice);
        etUpdatePurchasingPrice.setText(purchasingPrice);
        etUpdateTime.setText(time);
        etUpdateSupplier.setText(supplier);
    }
}