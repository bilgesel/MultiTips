package com.multitips.entity;

import com.multitips.api.Security;

public class Match {
    private String Home;
    private String HomeScore;
    private String Visitor;
    private String VisitorScore;
    private String Bet;
    private String Rate;
    private String Status;
    private String Date;
    private String Time;
    private String League;
    private String Flag;

    public String getHome() {
        String result="";
        try {
            result = Security.Decrypt(Home);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setHome(String home) {
        Home = home;
    }

    public String getHomeScore() {
        String result="";
        try {
            result = Security.Decrypt(HomeScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setHomeScore(String homeScore) {
        HomeScore = homeScore;
    }

    public String getVisitor() {
        String result="";
        try {
            result = Security.Decrypt(Visitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setVisitor(String visitor) {
        Visitor = visitor;
    }

    public String getVisitorScore() {
        String result="";
        try {
            result = Security.Decrypt(VisitorScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setVisitorScore(String visitorScore) {
        VisitorScore = visitorScore;
    }

    public String getBet() {
        String result="";
        try {
            result = Security.Decrypt(Bet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setBet(String bet) {
        Bet = bet;
    }

    public String getRate() {
        String result="";
        try {
            result = Security.Decrypt(Rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getStatus() {
        String result="";
        try {
            result = Security.Decrypt(Status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setStatus(String status) {
        Status = status;
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

    public String getTime() {
        String result="";
        try {
            result = Security.Decrypt(Time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLeague() {
        String result="";
        try {
            result = Security.Decrypt(League);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setLeague(String league) {
        League = league;
    }

    public String getFlag() {
        String result="";
        try {
            result = Security.Decrypt(Flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public Match(String flag,String leagueName,String date,String time,String home,String homeScore,String visitor,String visitorScore,String matchStatus,String bet,String rate){
        this.Flag = flag;
        this.League=leagueName;
        this.Date=date;
        this.Time=time;
        this.Home=home;
        this.HomeScore=homeScore;
        this.Visitor=visitor;
        this.VisitorScore=visitorScore;
        this.Status = matchStatus;
        this.Bet=bet;
        this.Rate=rate;
    }
}
