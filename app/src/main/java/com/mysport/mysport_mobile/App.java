package com.mysport.mysport_mobile;

import android.app.Application;

import com.mysport.mysport_mobile.injection.AppComponent;
import com.mysport.mysport_mobile.injection.AppModule;

public class App extends Application {

    private static App singleton;
    private AppComponent component;

    public App() {
        singleton = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //setComponent(createComponent());
    }

    public static AppComponent getComponent() {
        return singleton.component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }

//    public AppComponent createComponent() {
//        return DaggerAppComponent.builder().appModule(new AppModule(this)).build();
//    }
}
