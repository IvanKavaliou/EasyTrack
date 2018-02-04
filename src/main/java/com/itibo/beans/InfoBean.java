package com.itibo.beans;


import com.itibo.comparators.DateComparatorTrackNumber;
import com.itibo.database.*;
import com.itibo.trackers.BelPostTrack;
import com.itibo.trackers.SeventeenTrack;
import com.itibo.trackers.YanwenTrack;
import com.itibo.tracking.TrackInfoRecord;
import com.itibo.tracking.Tracker;
import com.itibo.tracking.TrackingNumber;
import org.primefaces.context.RequestContext;
import sun.security.validator.ValidatorException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ManagedBean
@SessionScoped
public class InfoBean implements Serializable {

    @ManagedProperty(value = "#{helloBean}")
    protected HelloBean helloBean;

    String search = "";
    String email = null;
    List<TrackerInfo> trackerList = null;
    String[] selectedTrackersList = null;
    List<RelationsInfo> relationsInfoList = null;
    List<TrackingNumber> trackingNumbersList = null; //+++
    String trackNumber = "";
    String namePackage = "";
    Integer costPackage = 0;
    FacesContext context = FacesContext.getCurrentInstance();
    ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
    TrackingNumberInfo trackingNumberInfo = null;
    List<TrackingNumberInfo> trackingNumberInfoList = null;
    boolean dlgDel = false;
    List<TrackingNumberInfo> selectedTrackingNumberInfo =  null;
    UserInfo userInfo = new UserInfo();
    String searchTrackingNumber = "";

    public void test(){
        System.out.println("test");
    }

    public void dlgClear(){
        trackNumber = "";
        namePackage = "";
        costPackage = 0;
        selectedTrackersList = new String[0];
    }

    public void tnUp(){
        if(null == trackingNumbersList) {
            trackingNumbersList = new LinkedList();
        }
            TrackingNumber tn = null;
            for(TrackingNumberInfo rec: trackingNumberInfoList){
                tn = new TrackingNumber(rec.getTrackingNumber());
                trackingNumbersList.add(tn);
                rec.setSt();
                rec.setTri();
            }
    }

    public void showAllTrackingNumbers(){
        setSearchTrackingNumber("");
        updateTrackingNumberInfoList();
    }

    public void cancelSaveTrackers(TrackingNumberInfo tn){
        List<Integer> id = new LinkedList(getDataBaseUtilites().getIdTrackersByIdTN(tn.getIdTrackingNumbers()));
        String[] selectedTrackers = new String[getDataBaseUtilites().getTrackers().size()];

        int i = 0;
        for(Integer rec: id){
            selectedTrackers[i] = rec.toString();
            i++;
        }
        tn.setSelectedTrackers(selectedTrackers);
    }

    public void saveTrackers(TrackingNumberInfo tn){
        String[] selectedTrackers = new String[trackerList.size()];

        if(tn.getSelectedTrackers().length == 0){
            System.out.println("change 0");
            List<Integer> id = new LinkedList(getDataBaseUtilites().getIdTrackersByIdTN(tn.getIdTrackingNumbers()));
            int i = 0;
            for(Integer rec: id){
                selectedTrackers[i] = rec.toString();
                i++;
            }
            tn.setSelectedTrackers(selectedTrackers);
            addMessage(bundle.getString("selectTracker.jsf"));
            return;
        }

        int i = 0;
        for(String rec: tn.getSelectedTrackers()){
            selectedTrackers[i] = rec;
            i++;
        }

        System.out.println("===========================");
        for(TrackerInfo rec:trackerList){
            for(i = 0; i < selectedTrackers.length; i++){
                if(rec.getIdTrackers().toString().equals(selectedTrackers[i])){
                    System.out.println("add " + rec.getName());
                    getDataBaseUtilites().addRelationsEd(tn.getIdTrackingNumbers(), rec.getIdTrackers());
                    getDataBaseUtilites().sessionCommit();
                    break;
                }
                if(null == selectedTrackers[i]) {
                    System.out.println("del " + rec.getName());
                    getDataBaseUtilites().delRelationsTn(tn.getIdTrackingNumbers(), rec.getIdTrackers());
                    getDataBaseUtilites().sessionCommit();
                }
            }
        }

    }

    public List<TrackInfoRecordsInfo> tIup(TrackingNumberInfo trackingNumberInfo){
        List<RelationsInfo> relationsInfoList1 = new LinkedList(getDataBaseUtilites()
                .getRelationsByIdTN(trackingNumberInfo.getIdTrackingNumbers()));
        List<TrackInfoRecordsInfo> trackInfoRecordsInfoList = new LinkedList();
        for(RelationsInfo rec: relationsInfoList1){
            trackInfoRecordsInfoList.addAll(getDataBaseUtilites().getTrackInfoRecords(rec.getIdRelations()));
        }
        return trackInfoRecordsInfoList;
    }


