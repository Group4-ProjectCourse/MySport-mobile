package com.mysport.mysport_mobile;

import android.app.Application;

import com.mysport.mysport_mobile.models.MongoManager;


public class App extends Application {

    private static App singleton = new App();
    private static MongoManager mongoManager;

    static {
        mongoManager = new MongoManager();
    }

    public App() {
        singleton = this;
    }

    public static MongoManager getMongo() {
        return mongoManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
