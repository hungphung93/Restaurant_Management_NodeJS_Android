package com.example.restaurantmanagement.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class TableListActivity extends AppCompatActivity implements ITableListEventListener, NavigationView.OnNavigationItemSelectedListener {

    private String selectedTable = "";
    private final String TABLE_NAME = "TableName";

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

            ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables();
            lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);
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
    public void onTableStatusClick(Table table) {
        try{
            selectedTable = table.getTableName();

            if(table.getTableStatus().equals(TableStatus.EMPTY.toString()))
            {
                ApiResponse<Boolean> isOpened = TableServices.openTable(new OpenTableRequest(table.getTableName()));
                isOpened.Subscribe(this::handleOpenTableSuccess, this::handleAPIFailure);
            }
            else
                goToTableDetail();

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void goToTableDetail(){
        Intent intent = new Intent(this, OrderListActivity.class);
        intent.putExtra(TABLE_NAME, selectedTable);
        startActivity(intent);
    }

    private void handleGetTablesSuccess(BaseResponse<ArrayList<Table>> response){
        try{
            if(!response.IsSuccess())
            {
                Toast.makeText(getApplicationContext(), response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            ArrayList<Table> lstTable = response.GetData();
            TableFragment tableFragment = new TableFragment(lstTable);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_table_frame, tableFragment)
                    .commit();
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void handleOpenTableSuccess(BaseResponse<Boolean> response){
        try{
            if(!response.IsSuccess())
            {
                Toast.makeText(getApplicationContext(), response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            Boolean isOpened = response.GetData();

            if(isOpened){
                goToTableDetail();
            }

        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleAPIFailure(Throwable t){
        Toast.makeText(this, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = new Intent();
        switch(menuItem.getItemId()){
            case R.id.nav_menu:
                intent = new Intent(this, TableListActivity.class);
                break;
            case R.id.nav_table:
                intent = new Intent(this, TableListActivity.class);
                break;
            case R.id.nav_food:
                intent = new Intent(this, OrderListActivity.class);
        }

        startActivity(intent);
        return true;
    }
}
