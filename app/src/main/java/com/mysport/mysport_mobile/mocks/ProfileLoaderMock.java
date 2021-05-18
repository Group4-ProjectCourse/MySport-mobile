package com.mysport.mysport_mobile.mocks;

import android.net.Uri;
import android.util.Log;

public class ProfileLoaderMock {
    private final Uri photo = Uri.parse("app/src/main/res/drawable/profile_image_john_cena.webp");

    public Uri getPhoto() {
        Log.d("ProfileLoaderMock", "Loading user image from database");
        return photo;
    }
}
