package com.multitips.entity;

import com.multitips.api.Security;

import java.util.List;

public class Category {
    private String Id;
    private String Name;
    private String Icon;
    private String Information;
    private String HeaderIsVisible;
    private String HeaderText;
    private String HeaderUrl;
    private String FooterIsVisible;
    private String FooterText;
    private String FooterUrl;
    private Integer NextPage;
    private Integer TotalPage;
    private boolean HasNextPage;
    private List<Coupon> CouponList;

    public Integer getNextPage() {
        return NextPage;
    }

    public void setNextPage(Integer nextPage) {
        NextPage = nextPage;
    }

    public Integer getTotalPage() {
        return TotalPage;
    }

    public void setTotalPage(Integer totalPage) {
        TotalPage = totalPage;
    }

    public boolean isHasNextPage() {
        return HasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        HasNextPage = hasNextPage;
    }



    public String getId() {
        String result="";
        try {
            result = Security.Decrypt(Id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        String result="";
        try {
            result = Security.Decrypt(Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIcon() {
        String result="";
        try {
            result = Security.Decrypt(Icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getInformation() {
        String result="";
        try {
            result = Security.Decrypt(Information);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public List<Coupon> getCouponList() {
        return CouponList;
    }

    public void setCouponList(List<Coupon> couponList) {
        CouponList = couponList;
    }

    public String getHeaderIsVisible() {
        String result="";
        try {
            result = Security.Decrypt(HeaderIsVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setHeaderIsVisible(String headerIsVisible) {
        HeaderIsVisible = headerIsVisible;
    }

    public String getHeaderText() {
        String result="";
        try {
            result = Security.Decrypt(HeaderText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setHeaderText(String headerText) {
        HeaderText = headerText;
    }

    public String getHeaderUrl() {
        String result="";
        try {
            result = Security.Decrypt(HeaderUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setHeaderUrl(String headerUrl) {
        HeaderUrl = headerUrl;
    }

    public String getFooterIsVisible() {
        String result="";
        try {
            result = Security.Decrypt(FooterIsVisible);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setFooterIsVisible(String footerIsVisible) {
        FooterIsVisible = footerIsVisible;
    }

    public String getFooterText() {
        String result="";
        try {
            result = Security.Decrypt(FooterText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setFooterText(String footerText) {
        FooterText = footerText;
    }

    public String getFooterUrl() {
        String result="";
        try {
            result = Security.Decrypt(FooterUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setFooterUrl(String footerUrl) {
        FooterUrl = footerUrl;
    }
}
