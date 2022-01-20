package com.multitips.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionItem implements Serializable {
    private String name;
    private String skuNumber;
    private String price;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSkuNumber() {
        return this.skuNumber;
    }
    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    public String getPrice() {
        return this.price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public SubscriptionItem(String name, String skuNumber,String price) {
        this.name = name;
        this.skuNumber = skuNumber;
        this.price = price;
    }

    public static List<SubscriptionItem> GetDataSource(String type){
        List<SubscriptionItem> dataSource = new ArrayList<>();
        if(type.equals("specialVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_special_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_special_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_special_vip_month_6",""));
        }else if (type.equals("fixedVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_fixed_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_fixed_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_fixed_vip_month_6",""));
        }else if (type.equals("correctScoreVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_correct_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_correct_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_correct_vip_month_6",""));
        }else if (type.equals("htFtVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_htft_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_htft_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_htft_vip_month_6",""));
        }else if(type.equals("multiVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_multi_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_multi_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_multi_vip_month_6",""));
        }else if (type.equals("mixedVIP")){
            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_mixed_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_mixed_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_mixed_vip_month_6",""));
        }
        return dataSource;
    }

    public static List<SubscriptionItem> GetDataSourceAll(){
        List<SubscriptionItem> dataSource = new ArrayList<>();

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_special_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_special_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_special_vip_month_6",""));

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_fixed_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_fixed_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_fixed_vip_month_6",""));

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_correct_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_correct_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_correct_vip_month_6",""));

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_htft_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_htft_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_htft_vip_month_6",""));

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_multi_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_multi_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_multi_vip_month_6",""));

            dataSource.add(new SubscriptionItem("Monthly Subscription","sku_mixed_vip_month_1",""));
            dataSource.add(new SubscriptionItem("3 Months Subscription","sku_mixed_vip_month_3",""));
            dataSource.add(new SubscriptionItem("6 Months Subscription","sku_mixed_vip_month_6",""));

        return dataSource;
    }

    public static List<SubscriptionItem> GetOldDataSource(String type){
        List<SubscriptionItem> dataSource = new ArrayList<>();
        return dataSource;
    }
}
