package com.mysport.mysport_mobile.models;

import android.net.Uri;
import androidx.annotation.NonNull;

public class Member {
    private String firstname;
    private String surname;
    private String email;
    private Uri photo;

    public Member(String firstname, String surname, String email, Uri photo) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.photo = photo;
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

    public Uri getPhoto() {
        return photo;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", firstname, surname);
    }
}
