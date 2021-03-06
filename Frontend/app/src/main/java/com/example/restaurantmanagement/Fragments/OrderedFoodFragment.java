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
import com.example.restaurantmanagement.Enums.OrderStatus;
import com.example.restaurantmanagement.Enums.Role;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.ChangeStatusOfOrderRequest;
import com.example.restaurantmanagement.Models.GetOrderedFoodRequest;
import com.example.restaurantmanagement.Models.LoggingUser;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.example.restaurantmanagement.Utilities.HttpHelper;
import com.example.restaurantmanagement.Utilities.LoadingSpinnerHelper;
import com.example.restaurantmanagement.Utilities.SocketHelper;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.ArrayList;

public class OrderedFoodFragment extends Fragment implements IOrderedFoodListEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private IOrderedFoodListEventListener mListener;

    private Context context;
    private ArrayList<Order> ListOrderedFood = new ArrayList<>();
    private View view;
    private OrderListRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    public OrderedFoodFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        // Display loading spinner before calling API
        LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

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

        recyclerView = (RecyclerView) view.findViewById(R.id.list_food);
        adapter = new OrderListRecyclerViewAdapter(context, this.ListOrderedFood, mListener);
        recyclerView.setAdapter(adapter);

        Socket socket = SocketHelper.getSocket();
        //socket.emit("food-added");
        socket.on("update", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                if(getActivity() == null)
                    return;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("update");
                        update();
                    }
                });
            }
        });
        return view;
    }

   private void update(){
       // Display loading spinner before calling API
       LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

       ApiResponse<ArrayList<Order>> lstOrderedFoods = TableServices.getAllOrderedFoodByRole(
               new GetOrderedFoodRequest(LoggingUser.getUserInfo().GetRole()));
       lstOrderedFoods.Subscribe(this::handleGetAllOrderedFoodSuccess, this::handleAPIFailure);

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
        if(isValidToChangeOrderStatus(orderedFood)){
            // Display loading spinner before calling API
            LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

            ApiResponse<Boolean> isUpdated = TableServices.changeStatusOfOrder(new ChangeStatusOfOrderRequest(orderedFood.getTransactionId(),
                    orderedFood.getOrderId(),
                    orderedFood.getFoodStatus()));
            isUpdated.Subscribe(this::handleChangeOrderStatusSuccess, this::handleAPIFailure);
        }else
            Toast.makeText(context, String.format("You are not able to change status of %s order", orderedFood.getFoodStatus()), Toast.LENGTH_SHORT).show();
    }

    private boolean isValidToChangeOrderStatus(Order order){

        String a = LoggingUser.getUserInfo().GetRole();

        if(LoggingUser.getUserInfo().GetRole().equals(Role.Waiter.toString()) &&
                (order.getFoodStatus().equals(OrderStatus.ONLINE.toString()) || order.getFoodStatus().equals(OrderStatus.COOKING.toString())))
            return false;

        if(LoggingUser.getUserInfo().GetRole().equals(Role.Cook.toString()) &&
                (order.getFoodStatus().equals(OrderStatus.READY.toString()) || order.getFoodStatus().equals(OrderStatus.SERVED.toString())))
            return false;

        return true;
    }

    private void handleChangeOrderStatusSuccess(BaseResponse response) {
        LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

        if(!response.IsSuccess()){
            Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Socket socket = SocketHelper.getSocket();
        socket.emit("updated");

        // Display loading spinner before calling API
        LoadingSpinnerHelper.displayLoadingSpinner(getActivity());

        // After update status successfully, reload the list
        ApiResponse<ArrayList<Order>> lstOrderedFoods = TableServices.getAllOrderedFoodByRole(
                new GetOrderedFoodRequest(LoggingUser.getUserInfo().GetRole()));
        lstOrderedFoods.Subscribe(this::handleGetAllOrderedFoodSuccess, this::handleAPIFailure);
    }

    private void handleGetAllOrderedFoodSuccess(BaseResponse<ArrayList<Order>> response) {
        LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

        if(!response.IsSuccess()){
            Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
            return;
        }


        ArrayList<Order> lstAllOrders = response.GetData();

        //this.ListOrderedFood = lstAllOrders;
        adapter.updateOrderList(lstAllOrders);
    }

    private void handleAPIFailure(Throwable t){
        LoadingSpinnerHelper.hideLoadingSpinner(getActivity());

        Toast.makeText(context, "Internal error happened. Pccclease try later.",
                Toast.LENGTH_LONG).show();

    }

}
