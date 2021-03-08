package com.mysport.mysport_mobile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.mysport.mysport_mobile.fragments.calendar.DayFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.mysport.mysport_mobile.fragments.calendar.DayViewFragment;
import com.mysport.mysport_mobile.fragments.calendar.MonthViewFragment;
import com.mysport.mysport_mobile.profile.UserProfile;
import com.mysport.mysport_mobile.fragments.settings.SettingsFragment;
import com.mysport.mysport_mobile.utils.CalendarUtils;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView viewOption, addSport;
    private int currentId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        viewOption = findViewById(R.id.nav_overflow_menu_item);
        addSport = findViewById(R.id.add_sport_button);
        toolbar = findViewById(R.id.toolbar);
        //tool bar
        setSupportActionBar(toolbar);
        //nav drawer menu

        //Hide or show items
        Menu menu = navigationView.getMenu();
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

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //fragment transaction
        handleFragment(new DayViewFragment(), "DAY_VIEW");
        toolbar.setTitle(CalendarUtils.toSimpleString(Calendar.getInstance()));
        //handleFragment(new MonthViewFragment(), "MONTH_VIEW");

        viewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag("DAY_VIEW");
                if(fragment != null && fragment.isVisible()) {
                    handleFragment(new MonthViewFragment(), "MONTH_VIEW");
                    toolbar.setTitle("Calendar View");
                    viewOption.setVisibility(View.GONE);
                    addSport.setVisibility(View.GONE);
                }
            }
        });

        addSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void handleFragment(Fragment fragment){
        handleFragment(fragment, null);
    }

    public void handleFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, fragment, tag == null ? "" : tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if(id == navigationView.getCheckedItem().getItemId())
            Toast.makeText(this, getString(R.string.menu_item_selected_again) + " - " + navigationView.getCheckedItem().getTitle(), Toast.LENGTH_SHORT).show();
        else if(id == R.id.nav_home){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("DAY_VIEW");
            if(fragment != null && fragment.isVisible())
                handleFragment(new MonthViewFragment(), "MONTH_VIEW");
            else
                handleFragment(new DayViewFragment(), "DAY_VIEW");
        }
        else if(id == R.id.nav_settings)
            handleFragment(new SettingsFragment());
        else if (id == R.id.nav_profile)
            handleFragment(new UserProfile());
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
        }
        else if(id == R.id.nav_overflow_menu_item) {
            Toast.makeText(this, "Overflow menu item selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public ImageView getViewOption() {
        return viewOption;
    }

    public ImageView getAddSport() {
        return addSport;
    }
}