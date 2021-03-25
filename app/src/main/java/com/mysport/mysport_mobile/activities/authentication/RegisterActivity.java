package com.mysport.mysport_mobile.activities.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.utils.Networking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "RegisterActivity";
    EditText mFullName, mEmail, mPassword, mPhone, mPersNum;
    Button mRegisterButton;
    TextView mLoginTextView, mCreateAccountTextView;
    //FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    float value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mCreateAccountTextView = findViewById(R.id.create_new_account_text);
        mFullName = findViewById(R.id.full_name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterButton = findViewById(R.id.register_button);
        mLoginTextView = findViewById(R.id.login_here_text);
        mPersNum = findViewById(R.id.personal_number);

        mCreateAccountTextView.setTranslationY(800);
        mFullName.setTranslationX(800);
        mEmail.setTranslationX(800);
        mPassword.setTranslationX(800);
        mPhone.setTranslationX(800);
        mRegisterButton.setTranslationX(800);
        mPersNum.setTranslationX(800);

        mCreateAccountTextView.setAlpha(value);
        mFullName.setAlpha(value);
        mEmail.setAlpha(value);
        mPassword.setAlpha(value);
        mPhone.setAlpha(value);
        mRegisterButton.setAlpha(value);

        mCreateAccountTextView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2400).start();
        mFullName.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        mEmail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1200).start();
        mPhone.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1600).start();
        mRegisterButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();
        mPersNum.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();


//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//        progressBar = findViewById(R.id.progressBar);
//
//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
//            finish();
//        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();
                final String persNum = mPersNum.getText().toString();
                String[] str = fullName.split(" ");
                String firstname = str[0];
                String lastname = str[1];

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //register user in MongoDB
                new Thread(() -> {
                    String url = App.baseURL + "auth/register";
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("firstname", firstname)
                                .put("lastname", lastname)
                                .put("email", email)
                                .put("password", BCrypt.withDefaults().hashToString((int) Math.floor(Math.random() * 3) + 10, password.toCharArray()))
                                .put("personal_number", persNum);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Networking.volleyPost(RegisterActivity.this, url, obj);
//                    Toast.makeText(RegisterActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT)
//                            .show();
                }).start();

                // register the user in firebase

//                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//                            // send verification link
//
//                            FirebaseUser fuser = fAuth.getCurrentUser();
//                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
//                                }
//                            });
//
//                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            Map<String, Object> user = new HashMap<>();
//                            user.put("fName", fullName);
//                            user.put("email", email);
//                            user.put("phone", phone);
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG, "onFailure: " + e.toString());
//                                }
//                            });
//                            startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
//
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
            }
        });

        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
            }
        });
    }
}