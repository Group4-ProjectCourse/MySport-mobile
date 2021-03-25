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
    private static Uri photo = Uri.parse("app/src/main/res/drawable/profile_image_john_cena.webp");

    static {
        //need to figure this out
        session = new Session(new Member("Deniel", "Alekseev", "deniel@mysport-community.com", photo));
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(String firstName, String familyName, String email, Uri photo) {
        session = new Session(new Member(firstName, familyName, email, photo));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
