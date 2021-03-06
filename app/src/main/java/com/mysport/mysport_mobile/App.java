package com.mysport.mysport_mobile;

import android.app.Application;

import android.content.Context;
import android.net.Uri;
import com.mysport.mysport_mobile.language.LanguageManager;
import com.mysport.mysport_mobile.mocks.ProfileLoaderMock;
import com.mysport.mysport_mobile.mocks.SessionMock;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;

import java.net.URL;


public class App extends Application {

    public static App instance = new App();
    //public static final String baseURL = "http://127.0.0.1:3001/";
    public static final String baseURL = "https://frozen-stream-48405.herokuapp.com/";
    private static Session<Member> session;
    private static final ProfileLoaderMock profile;

    static {
        session = new Session<>(null);
        profile = new ProfileLoaderMock();
        //need to figure this out
        //session = new Session<Member>(new Member(2, "John", "Cena", "deniel@mysport-community.com", photo));
    }

    public static Session<Member> getSession() {
        if(session.getUser() == null)
            setSession(SessionMock.instance.getMember());//MOCK
        return session;
    }

    public static void setSession(String firstName, String familyName, String email, Uri photo) {
        session = new Session<>(new Member(1, firstName, familyName, email, photo));
    }

    public static void setSession(Member member) {
        session = new Session<>(member);
    }

    //MOCK
    public static Uri getProfileURL() {
        return profile.getPhoto();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
