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

import com.example.restaurantmanagement.Adapter.OrderListRecyclerViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.ChangeStatusOfOrderRequest;
import com.example.restaurantmanagement.Models.GetOrderedFoodRequest;
import com.example.restaurantmanagement.Models.LoggingUser;
import com.example.restaurantmanagement.Models.OpenTableRequest;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;

import java.util.ArrayList;

public class OrderedFoodFragment extends Fragment implements IOrderedFoodListEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private IOrderedFoodListEventListener mListener;

    private Context context;
    private ArrayList<Order> ListOrderedFood;
    private View view;
    private OrderListRecyclerViewAdapter adapter;

    public OrderedFoodFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        ApiResponse<ArrayList<Order>> lstOrderedFoods = TableServices.getAllOrderedFoodByRole(
                new GetOrderedFoodRequest(LoggingUser.getUserInfo().GetRole()));
        lstOrderedFoods.Subscribe(this::handleGetAllOrderedFoodSuccess, this::handleAPIFailure);
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

        ApiResponse<Boolean> isUpdated = TableServices.changeStatusOfOrder(new ChangeStatusOfOrderRequest(orderedFood.getTransactionId(),
                                                                                                          orderedFood.getOrderId(),
                                                                                                          orderedFood.getFoodStatus()));
        isUpdated.Subscribe(this::handleChangeOrderStatusSuccess, this::handleAPIFailure);
    }

    private void handleChangeOrderStatusSuccess(BaseResponse response) {
        if(!response.IsSuccess()){
            Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        // After update status successfully, reload the list
        ApiResponse<ArrayList<Order>> lstOrderedFoods = TableServices.getAllOrderedFoodByRole(
                new GetOrderedFoodRequest(LoggingUser.getUserInfo().GetRole()));
        lstOrderedFoods.Subscribe(this::handleGetAllOrderedFoodSuccess, this::handleAPIFailure);
    }

    private void handleGetAllOrderedFoodSuccess(BaseResponse<ArrayList<Order>> response) {
        if(!response.IsSuccess()){
            Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Order> lstAllOrders = response.GetData();

        this.ListOrderedFood = lstAllOrders;

        RecyclerView lstFoodView = (RecyclerView) view.findViewById(R.id.list_food);

        lstFoodView.setAdapter(new OrderListRecyclerViewAdapter(context, this.ListOrderedFood, mListener));
    }

    private void handleAPIFailure(Throwable t){
        Toast.makeText(context, "Internal error happened. Pccclease try later.",
                Toast.LENGTH_LONG).show();
    }
}