    public List<TrackingNumberInfo> getSelectedTrackingNumberInfo() {
        return selectedTrackingNumberInfo;
    }

    public void setSelectedTrackingNumberInfo(List<TrackingNumberInfo> selectedTrackingNumberInfo) {
        this.selectedTrackingNumberInfo = selectedTrackingNumberInfo;
    }

    public String getSearchTrackingNumber() {
        return searchTrackingNumber;
    }

    public void setSearchTrackingNumber(String searchTrackingNumber) {
        this.searchTrackingNumber = searchTrackingNumber;
    }

    public List<TrackingNumber> getTrackingNumbersList() {
        return trackingNumbersList;
    }

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public void setHelloBean(HelloBean helloBean) {
        this.helloBean = helloBean;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getNamePackage() {
        return namePackage;
    }

    public void setNamePackage(String namePackage) {
        this.namePackage = namePackage;
    }

    public boolean isDlgDel() {
        return dlgDel;
    }

    public void setDlgDel(boolean dlgDel) {
        this.dlgDel = dlgDel;
    }

    public Integer getCostPackage() {
        return costPackage;
    }

    public void setCostPackage(Integer costPackage) {
        this.costPackage = costPackage;
    }

    public List<TrackerInfo> gettTrackerList() {
        return getDataBaseUtilites().getTrackers();
    }

    public List<TrackerInfo> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<TrackerInfo> trackerList) {
        this.trackerList = trackerList;
    }

    public String[] getSelectedTrackersList() {
        return selectedTrackersList;
    }

    public List<TrackingNumberInfo> getTrackingNumberInfoList(){
        Collections.sort(trackingNumberInfoList, new DateComparatorTrackNumber());
        return trackingNumberInfoList;
    }

