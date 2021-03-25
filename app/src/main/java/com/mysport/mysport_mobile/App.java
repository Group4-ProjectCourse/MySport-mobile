package com.mysport.mysport_mobile;

import android.app.Application;

import android.content.Context;
import android.net.Uri;
import com.mysport.mysport_mobile.language.LanguageManager;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;


public class App extends Application {

    public static App instance = new App();
    public static final String baseURL = "https://frozen-stream-48405.herokuapp.com/";
    private static Session<Member> session;
    private static Uri photo = Uri.parse("app/src/main/res/drawable/profile_image_john_cena.webp");

    static {
        //need to figure this out
        session = new Session<Member>(new Member(2, "Deniel", "Alekseev", "deniel@mysport-community.com", photo));
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(String firstName, String familyName, String email, Uri photo) {
        session = new Session<Member>(new Member(1, firstName, familyName, email, photo));
    }

    public static void setSession(Member member) {
        session = new Session<Member>(member);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
