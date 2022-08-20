package com.zun.database_test.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.database_test.R;
import com.zun.database_test.utils.Utils;

public class EditActivity extends Activity {

    private EditText mEditRemarkEt,mEditNameEt,mEditSizeEt,mEditSizePlusEt,mEditSellingPriceEt,mEditPurchasingPriceEt,mEditTimeEt,mEditSupplierEt;
    private Button mEditBtn,mGetTimeBtn;
    private String mId, mRemark, mName, mSize, mSizePlus, mSellingPrice, mPurchasingPrice, mTime, mSupplier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initView();

        mId = getValue("id");
        mRemark = getValue("remark");
        mName = getValue("name");
        mSize = getValue("size");
        mSizePlus = getValue("sizePlus");
        mSellingPrice = getValue("sellingPrice");
        mPurchasingPrice = getValue("purchasingPrice");
        mTime = getValue("time");
        mSupplier = getValue("supplier");

        setIntentText();

        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把EditText上的值更新到数据库
                Utils.dbUpdate(mId, mEditRemarkEt.getText().toString(), mEditNameEt.getText().toString(), mEditSizeEt.getText().toString(),
                        mEditSizePlusEt.getText().toString(), mEditSellingPriceEt.getText().toString(), mEditPurchasingPriceEt.getText().toString(),
                        mEditTimeEt.getText().toString(), mEditSupplierEt.getText().toString());
                onBackPressed(); //调用系统返回方法，效果如同返回键，不带刷新
                Utils.dbETSearch(Utils.mTempSql); //返回到上一界面后再调用一次搜索刷新list
            }
        });
        mGetTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditTimeEt.setText(Utils.getTime());
            }
        });
    }
    private String getValue(String str){
        if(getIntent().getStringExtra(str).equals("") || getIntent().getStringExtra(str).isEmpty()){
            return ""; //如不做判断，空值将显示为字符串“Null”
        }else{
            if(getIntent().getStringExtra(str).equals("null")){
                return "";
            }else{
                return getIntent().getStringExtra(str);
            }
        }
    }
    private void initView(){
        mEditRemarkEt = findViewById(R.id.edit_remark_et);
        mEditNameEt = findViewById(R.id.edit_name_et);
        mEditSizeEt  = findViewById(R.id.edit_size_et);
        mEditSizePlusEt = findViewById(R.id.edit_size_plus_et);
        mEditSellingPriceEt = findViewById(R.id.edit_selling_price_et);
        mEditPurchasingPriceEt = findViewById(R.id.edit_purchasing_price_et);
        mEditTimeEt = findViewById(R.id.edit_time_et);
        mEditSupplierEt = findViewById(R.id.edit_supplier_et);
        mEditBtn = findViewById(R.id.edit_btn);
        mGetTimeBtn = findViewById(R.id.getTime_btn);
    }
    //把传过来的值赋值到各个EditText中
    private void setIntentText(){
        mEditRemarkEt.setText(mRemark);
        mEditNameEt.setText(mName);
        mEditSizeEt.setText(mSize);
        mEditSizePlusEt.setText(mSizePlus);
        mEditSellingPriceEt.setText(mSellingPrice);
        mEditPurchasingPriceEt.setText(mPurchasingPrice);
        mEditTimeEt.setText(mTime);
        mEditSupplierEt.setText(mSupplier);
    }
}