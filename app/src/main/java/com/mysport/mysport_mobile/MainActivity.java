package com.mysport.mysport_mobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mysport.mysport_mobile.enums.TransactionAction;
import com.mysport.mysport_mobile.events.OnFragmentSendDataListener;
import com.mysport.mysport_mobile.fragments.calendar.DayViewFragment;
import com.mysport.mysport_mobile.fragments.calendar.MonthViewFragment;
import com.mysport.mysport_mobile.language.LanguageManager;
import com.mysport.mysport_mobile.fragments.ProfileFragment;
import com.mysport.mysport_mobile.fragments.settings.SettingsFragment;
import com.mysport.mysport_mobile.models.MongoActivity;
import com.mysport.mysport_mobile.models.Session;
import com.mysport.mysport_mobile.utils.CalendarUtils;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Calendar;

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
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getTheme().applyStyle(R.style.ThemePurple, true);
        setContentView(R.layout.activity_main);


        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        viewOption = findViewById(R.id.nav_overflow_menu_item);
        toolbar = findViewById(R.id.toolbar);
        circleImageView = findViewById(R.id.profileImage);
        //tool bar
        setSupportActionBar(toolbar);
        //nav drawer menu

        //Hide or show items
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);
        //menu.findItem(R.id.nav_logout).setVisible(false);
        //menu.findItem(R.id.nav_profile).setVisible(false); //If unlogged

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
        circleImageView.setImageURI(App.getSession().getUser().getPhotoUri());

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

    //    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(languageManager.setLocale(base));
//    }

//    @Override
//    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        languageManager.setLocale(this);
//    }
}