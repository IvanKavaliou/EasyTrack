package com.itibo.googleapi;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Person;
import com.itibo.googleapi.core.GoogleLoginService;
import com.itibo.googleapi.core.GoogleOAuthLoginException;
import com.itibo.beans.HelloBean;


import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Properties;

@ManagedBean
@SessionScoped
public class ProfileController {

    private static final long serialVersionUID = 8707726379300120095L;

    @ManagedProperty(value = "#{helloBean}")
    protected HelloBean helloBean;

    @ManagedProperty(value = "#{googleLoginBean}")
    protected GoogleLoginService googleLoginService;

    protected String applicationName;
    private Person profile;

    public HelloBean getHelloBean() {
        return helloBean;
    }

    public void setHelloBean(HelloBean helloBean) {
        this.helloBean = helloBean;
    }

    public ProfileController() throws GoogleOAuthLoginException {
        Properties props = new Properties();
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("com.itibo.web/application.properties"));
            applicationName = props.getProperty("controller.application.name");
        } catch (IOException e) {
            throw new GoogleOAuthLoginException("Unable to initialize", e);
        }
        System.out.println("@ProfileController#Construct");
    }

    public void process() throws Exception {
        try {
            process2(); // run the context
        } catch (GoogleJsonResponseException ex) {
            // json exception something went wrong on google side (bad request, permission denied etc.)
            if (ex.getDetails().getCode() == 403) {
                try {
                    // redirect to google and request new accessToken for added scope
                    googleLoginService.doRedirect();
                } catch (GoogleOAuthLoginException e) {
                    e.printStackTrace();
                }
            } else {
                throw ex;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void process2() throws IOException {
        Plus plus = new Plus.Builder(new NetHttpTransport(), new JacksonFactory(), googleLoginService.getCredential())
                .setApplicationName(applicationName)
                .build();
        profile = plus.people().get("me").execute();
        helloBean.setId(helloBean.getDataBaseUtilites().getUserIdByGoogleId(profile.getId()));
        helloBean.setEmail(helloBean.getDataBaseUtilites().getUserEmailById(helloBean.getId()));
        System.out.println("Id: "+profile.getId());
        System.out.println("Email: "+profile.getEmails().get(0).getValue());

        if(null == helloBean.dataBaseUtilites.getUserIdByGoogleId(profile.getId())){
            helloBean.dataBaseUtilites.addUser(profile.getEmails().get(0).getValue(), profile.getId());
            System.out.println("New user added!");
        } else {
            helloBean.profile = profile;
            System.out.println("User "+profile.getEmails().get(0).getValue()+" login!");
        }

    }

    public Person getProfile() {
        return profile;
    }

    public void setProfile(Person profile) {
        this.profile = profile;
    }

    public void setScopes() {
        googleLoginService.addScope(PlusScopes.PLUS_LOGIN);
    }

    public GoogleLoginService getGoogleLoginService() {
        return googleLoginService;
    }

    public void setGoogleLoginService(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }
}