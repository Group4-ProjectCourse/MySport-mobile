package com.mysport.mysport_mobile;

import android.app.Application;

import android.net.Uri;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;

import java.net.URL;


public class App extends Application {

    public static App instance = new App();
    public static final String baseURL = "http://192.168.1.72:3000/";
    private static Session session;

    static {
        //need to figure this out
        session = new Session(new Member("Deniel", "Alekseev", "deniel@mysport-community.com"));
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(String firstName, String familyName, String email, Uri photoUrl) {
        session = new Session(new Member(firstName, familyName, email, photoUrl));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
