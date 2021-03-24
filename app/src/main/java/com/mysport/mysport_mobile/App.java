package com.mysport.mysport_mobile;

import android.app.Application;

import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;


public class App extends Application {

    public static App instance = new App();
    private static Session session;

    static {
        session = new Session(new Member("012", "Deniel", "Alekseev", "deniel@mysport-community.com"));
    }

    public static Session getSession() {
        return session;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
