package com.mysport.mysport_mobile.activities.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.utils.Networking;

import org.json.JSONException;
import org.json.JSONObject;

import at.favre.lib.crypto.bcrypt.BCrypt;

import static java.lang.System.out;

public class EmailLoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginButton;
    TextView mCreateButton, forgotTextLink, loginToTextView;
    ImageView mImageView;
    ProgressBar indicatorBar;
    TextView statusView;
    int currentValue = 0;
    FirebaseAuth fAuth;
    float value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_email_login);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        //indicatorBar = findViewById(R.id.progressBar);
        statusView = findViewById(R.id.statusView);
        //fAuth = FirebaseAuth.getInstance();
        mLoginButton = findViewById(R.id.login_button);
        mCreateButton = findViewById(R.id.create_account_text);
        forgotTextLink = findViewById(R.id.forgot_password_text);
        loginToTextView = findViewById(R.id.login_to_text_view);
        mImageView = findViewById(R.id.imageView);

        indicatorBar.setVisibility(View.INVISIBLE);
        indicatorBar.setProgress(0);

        loginToTextView.setTranslationY(800);
        mImageView.setTranslationY(800);

        loginToTextView.setAlpha(value);
        mImageView.setAlpha(value);

        loginToTextView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1300).start();
        mImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1600).start();

        mEmail.setTranslationX(800);
        mPassword.setTranslationX(800);
        mLoginButton.setTranslationX(800);

        mEmail.setAlpha(value);
        mPassword.setAlpha(value);
        mLoginButton.setAlpha(value);

        mEmail.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        mPassword.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        mLoginButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1000).start();

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EmailLoginActivity.this, RegisterActivity.class));
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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
                //indicatorBar.setVisibility(View.VISIBLE);

                statusView.post(() -> {
                    String url = App.baseURL + "auth/login";
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("email", email)
                                .put("password", BCrypt.withDefaults().hashToString((int) Math.floor(Math.random() * 3) + 10, password.toCharArray()))
                                .put("mobile", true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Networking.volleyPost(EmailLoginActivity.this, url, obj, new Networking.VolleyCallBack() {
                        @Override
                        public void onSuccess(Member member) {
                            App.setSession(member);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                        @Override
                        public void onError(int statusCode, String message) {
                            Toast.makeText(EmailLoginActivity.this, "Error: " + message, Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                });
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                    // extract the email and send reset link
                    String mail = resetMail.getText().toString();
                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid ->
                            Toast.makeText(EmailLoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e ->
                            Toast.makeText(EmailLoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show());

                });

                passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                    // close the dialog
                });

                passwordResetDialog.create().show();

            }
        });
    }
}



//        mLoginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String email = mEmail.getText().toString().trim();
//                String password = mPassword.getText().toString().trim();
//
//                if(TextUtils.isEmpty(email)){
//                    mEmail.setError("Email is Required.");
//                    return;
//                }
//
//                if(TextUtils.isEmpty(password)){
//                    mPassword.setError("Password is Required.");
//                    return;
//                }
//
//                if(password.length() < 6){
//                    mPassword.setError("Password Must be >= 6 Characters");
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//
//

// authenticate the user

//                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(EmailLoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        }else {
//                            Toast.makeText(EmailLoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//            }
//        });