package com.example.restaurantmanagement.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Adapter.MyTableDetailRecyclerViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Fragments.tableOrder.TableOrderFragment;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.Models.TableTransactionDetail;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.example.restaurantmanagement.Utilities.LoadingSpinnerHelper;

import java.util.ArrayList;

public class TableDetailFragment extends Fragment implements IOrderedFoodListEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private IOrderedFoodListEventListener mListener;

    private String tableName;
    private Context context;
    private ArrayList<Order> ListOrderedFood;
    private TextView tvAddOrder;
    private TextView tvCashOut;
    private View view;
    private TableTransactionDetail transaction;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TableDetailFragment(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Display loading spinner before calling API
        LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

        // Call API to load ordered foods of the selected table
        ApiResponse<TableTransactionDetail> transaction = TableServices.getTableDetail(new OpenTableRequest(this.tableName));
        transaction.Subscribe(this::handleGetTableTransactionSuccess, this::handleAPIFailure);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabledetail_list, container, false);

        View lstFoodView = view.findViewById(R.id.table_list_food);
        // Set the adapter
        if (lstFoodView instanceof RecyclerView) {
            context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) lstFoodView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        tvAddOrder = (TextView) view.findViewById(R.id.tvAddOrder);
        tvAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TableOrderFragment fragment = new TableOrderFragment(tableName);

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_frame, fragment)
                        .commit();
            }
        });

        tvCashOut = (TextView) view.findViewById(R.id.tvCashOut);
        tvCashOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TableCashOutFragment fragment = new TableCashOutFragment(tableName);

                getFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_frame, fragment)
                        .commit();
            }
        });

        return view;
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

    private void handleGetTableTransactionSuccess(BaseResponse<TableTransactionDetail> response){
        try{
            LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

            if(!response.IsSuccess()){
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            // set adapter to bind data to UI
            transaction = response.GetData();

            this.ListOrderedFood = transaction.getOrderedFoods();

            RecyclerView lstFoodView = (RecyclerView) view.findViewById(R.id.table_list_food);
            lstFoodView.setAdapter(new MyTableDetailRecyclerViewAdapter(context, this.ListOrderedFood, mListener));

        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void handleAPIFailure(Throwable t){
        LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

        Toast.makeText(context, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOrderedFoodClick(Order orderedFood) {
        Toast.makeText(context, String.format("%s belong to table %s with status %s is clicked",
                orderedFood.getFoodName(),
                orderedFood.getTableName(),
                orderedFood.getFoodStatus()), Toast.LENGTH_SHORT)
                .show();

    }
}
