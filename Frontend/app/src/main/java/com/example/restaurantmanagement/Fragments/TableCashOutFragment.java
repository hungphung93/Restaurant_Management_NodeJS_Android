package com.example.restaurantmanagement.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantmanagement.Adapter.TableCashOutRecyclerViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.ITableCashoutEventListener;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.TableTransactionDetail;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.example.restaurantmanagement.Utilities.LoadingSpinnerHelper;

import java.util.ArrayList;

public class TableCashOutFragment extends Fragment implements ITableCashoutEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ITableCashoutEventListener mListener;
    private String tableName;
    private Context context;
    private View view;

    private TableTransactionDetail transaction;


    public TableCashOutFragment(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table_cash_out, container, false);

        context = view.getContext();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Display loading spinner before calling API
            LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

            ApiResponse<TableTransactionDetail> transaction = TableServices.getOrderSummary(new OpenTableRequest(this.tableName));
            transaction.Subscribe(this::handleGetTableTransactionSuccess, this::handleAPIFailure);
        }
        return view;
    }

    private void handleGetTableTransactionSuccess(BaseResponse<TableTransactionDetail> response) {
        try{
            LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

            if(!response.IsSuccess())
            {
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            transaction = response.GetData();

            ((RecyclerView) view).setAdapter(new TableCashOutRecyclerViewAdapter(context, this.transaction, mListener));
        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleAPIFailure(Throwable throwable) {
        LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

        Toast.makeText(context, "Internal error happened. Please try later.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mListener = this;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCashOutButtonEventClick() {
        // Display loading spinner before calling API
        LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

        ApiResponse<Boolean> isTableClosed = TableServices.closeTable(new OpenTableRequest(this.tableName));
        isTableClosed.Subscribe(this::handleCloseTableSuccess, this::handleAPIFailure);
    }

    private void handleCloseTableSuccess(BaseResponse<Boolean> response) {
        try{
            LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

            if(!response.IsSuccess()){
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            TableFragment fragment = new TableFragment();

            getFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_frame, fragment)
                    .commit();

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
