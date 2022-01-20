package com.multitips.entity;

import com.multitips.api.Security;

import java.util.List;

public class Coupon {
    private String Name;
    private String Information;
    private String Date;
    private List<Match> MatchList;

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

    public String getDate() {
        String result="";
        try {
            result = Security.Decrypt(Date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setDate(String date) {
        Date = date;
    }

    public List<Match> getMatchList() {
        return MatchList;
    }

    public void setMatchList(List<Match> matchList) {
        MatchList = matchList;
    }
}
