package com.example.database_test.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.database_test.BaseActivity;
import com.example.database_test.R;
import com.example.database_test.utils.Utils;

public class AddActivity extends Activity {
    private EditText mAddRemarkEt,mAddNameEt,mAddSizeEt,mAddSizePlusEt,mAddSellingPriceEt,mAddPurchasingPriceEt,mAddTimeEt,mAddSupplierEt;
    private Button mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        int id = Utils.getdbMaxId()+1;

        mAddTimeEt.setText(Utils.getTime()); //在增加商品时给time EditText自动补上系统时间

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddNameEt.getText().toString().trim().isEmpty()){
                    Toast.makeText(AddActivity.this,"名称不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    Utils.dbInsert(id, mAddRemarkEt.getText().toString(), mAddNameEt.getText().toString(), mAddSizeEt.getText().toString(),
                            mAddSizePlusEt.getText().toString(), mAddSellingPriceEt.getText().toString(), mAddPurchasingPriceEt.getText().toString(),
                            mAddTimeEt.getText().toString(), mAddSupplierEt.getText().toString());
                    onBackPressed(); //调用系统返回方法，效果如同返回键，不带刷新
                    if(Utils.mTempNewText != null){ //避免无搜索时进入增加商品返回时报错
                        Utils.dbETSearch(Utils.mTempSql); //返回到上一界面后再调用一次搜索刷新list
                    }
                }
            }
        });
    }

    private void initView(){
        mAddRemarkEt = findViewById(R.id.add_remark_et);
        mAddNameEt = findViewById(R.id.add_name_et);
        mAddSizeEt  = findViewById(R.id.add_size_et);
        mAddSizePlusEt = findViewById(R.id.add_size_plus_et);
        mAddSellingPriceEt = findViewById(R.id.add_selling_price_et);
        mAddPurchasingPriceEt = findViewById(R.id.add_purchasing_price_et);
        mAddTimeEt = findViewById(R.id.add_time_et);
        mAddSupplierEt = findViewById(R.id.add_supplier_et);
        mAddBtn = findViewById(R.id.add_btn);
    }
}