package com.itibo.beans;

import com.google.api.services.plus.model.Person;
import com.itibo.database.*;
import com.itibo.trackers.BelPostTrack;
import com.itibo.trackers.SeventeenTrack;
import com.itibo.trackers.YanwenTrack;
import com.itibo.tracking.TrackInfoRecord;
import com.itibo.tracking.Tracker;
import com.itibo.tracking.TrackingNumber;
import org.primefaces.context.RequestContext;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {

	public String easyTrack = "EasyTrack";
	public Person profile;
	public Integer id = null;
	private String email = null;
	boolean dlgAdd = false;
//Трек-система для отслеживания международных отправлений
	String track = null;
	TrackingNumber trackingNumber = null;
	public DataBaseUtilites dataBaseUtilites = new DataBaseUtilites();
	public boolean access = true;
	public String adminPassword = null;
	public boolean admin = false;

	List<TrackingNumber> trackingNumbersList = null;
	FacesContext context = FacesContext.getCurrentInstance();
	ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");


	public boolean isDlgAdd() {
		return dlgAdd;
	}

	public void setDlgAdd(boolean dlgAdd) {
		this.dlgAdd = dlgAdd;
	}

	public void dlgAddShow(){
		setDlgAdd(true);
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('add').show();");
	}

	public void dlgAddHide(){
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('add').hide();");
		setDlgAdd(false);
	}

	public static void main(String[] args){

	}

	public UserInfo loginUser(){
		if(admin) {
			return  dataBaseUtilites.getUserByGoogleId("admin");
		} else {
			return dataBaseUtilites.getUserByGoogleId(profile.getId());
		}
	}

	@PostConstruct
	public void init(){
		System.out.println("HelloBean init");
		dataBaseUtilites = new DataBaseUtilites();
	}

	public void addErrorMessage(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage
				.SEVERITY_WARN, message, "Error"));
	}

	public void loginAdmin() throws IOException {
		if((null == adminPassword) || (adminPassword.trim().equals(""))){
			addErrorMessage(bundle.getString("mustEnterPass.jsf"));
			return;
		}

		if (!adminPassword.equals("12345")){
			addErrorMessage(bundle.getString("incorrctPass.jsf"));
			return;
		}


		admin = true;
		String url = "hello.xhtml";
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}


	public List<TrackingNumber> getTrackingNumbersList() throws InterruptedException {
		return trackingNumbersList;
	}

	public void setTrackingNumbersList(List<TrackingNumber> trackingNumbersList) {
		this.trackingNumbersList = trackingNumbersList;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getEasyTrack() {
		return easyTrack;
	}

	public void setEasyTrack(String easyTrack) {
		this.easyTrack = easyTrack;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DataBaseUtilites getDataBaseUtilites() {
		return dataBaseUtilites;
	}

	public boolean isAccess() {
		return access;
	}

	public void setAccess(boolean access) {
		this.access = access;
	}

	public Person getProfile() {
		return profile;
	}

	public void mes(){
		System.out.println("saveTrackers============================");
	}

	public TrackingNumber getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(TrackingNumber trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTrack() {
		return track;
	}

	public void setTrack(String track) {
		this.track = track;
	}

	public void buttonAction(ActionEvent actionEvent) throws InterruptedException{
		if(track == null){
			addMessage(bundle.getString("trackerNumberNotValid.jsf"));
			return;
		}

		if((track.trim().equals("")) || (track.trim().length() != 13)){
			addMessage(bundle.getString("trackerNumberNotValid.jsf"));
			return;
		}
				//============================================================================"RD503242720CN"
				TrackingNumber tn = new TrackingNumber(track);
				Tracker bp = new BelPostTrack(tn);
				Tracker st = new SeventeenTrack(tn);
				Tracker yt = new YanwenTrack(tn);

				tn.add(bp);
				tn.add(st);
				tn.add(yt);

				tn.update2();
				trackingNumber = tn;
				System.out.println("Results:");
				for(String key: tn.getInfo().keySet()){
					for(TrackInfoRecord record: tn.getInfo().get(key)){
						System.out.println(record.toString());
					}
				}
				//============================================================================
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}