    public void setSelectedTrackersList(String[] selectedTrackersList) {
        this.selectedTrackersList = selectedTrackersList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void saveEmail(){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(userInfo.getEmail());

        if(!matcher.find()){
            addErrorMessage(bundle.getString("incorrectlyEmail.jsf"));
            userInfo.setEmail(helloBean.getEmail());
            return;
        }

        getDataBaseUtilites().upEmail(userInfo);
        getDataBaseUtilites().sessionCommit();
        System.out.println("Email saved");
    }

    public void dlgDelShow(){
        if(!selectedTrackingNumberInfo.isEmpty()){
            setDlgDel(true);
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('del').show();");
            return;
        }
        addMessage(bundle.getString("notSelected.jsf"));
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void dlgDelHide(){
        setDlgDel(false);
    }

    public DataBaseUtilites getDataBaseUtilites() {
        return helloBean.getDataBaseUtilites();
    }


    public void updateTrackingNumberInfoList(){
        trackingNumberInfoList = new LinkedList(helloBean.getDataBaseUtilites().getTrackingNumberInfoList(userInfo.getIdUsers(), searchTrackingNumber, helloBean.admin));
        trackerList = new LinkedList(gettTrackerList());
        tnUp();
        System.out.println("trackingNumberInfoList update");
    }

    @PostConstruct
    public void init(){
        System.out.println("NewTrackingNumberBean init");
        setUserInfo(helloBean.loginUser());
        updateTrackingNumberInfoList();
/*        setEmail(helloBean.getEmail());*/
    }

    public List<RelationsInfo> getRelationsInfoList(Integer idTrackingNumbers){
        relationsInfoList = new LinkedList();
        RelationsInfo ri = null;
        for(String rec: selectedTrackersList){
            ri = new RelationsInfo();
            ri.setIdTrackingNumbers(idTrackingNumbers);
            ri.setIdTrackers(Integer.valueOf(rec));
            relationsInfoList.add(ri);
        }
        return relationsInfoList;
    }


    public void addTrackingNumbers() throws ValidatorException {
        if(addTrackingNumberValidator()){
            trackingNumberInfo = new TrackingNumberInfo();
            trackingNumberInfo.setIdUsers(helloBean.getId());
            trackingNumberInfo.setTrackingNumber(trackNumber);
            trackingNumberInfo.setNamePackage(namePackage);
            trackingNumberInfo.setCost(costPackage);
            getDataBaseUtilites().addTrackingNumbers(trackingNumberInfo);
            getDataBaseUtilites().addRelations(getRelationsInfoList(trackingNumberInfo.getIdTrackingNumbers()));
            getDataBaseUtilites().sessionCommit();
            trackingNumberInfoList.add(trackingNumberInfo);
            System.out.println("Add trackingNUmber to DB");
            helloBean.dlgAddHide();
            dlgClear();
            tnUp();
        }
    }


    public void addErrorMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage
                .SEVERITY_WARN, message, "Error"));
    }

    public void delTrackingNumbers(){
        for(TrackingNumberInfo rec: selectedTrackingNumberInfo){
            helloBean.getDataBaseUtilites().delTrackingNumbers(rec.getIdTrackingNumbers());
        }
        helloBean.getDataBaseUtilites().sessionCommit();
        trackingNumberInfoList.removeAll(selectedTrackingNumberInfo);
    }

    public boolean addTrackingNumberValidator(){
        if ((trackNumber.trim().length() == 0) || (namePackage.trim().length() == 0)
                || (null == selectedTrackersList) || (selectedTrackersList.length == 0) ){
            addErrorMessage(bundle.getString("nullField.jsf"));
            return false;
        }

        if(trackNumber.trim().length() == 0){
            addErrorMessage(bundle.getString("incorrectlyEnteredTrackingNumber.jsf"));
            return false;
        }

        if((namePackage.trim().length() < 3) || (namePackage.trim().length() > 30)){
            addErrorMessage(bundle.getString("namePackageInter.jsf"));
            return false;
        }

        if(getDataBaseUtilites().getTrackingNumberIdByName(trackNumber, helloBean.getId()) != null){
            addErrorMessage(bundle.getString("thisTrack.jsf"));
            return false;
        }

        return true;
    }

    public void saveEvent(TrackingNumberInfo trackingNumberInfo){
        TrackingNumberInfo tni = new TrackingNumberInfo();
        tni = getDataBaseUtilites().getTrackingNumberById(trackingNumberInfo.getIdTrackingNumbers());

        if(trackingNumberInfo.getTrackingNumber().length()!= 13){
            addErrorMessage(bundle.getString("trackerNumberNotValid.jsf"));
            trackingNumberInfo.setTrackingNumber(tni.getTrackingNumber());
            return;
        }

        if((trackingNumberInfo.getNamePackage().trim().length() < 3) || (trackingNumberInfo.getNamePackage().trim().length() > 30)){
            addErrorMessage(bundle.getString("namePackageInter.jsf"));
            trackingNumberInfo.setNamePackage(tni.getNamePackage());
            return;
        }

        if(getDataBaseUtilites().getTrackingNumberIdByName(trackingNumberInfo.getTrackingNumber(), helloBean.getId()) != null){
            addErrorMessage(bundle.getString("thisTrack.jsf"));
            trackingNumberInfo.setTrackingNumber(tni.getTrackingNumber());
            return;
        }

        getDataBaseUtilites().upTrackingNumbers(trackingNumberInfo);
        getDataBaseUtilites().sessionCommit();
        System.out.println("Save event");
    }

    public void showData(TrackingNumberInfo trackingNumberInfo) throws InterruptedException {
        TrackingNumber tn = new TrackingNumber(trackingNumberInfo.getTrackingNumber());
        List<RelationsInfo> relationsInfoList = new LinkedList(getDataBaseUtilites().getRelationsByIdTN(trackingNumberInfo.getIdTrackingNumbers()));
        List<String> trackersNameList = new LinkedList();

        for(RelationsInfo rec: relationsInfoList)
        {
            trackersNameList.add(getDataBaseUtilites().getTrackerNameByIdRelation(rec.getIdRelations()));
        }

        for(String rec: trackersNameList){
            if(rec.equals("BelPostTrack")){
                Tracker bp = new BelPostTrack(tn);
                tn.add(bp);
            }
            if(rec.equals("SeventeenTrack")){
                Tracker st = new SeventeenTrack(tn);
                tn.add(st);
            }
            if(rec.equals("YanwenTrack")){
                Tracker yt = new YanwenTrack(tn);
                tn.add(yt);
            }
        }
        tn.update2();


        TrackInfoRecordsInfo trackInfoRecordsInfo = null;
        for(String key: tn.getInfo().keySet()){
            for(TrackInfoRecord record: tn.getInfo().get(key)){
                trackInfoRecordsInfo = new TrackInfoRecordsInfo();
                Integer idRelations = getDataBaseUtilites().getIdRelations(trackingNumberInfo.getIdTrackingNumbers(), getDataBaseUtilites().getIdTrackersByName(record.getTrackerId()));
                trackInfoRecordsInfo.setIdRelations(idRelations);
                trackInfoRecordsInfo.setDate(record.getDate());
                trackInfoRecordsInfo.setMessage(record.getMessage());
                if(null == getDataBaseUtilites().getTrackInfoRecordsId(trackInfoRecordsInfo)){
                    getDataBaseUtilites().addTrackInfoRecords(trackInfoRecordsInfo);
                    getDataBaseUtilites().sessionCommit();
                    System.out.println(record.toString() + "         ADDED TO DATABASE");
                } else {
                    System.out.println(record.toString() + "         DON'T ADDED TO DATABASE");
                }
            }
        }
        trackingNumberInfo.setTri();
    }
}
