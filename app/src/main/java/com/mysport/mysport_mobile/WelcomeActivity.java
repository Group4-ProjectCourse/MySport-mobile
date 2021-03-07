package com.mysport.mysport_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    ImageView myPhoto;
    TextView myName, myEmail;
    Button sign_out_button;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = mAuth.getCurrentUser();

        myPhoto = findViewById(R.id.myPhoto);
        myName = findViewById(R.id.myName);
        myEmail = findViewById(R.id.myEmail);
        sign_out_button = findViewById(R.id.sign_out_button);

        sign_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
            }
        });

        if (mUser != null){
            String name = mUser.getDisplayName();
            String email = mUser.getEmail();
            String photoURL = mUser.getPhotoUrl().toString();
            photoURL = photoURL + "?type=large";
            Glide.with(this).load(photoURL).into(myPhoto);
            myName.setText(name);
            myEmail.setText(email);
        }
    }
}