package com.itibo.database;


import com.itibo.beans.HelloBean;
import com.itibo.trackers.BelPostTrack;
import com.itibo.trackers.SeventeenTrack;
import com.itibo.trackers.YanwenTrack;
import com.itibo.comparators.DateComparatorRecordInfo;
import com.itibo.tracking.Tracker;
import com.itibo.tracking.TrackingNumber;

import javax.faces.context.FacesContext;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TrackingNumberInfo {
/*    @ManagedProperty(value = "#{helloBean}")
    protected HelloBean helloBean;*/

    private Integer idTrackingNumbers;
    private Integer idUsers;
    private String trackingNumber;
    private String namePackage;
    private Integer cost;
    private boolean selected = false;
    private String[] selectedTrackers = null;
    private List<TrackerInfo> trackerList = new LinkedList();
    private DataBaseUtilites dataBaseUtilites = new DataBaseUtilites();
    private List<TrackInfoRecordsInfo> tri = null;
    private boolean casch = false;
    private TrackingNumber trn = null;
    private String lastMessage = null;
    private String lastDateString = null;
    private String activeIndex = "null";


    public DataBaseUtilites getDataBaseUtiles(){
        HelloBean hellobean = (HelloBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("helloBean");
        return hellobean.getDataBaseUtilites();
    }

    public void caschListener() throws InterruptedException {
        TrackingNumber tn = new TrackingNumber(trackingNumber);
        Tracker bp = new BelPostTrack(tn);
        Tracker st = new SeventeenTrack(tn);
        Tracker yt = new YanwenTrack(tn);

        tn.add(bp);
        tn.add(st);
        tn.add(yt);
        tn.update2();

        trn = tn;
    }

    public List<TrackInfoRecordsInfo> tIup(TrackingNumberInfo trackingNumberInfo){
        List<RelationsInfo> relationsInfoList1 = new LinkedList(getDataBaseUtiles()
                .getRelationsByIdTN(trackingNumberInfo.getIdTrackingNumbers()));
        List<TrackInfoRecordsInfo> trackInfoRecordsInfoList = new LinkedList();
        for(RelationsInfo rec : relationsInfoList1) {
            trackInfoRecordsInfoList.addAll(getDataBaseUtiles().getTrackInfoRecords(rec.getIdRelations()));
        }
        for(TrackInfoRecordsInfo rec : trackInfoRecordsInfoList){
            rec.setTrackerName(getDataBaseUtiles().getTrackerNameByIdRelation(rec.getIdRelations()));
        }

        Collections.sort(trackInfoRecordsInfoList, new DateComparatorRecordInfo());

        return trackInfoRecordsInfoList;
    }

    public void clear(){
        idTrackingNumbers = null;
        idUsers = null;
        trackingNumber = "";
        namePackage = "";
        cost = null;
    }

    public void setSt(){

        List<Integer> id = new LinkedList(getDataBaseUtiles().getIdTrackersByIdTN(idTrackingNumbers));
        selectedTrackers = new String[id.size()];
        int i = 0;
        for(Integer rec: id){
            selectedTrackers[i] = getDataBaseUtiles().getTrackerById(rec).getIdTrackers().toString();
            i++;
        }
    }

    public List<TrackInfoRecordsInfo> getTri() {
        return tri;
    }

    public void setTri() {
        tri = new LinkedList(tIup(this));
    }

    public String getLastMessage(){
        if (tri.size() > 0) {
            return tri.get(0).getMessage();
        }
        return "";
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastDateString() {
        if(tri.size() > 0) {
            return tri.get(0).getDateString();
        }
        return "";
    }

    public void setLastDateString(String lastDateString) {
        this.lastDateString = lastDateString;
    }

    public boolean isCasch() {
        return casch;
    }

    public void setCasch(boolean casch) {
        this.casch = casch;
    }

    public List<TrackerInfo> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<TrackerInfo> trackerList) {
        this.trackerList = trackerList;
    }

    public Integer getIdTrackingNumbers() {
        return idTrackingNumbers;
    }

    public void setIdTrackingNumbers(Integer idTrackingNumbers) {
        this.idTrackingNumbers = idTrackingNumbers;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(Integer idUsers) {
        this.idUsers = idUsers;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber.trim().toUpperCase();
    }

    public String getNamePackage() {
        return namePackage;
    }

    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage.trim();
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String[] getSelectedTrackers() {
        return selectedTrackers;
    }

    public void setSelectedTrackers(String[] selectedTrackers) {
        this.selectedTrackers = selectedTrackers;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }
}
