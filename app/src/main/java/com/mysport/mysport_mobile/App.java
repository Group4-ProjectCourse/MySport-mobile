package com.mysport.mysport_mobile;

import android.app.Application;

import android.content.Context;
import com.mysport.mysport_mobile.language.LanguageManager;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.MongoManager;
import com.mysport.mysport_mobile.models.Session;


public class App extends Application {

    public static App instance = new App();
    private static MongoManager mongoManager;
    private static Session session;

    static {
        mongoManager = new MongoManager();
        session = new Session(new Member("012", "Deniel", "Alekseev", "deniel@mysport-community.com"));
    }

    public static MongoManager getMongo() {
        return mongoManager;
    }

    public static Session getSession() {
        return session;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
