package com.mysport.mysport_mobile;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mysport.mysport_mobile.activities.authentication.LoginActivity;
import com.mysport.mysport_mobile.chat.AppConfig;
import com.mysport.mysport_mobile.enums.TransactionAction;
import com.mysport.mysport_mobile.events.OnFragmentSendDataListener;
import com.mysport.mysport_mobile.fragments.ProfileFragment;
import com.mysport.mysport_mobile.fragments.calendar.DayViewFragment;
import com.mysport.mysport_mobile.fragments.calendar.MonthViewFragment;
import com.mysport.mysport_mobile.fragments.forum.ForumFragment;
import com.mysport.mysport_mobile.fragments.settings.SettingsFragment;
import com.mysport.mysport_mobile.language.LanguageManager;
import com.mysport.mysport_mobile.models.MongoActivity;
import com.mysport.mysport_mobile.utils.CalendarUtils;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnFragmentSendDataListener<MongoActivity> {
    private DayViewFragment dayViewFragment;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Button viewOption;
    private int currentId;
    private FirebaseAuth mAuth;
    private LanguageManager languageManager;
    private TextView name;
    private CircleImageView circleImageView;
    private String TAG = "Chat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getTheme().applyStyle(R.style.ThemePurple, true);
        setContentView(R.layout.activity_main);

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(AppConfig.REGION).build();

        CometChat.init(this, AppConfig.APP_ID,appSettings, new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d(TAG, "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d(TAG, "Initialization failed with exception: " + e.getMessage());
            }
        });


        //android notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationChannel channel = new NotificationChannel("My notification", "My notification", IMPORTANCE_DEFAULT)
            NotificationChannel channel = new NotificationChannel("MS_Notification", "MySport Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        viewOption = findViewById(R.id.nav_overflow_menu_item);
        toolbar = findViewById(R.id.toolbar);

        //tool bar
        setSupportActionBar(toolbar);
        //nav drawer menu

        //Hide or show items
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        //menu.findItem(R.id.nav_logout).setVisible(false);
        //menu.findItem(R.id.nav_profile).setVisible(false); //If unlogged

        //find header view
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.textViewName);
        circleImageView = headerView.findViewById(R.id.profileImage);
        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(view -> {
            App.setSession(null);
            startActivity(new Intent(this, LoginActivity.class));

            return false;
        });


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //user photo update
        try {
            Glide.with(this).load(App.getSession().getUser().getPhoto()).into(circleImageView);
        } catch (NullPointerException nullPointerException) {
            Log.e("MyLog", "Photo Uri is null!");
        }

        //load name from session
        name.setText(App.getSession().getUser().getFirstname() + " " + App.getSession().getUser().getSurname());


        System.out.println("Name: " + App.getSession().getUser().getFirstname());

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //fragment transaction
        handleFragment(TransactionAction.REPLACE, R.id.main_place_for_fragments, (dayViewFragment = new DayViewFragment(Calendar.getInstance())), "DAY_VIEW");
        toolbar.setTitle(CalendarUtils.toSimpleString(Calendar.getInstance()));
        //handleFragment(new MonthViewFragment(), "MONTH_VIEW");

        viewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = getSupportFragmentManager().findFragmentByTag("DAY_VIEW");
//                if(fragment != null && fragment.isVisible()) {
//                    handleFragment(TransactionAction.REPLACE, R.id.main_place_for_fragments, new MonthViewFragment(), "MONTH_VIEW");
//                    toolbar.setTitle("Calendar View");
//                    viewOption.setVisibility(View.INVISIBLE);
//                    viewOption.setClickable(false);
//                }
                handleFragment(TransactionAction.REPLACE, R.id.main_place_for_fragments, new MonthViewFragment(), "MONTH_VIEW");
                toolbar.setTitle("Calendar View");
                viewOption.setVisibility(View.INVISIBLE);
                viewOption.setClickable(false);
            }
        });
    }

    public void handleFragment(int containerViewId, Fragment fragment) {
        handleFragment(TransactionAction.REPLACE, containerViewId, fragment, null);
    }

    public void handleFragment(TransactionAction action, int containerViewId, Fragment fragment) {
        handleFragment(action, containerViewId, fragment, null);
    }

    public void handleFragment(TransactionAction action, int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        switch (action) {
            case ADD:
                transaction.add(containerViewId, fragment, tag == null ? "" : tag);
                break;
            case REMOVE:
                transaction.remove(fragment);
                break;
            case ATTACH:
                transaction.attach(fragment);
                break;
            case DETACH:
                transaction.detach(fragment);
                break;
            case HIDE:
                transaction.hide(fragment);
                break;
            default:
                transaction.replace(containerViewId, fragment, tag == null ? "" : tag);
        }
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == navigationView.getCheckedItem().getItemId())
            Toast.makeText(this, getString(R.string.menu_item_selected_again) + " - " + navigationView.getCheckedItem().getTitle(), Toast.LENGTH_SHORT).show();
        else if (id == R.id.nav_home)
            handleFragment(TransactionAction.REPLACE, R.id.main_place_for_fragments, new MonthViewFragment(), "MONTH_VIEW");
        else if (id == R.id.nav_calendar)
            handleFragment(TransactionAction.REPLACE, R.id.main_place_for_fragments, (dayViewFragment = new DayViewFragment(Calendar.getInstance())), "DAY_VIEW");
        else if (id == R.id.nav_settings)
            handleFragment(R.id.main_place_for_fragments, new SettingsFragment());
        else if (id == R.id.nav_forum)
            handleFragment(R.id.main_place_for_fragments, new ForumFragment());
        else if (id == R.id.nav_profile)
            handleFragment(R.id.main_place_for_fragments, new ProfileFragment());
        else if (id == R.id.nav_share)
            Toast.makeText(this, R.string.message_share_example, Toast.LENGTH_SHORT).show();
//        else if (id == R.id.nav_logout)
//            FirebaseAuth.getInstance().signOut();

        toolbar.setTitle(navigationView.getCheckedItem().getTitle());
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            finish();
            return true;
        } else if (id == R.id.nav_overflow_menu_item) {
            Toast.makeText(this, "Overflow menu item selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public Button getViewOption() {
        return viewOption;
    }

    @Override
    public void onSendData(MongoActivity sport) {
        dayViewFragment.receiveItem(sport);
    }

    public DayViewFragment setDayViewFragment(DayViewFragment dayViewFragment) {
        this.dayViewFragment = dayViewFragment;

        return dayViewFragment;
    }

    public DayViewFragment getDayViewFragment() {
        return dayViewFragment;
    }

    public void makeNotice(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MS_Notification");
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_sports);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1, builder.build());
    }

    //    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(languageManager.setLocale(base));
//    }

//    @Override
//    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        languageManager.setLocale(this);
//    }
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LanguageManager.onAttach(newBase));
//    }
}