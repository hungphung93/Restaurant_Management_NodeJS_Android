package com.example.restaurantmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanagement.EventListenerInterface.ITableListEventListener;
import com.example.restaurantmanagement.Fragments.TableFragment;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;

import java.util.ArrayList;

public class TableListActivity extends AppCompatActivity implements ITableListEventListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate(savedInstanceState);
            setContentView(R.layout.table_list);

            ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables();
            lstTables.Subscribe(this::handleGetTablesSuccess, this::handleGetTablesError);
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
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

    private void handleGetTablesError(Throwable t){
        Toast.makeText(this, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onTableStatusClick(Table table) {

        Toast.makeText(this, table.getTableStatus(), Toast.LENGTH_LONG).show();
    }
}
