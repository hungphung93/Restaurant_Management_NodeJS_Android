package com.example.restaurantmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.restaurantmanagement.Enums.Role;
import com.example.restaurantmanagement.Enums.TableStatus;
import com.example.restaurantmanagement.EventListenerInterface.ITableListEventListener;
import com.example.restaurantmanagement.Fragments.OrderedFoodFragment;
import com.example.restaurantmanagement.Fragments.TableFragment;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.LoggingUser;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawer = findViewById(R.id.draw_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,  toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            drawer.addDrawerListener(toggle);
            toggle.syncState();

            navigationView.setCheckedItem(R.id.nav_food);

            Menu menu = navigationView.getMenu();

            if(LoggingUser.getUserInfo().GetRole().equals(Role.Cook.toString()))
                menu.findItem(R.id.nav_table).setVisible(false);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_frame, new OrderedFoodFragment())
                    .commit();

        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = new Fragment();

        switch(menuItem.getItemId()){
            case R.id.nav_table:
                fragment = new TableFragment();
                break;
            case R.id.nav_food:
                fragment = new OrderedFoodFragment();
                break;
            case R.id.nav_logout:
                LoggingUser.logout();
                Intent intent =  new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame, fragment)
                .commit();

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
