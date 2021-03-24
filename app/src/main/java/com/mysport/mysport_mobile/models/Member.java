package com.mysport.mysport_mobile.models;

import android.net.Uri;
import androidx.annotation.NonNull;

public class Member {
    private String firstname;
    private String surname;
    private String email;
    private Uri photoUri;

    public Member(String firstname, String surname, String email, Uri photoUri) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.photoUri = photoUri;
    }

//    public String getUserId() {
//        return userId;
//    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", firstname, surname);
    }
}
