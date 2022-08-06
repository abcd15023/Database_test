package com.example.database_test;

public class nianhui_info {
    private int id;
    private String remark;
    private String name;
    private String size;
    private String sizePlus;
    private String sellingPrice;
    private String purchasingPrice;
    private String time;
    private String supplier;

    public nianhui_info() {
    }

    public nianhui_info(int id, String remark, String name, String size, String sizePlus, String sellingPrice, String purchasingPrice, String time, String supplier) {
        this.id = id;
        this.remark = remark;
        this.name = name;
        this.size = size;
        this.sizePlus = sizePlus;
        this.sellingPrice = sellingPrice;
        this.purchasingPrice = purchasingPrice;
        this.time = time;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getSizePlus() {
        return sizePlus;
    }

    public void setSizePlus(String sizePlus) {
        this.sizePlus = sizePlus;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPurchasingPrice() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(String purchasingPrice) {
        this.purchasingPrice = purchasingPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "nianhui_info{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", sizePlus='" + sizePlus + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", purchasingPrice='" + purchasingPrice + '\'' +
                ", time='" + time + '\'' +
                ", supplier='" + supplier + '\'' +
                '}';
    }
}
