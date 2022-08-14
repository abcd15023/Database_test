package com.example.database_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText etAddRemark,etAddName,etAddSize,etAddSizePlus,etAddSellingPrice,etAddPurchasingPrice,etAddTime,etAddSupplier;
    Button btnuAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        int id = Utils.getdbMaxId()+1;

        etAddTime.setText(Utils.getTime()); //在增加商品时给time EditText自动补上系统时间

        btnuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dbInsert(id, etAddRemark.getText().toString(), etAddName.getText().toString(), etAddSize.getText().toString(),
                        etAddSizePlus.getText().toString(), etAddSellingPrice.getText().toString(), etAddPurchasingPrice.getText().toString(),
                        etAddTime.getText().toString(), etAddSupplier.getText().toString());
                onBackPressed(); //调用系统返回方法，效果如同返回键，不带刷新
                if(BaseActivity.tempNewtext != null){
                    Utils.dbETSearch(BaseActivity.tempNewtext); //返回到上一界面后再调用一次搜索更新list
                }
            }
        });
    }

    public void initView(){
        etAddRemark = findViewById(R.id.ETAddRemark);
        etAddName = findViewById(R.id.ETAddName);
        etAddSize  = findViewById(R.id.ETAddSize);
        etAddSizePlus = findViewById(R.id.ETAddSizePlus);
        etAddSellingPrice = findViewById(R.id.ETAddSellingPrice);
        etAddPurchasingPrice = findViewById(R.id.ETAddPurchasingPrice);
        etAddTime = findViewById(R.id.ETAddTime);
        etAddSupplier = findViewById(R.id.ETAddSupplier);
        btnuAdd = findViewById(R.id.btnAdd);
    }
}