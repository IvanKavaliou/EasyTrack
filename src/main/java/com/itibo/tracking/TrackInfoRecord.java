package com.itibo.tracking;


import java.util.Date;

public class TrackInfoRecord {

    private String trackerId;
    private String dateString;
    private String message;
    private Date date;

    public TrackInfoRecord(Tracker tracker){
        setTrackerId(tracker.getTrackerName());
    }

    public TrackInfoRecord(String trackerId, Date date, String message) {
        this.trackerId = trackerId;
        this.message = message;
        this.date = date;
        this.dateString = Converters.convertDateToString(this.date, Tracker.COMMON_DATE_FORMAT);
    }

    public void setTrackerId(String trackerId){
        this.trackerId = trackerId;
    }
    public String getTrackerId(){
        return trackerId;
    }

    public void setDateString(String dateString){
        this.dateString = dateString;
    }
    public String getDateString(){
        return dateString;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public void messageAdd(String message){
        this.message+= " " + message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return trackerId +" "+ dateString +" "+ message;
    }

    public void fillDate(String dateString, String dateStringFormat) {
        this.setDate(Converters.convertStringToDate(dateString, dateStringFormat));
        this.setDateString(Converters.convertDateToString(this.getDate(), Tracker.COMMON_DATE_FORMAT));
    }
}
