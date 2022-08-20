package com.zun.database_test.hua;

/*
 *Author: Zun
 *Date: 2022-08-20 17:41
 *Description:
 *
 */

public class GoodInfo extends BaseInfo{

    /** 数据库表的名字*/
    public static final String TABLE_GOOD_INFO = "GoodInfo";

    /**
     * 数据库字段
     */
    public static final String NAME = "name";
    public static final String SIZE = "size";
    public static final String SIZE_SON = "sizeSon";
    public static final String SELL_PRICE = "sellPrice";
    public static final String PURCHASE_PRICE = "purchasePrice";
    public static final String SUPPLIER = "supplier";
    public static final String REMARK = "remark";
    public static final String EXTEND = "extend";

    /**
     * 字段下标记
     */
    public static final int NAME_INDEX = 3;;//从3开始，因为0、1、2已经被父类占了
    public static final int SIZE_INDEX = 4;
    public static final int SIZE_SON_INDEX = 5;
    public static final int SELL_PRICE_INDEX = 6;
    public static final int PURCHASE_PRICE_INDEX = 7;
    public static final int SUPPLIER_INDEX = 8;
    public static final int REMARK_INDEX = 9;
    public static final int EXTEND_INDEX = 10;


    private String name;
    private String size;
    private String sizeSon;//子规格
    private String sellPrice;//销售价
    private String purchasePrice;//进货价
    private String supplier;
    private String remark;
    private String extend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSizeSon() {
        return sizeSon;
    }

    public void setSizeSon(String sizeSon) {
        this.sizeSon = sizeSon;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "GoodInfo{" +
                "id=" + getId() +
                ", insertTime=" + getInsertTime() +
                ", updateTime=" + getUpdateTime() +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", sizeSon='" + sizeSon + '\'' +
                ", sellPrice='" + sellPrice + '\'' +
                ", purchasePrice='" + purchasePrice + '\'' +
                ", supplier='" + supplier + '\'' +
                ", remark='" + remark + '\'' +
                ", extend='" + extend + '\'' +
                '}';
    }
}
