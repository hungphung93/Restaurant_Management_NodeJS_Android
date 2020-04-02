package com.example.restaurantmanagement.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantmanagement.Adapter.MyTableRecyclerViewAdapter;
import com.example.restaurantmanagement.Enums.TableStatus;
import com.example.restaurantmanagement.EventListenerInterface.ITableListEventListener;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;

import java.util.ArrayList;

public class TableFragment extends Fragment implements ITableListEventListener {

    private Context context = null;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ITableListEventListener mListener;

    private ArrayList<Table> ListTable;

    private String selectedTable = "";
    private final String TABLE_NAME = "TableName";

    private RecyclerView view;
    private RecyclerView.Adapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TableFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        ApiResponse<ArrayList<Table>> lstTables = TableServices.getAllTables();
        lstTables.Subscribe(this::handleGetTablesSuccess, this::handleAPIFailure);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (RecyclerView) inflater.inflate(R.layout.fragment_table_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            if (mColumnCount <= 1) {
                view.setLayoutManager(new LinearLayoutManager(context));
            } else {
                view.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof ITableListEventListener) {
            mListener = (ITableListEventListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ITableListEventListener");
        }*/

        mListener = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                goToTableDetail(selectedTable);

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleGetTablesSuccess(BaseResponse<ArrayList<Table>> response){
        try{
            if(!response.IsSuccess())
            {
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            this.ListTable = response.GetData();

            // update Adapter
            adapter = new MyTableRecyclerViewAdapter(context , this.ListTable, mListener);

            view.setAdapter(adapter);

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void handleOpenTableSuccess(BaseResponse<Boolean> response){
        try{
            if(!response.IsSuccess())
            {
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            Boolean isOpened = response.GetData();

            if(isOpened){
                goToTableDetail(selectedTable);
            }

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleAPIFailure(Throwable t){
        Toast.makeText(context, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }

    private void goToTableDetail(String tableName){
        OrderedFoodFragment fragment = new OrderedFoodFragment(tableName);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame, fragment)
                .commit();

    }
}
