package com.itibo.database;


import com.itibo.tracking.Converters;
import com.itibo.tracking.Tracker;

import java.util.Date;

public class UserInfo {
    private Integer idUsers;
    private String email;
    private String googleid;
    private Date regdate;
    private String dateString;

/*    UserInfo(Integer idUsers, String email, String googleid){
        this.idUsers = idUsers;
        this.email = email;
        this.googleid = googleid;
    }*/

    public String getDateString() {
        return Converters.convertDateToString(regdate, Tracker.COMMON_DATE_FORMAT);
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoogleid() {
        return googleid;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }
}
