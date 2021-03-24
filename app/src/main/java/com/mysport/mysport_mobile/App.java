package com.mysport.mysport_mobile;

import android.app.Application;

import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;


public class App extends Application {

    public static App instance = new App();
    public static final String baseURL = "http://192.168.1.72:3000/";
    private static Session session;

    static {
        session = new Session(new Member("Deniel", "Alekseev", "deniel@mysport-community.com"));
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(String firstName, String familyName, String email) {
        session = new Session(new Member(firstName, familyName, email));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
