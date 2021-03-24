package com.mysport.mysport_mobile.models;

import androidx.annotation.NonNull;

public class Member {
    private String firstname;
    private String surname;
    private String email;

    public Member(String firstname, String surname, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
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

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", firstname, surname);
    }
}
