package com.itibo.database;


import com.itibo.beans.HelloBean;
import com.itibo.tracking.Converters;
import com.itibo.tracking.Tracker;

import javax.faces.bean.ManagedProperty;
import java.util.Date;

public class TrackInfoRecordsInfo {
    private Integer idTrackInfoRecords;
    private Integer idRelations;
    private String message;
    private Date date;
    private String dateString;
    private String trackerName;


    public String getTrackerName() {
        return trackerName;
    }

    public void setTrackerName(String trackerName) {
        this.trackerName = trackerName;
    }

    public String getDateString() {
        return Converters.convertDateToString(this.date, Tracker.COMMON_DATE_FORMAT);
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Integer getIdTrackInfoRecords() {
        return idTrackInfoRecords;
    }

    public void setIdTrackInfoRecords(Integer idTrackInfoRecords) {
        this.idTrackInfoRecords = idTrackInfoRecords;
    }

    public Integer getIdRelations() {
        return idRelations;
    }

    public void setIdRelations(Integer idRelations) {
        this.idRelations = idRelations;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
