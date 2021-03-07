package com.mysport.mysport_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.mysport.mysport_mobile.calendar.DayFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.mysport.mysport_mobile.profile.UserProfile;
import com.mysport.mysport_mobile.settings.SettingsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private int currentId;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
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
        handleFragment(new DayFragment());
    }

    private void handleFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frameLayout, fragment);
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
        else if(id == R.id.nav_home)
            handleFragment(new DayFragment());
        else if(id == R.id.nav_settings)
            handleFragment(new SettingsFragment());
        else if (id == R.id.nav_profile)
            handleFragment(new UserProfile());
        else if (id == R.id.nav_share)

            Toast.makeText(this, R.string.message_share_example, Toast.LENGTH_SHORT).show();

//        else if (id == R.id.nav_logout)
//            FirebaseAuth.getInstance().signOut();


//        switch (menuItem.getItemId()){
//            case R.id.nav_home:
//            break;
//
//            case R.id.nav_settings:
//                Intent intent = new Intent(this, SettingActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.nav_share:
//                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
//                break;
//        }




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
        switch (item.getItemId()){
            case R.id.nav_logout:
                mAuth.signOut();
                LoginManager.getInstance().logOut();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}