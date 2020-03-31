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
import android.view.MenuItem;
import android.widget.Toast;

import com.example.restaurantmanagement.Enums.TableStatus;
import com.example.restaurantmanagement.EventListenerInterface.ITableListEventListener;
import com.example.restaurantmanagement.Fragments.TableFragment;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TableListActivity extends AppCompatActivity implements ITableListEventListener , NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate(savedInstanceState);
            setContentView(R.layout.table_list);

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            drawer = findViewById(R.id.draw_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,  toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            drawer.addDrawerListener(toggle);
            toggle.syncState();
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
            case R.id.nav_menu:
                fragment = new TableFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_table_frame, fragment)
                        .commit();
                break;
            case R.id.nav_table:
                fragment = new TableFragment();
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.list_table_frame, fragment)
                        .commit();
                break;
            case R.id.nav_food:
                Intent intent = new Intent(this, OrderListActivity.class);
                startActivity(intent);
        }

        return true;
    }

    @Override
    public void onTableStatusClick(Table table) {

    }
}
