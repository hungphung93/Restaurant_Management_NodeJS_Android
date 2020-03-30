package com.example.restaurantmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class TableListActivity extends AppCompatActivity implements ITableListEventListener {

    private String selectedTable = "";
    private final String TABLE_NAME = "TableName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{

            super.onCreate(savedInstanceState);
            setContentView(R.layout.table_list);

            ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables();
            lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onTableStatusClick(Table table) {
        try{

            //Toast.makeText(this, String.format("This table is %s", table.getTableStatus()) , Toast.LENGTH_SHORT).show();

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
}
