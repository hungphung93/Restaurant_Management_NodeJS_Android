package com.example.restaurantmanagement.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.Adapter.OrderListRecyclerViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.GetOrderedFoodRequest;
import com.example.restaurantmanagement.Models.LoggingUser;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.Models.TableTransactionDetail;
import com.example.restaurantmanagement.Models.UserInfo;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.example.restaurantmanagement.Services.Implementation.TransactionServices;

import java.util.ArrayList;

public class OrderedFoodFragment extends Fragment implements IOrderedFoodListEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private IOrderedFoodListEventListener mListener;

    private String tableName;
    private Context context;
    private ArrayList<Order> ListOrderedFood;
    private TextView tvAddOrder;
    private View view;
    private TableTransactionDetail transaction;


    public OrderedFoodFragment(String tableName) { this.tableName = tableName;  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Call API to load ordered foods of the selected table
        ApiResponse<TableTransactionDetail> transaction = TableServices.getTableDetail(new OpenTableRequest(this.tableName));
        transaction.Subscribe(this::handleGetTableTransactionSuccess, this::handleAPIFailure);

        this.ListOrderedFood = TransactionServices.getAllCurrentTransactionFoods();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_orderedfooditem_list, container, false);

        View lstFoodView = view.findViewById(R.id.list_food);

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
                Toast.makeText(context, "Add order is clicked", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onOrderedFoodClick(Order orderedFood) {
        Toast.makeText(context, String.format("%s belong to table %s with status %s is clicked",
                                                orderedFood.getFoodName(),
                                                orderedFood.getTableName(),
                                                orderedFood.getFoodStatus()), Toast.LENGTH_SHORT)
                .show();
    }

    private void handleGetTableTransactionSuccess(BaseResponse<TableTransactionDetail> response){
        try{
            if(!response.IsSuccess()){
                Toast.makeText(context, response.GetMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            // set adapter to bind data to UI
            // Refractor later to separate ordered food page from table detail page
            transaction = response.GetData();

            ApiResponse<ArrayList<Order>> lstOrderedFoods = TableServices.getAllOrderedFoodByRole(
                                                                    new GetOrderedFoodRequest(LoggingUser.getUserInfo().GetRole()));
            lstOrderedFoods.Subscribe(this::handleGetAllOrderedFoodSuccess, this::handleAPIFailure);
        }catch(Exception ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void handleGetAllOrderedFoodSuccess(BaseResponse<ArrayList<Order>> response) {
        ArrayList<Order> lstAllOrders = response.GetData();

        lstAllOrders.removeIf(x -> !x.getTableName().equals(transaction.getTableName()));

        this.ListOrderedFood = lstAllOrders;

        RecyclerView lstFoodView = (RecyclerView) view.findViewById(R.id.list_food);
        lstFoodView.setAdapter(new OrderListRecyclerViewAdapter(context, this.ListOrderedFood, mListener));

        /*
        if(tableName.equals("Table #1"))
            this.ListOrderedFood = TransactionServices.getAllCurrentTransactionFoods();
        else
            this.ListOrderedFood = transaction.getOrderedFoods();
         */
    }

    private void handleAPIFailure(Throwable t){
        Toast.makeText(context, "Internal error happened. Please try later.",
                Toast.LENGTH_LONG).show();
    }
}
