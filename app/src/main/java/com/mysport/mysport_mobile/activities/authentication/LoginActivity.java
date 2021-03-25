package com.mysport.mysport_mobile.activities.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.facebook.FacebookSdk;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.Session;


public class LoginActivity extends AppCompatActivity {

    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private TextView textViewUser;
    private ImageView mLogo, mImageView;
    private LoginButton mLoginButton;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "Facebook Authentication";
    private Button mEmailButton;
    private float value = 0;
    private SignInButton mGoogleButton;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        textViewUser = findViewById(R.id.full_name);
        mLogo = findViewById(R.id.profile_image);

        mImageView = findViewById(R.id.imageView);
        mImageView.setTranslationY(800);
        mImageView.setAlpha(value);
        mImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(2000);

        mEmailButton = findViewById(R.id.email_button);
        mLoginButton = findViewById(R.id.login_button);
        mGoogleButton = findViewById(R.id.sign_in_button);

        mEmailButton.setTranslationX(800);
        mGoogleButton.setTranslationX(800);
        mLoginButton.setTranslationX(800);

        mEmailButton.setAlpha(value);
        mGoogleButton.setAlpha(value);
        mLoginButton.setAlpha(value);

        mEmailButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        mGoogleButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        mLoginButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(1500).start();


        mEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, EmailLoginActivity.class));
            }
        });

        videoBG = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.crossfit);

        videoBG.setVideoURI(uri);
        videoBG.start();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleButton.setOnClickListener(view -> {
            signIn();
        });

        // https://developer.android.com/reference/android/widget/VideoView.html
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                mMediaPlayer.setLooping(true);

                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

        // https://firebase.google.com/docs/auth/android/facebook-login
        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(LoginActivity.this);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.setReadPermissions("email", "public_profile");
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError" + error);
            }
        });

        //       authStateListener = new FirebaseAuth.AuthStateListener() {
        //           @Override
        //           public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        //               FirebaseUser user = firebaseAuth.getCurrentUser();
        //               if (user != null){
        //                  updateUI(user);
        //               }
        //               else {
        //                  updateUI(null);
        //               }
        //           }
        //       };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    // mFirebaseAuth.getInstance().signOut();
                }
            }
        };
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                App.setSession(task.getResult().getGivenName(), task.getResult().getFamilyName(), task.getResult().getEmail(), task.getResult().getPhotoUrl());
            } catch (RuntimeException exception) {
                Log.e("MyLog", "No email selected");
            }

//            Log.d("Mylog", "Surname: " + task.getResult().getFamilyName() + "\nName: " + task.getResult().getGivenName() + "\nPhotoUrl" + task.getResult().getPhotoUrl());
            handleSignInResult(task);
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d(TAG, "handleFacebookToken" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "sign in with credential: successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Log.d(TAG, "sign in with credential: failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

            }
        });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUIG(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code = " + e.getStatusCode());
            updateUIG(null);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            //   textViewUser.setText(user.getDisplayName());
            //   if (user.getPhotoUrl() != null){
            //       String photoUrl = user.getPhotoUrl().toString();
            //       photoUrl = photoUrl + "?type=large";
            //       Picasso.get().load(photoUrl).into(mLogo);
            //   }
        } else {
            //  textViewUser.setText("");
            //  mLogo.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
            Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUIG(GoogleSignInAccount account) {
        if (account != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // mFirebaseAuth.addAuthStateListener(authStateListener);
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null)
//            updateUIG(account);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoBG.start();
    }

    //   @Override
    //   protected void onStop() {
    //       super.onStop();
    //       if (authStateListener != null){
    //           mFirebaseAuth.removeAuthStateListener(authStateListener);
    //       }
//
    //   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

}