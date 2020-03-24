package com.example.restaurantmanagement.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.restaurantmanagement.Adapter.OrderListAdapter;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;

public class OrderListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

//        if (getActionBar() != null) getActionBar().setTitle("Order List");

        initRecyclerView();
    }

    // Initialize Recyclerview
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderListAdapter adapter = new OrderListAdapter(this, createDummyOrders());
        recyclerView.setAdapter(adapter);
    }

    // Creating Dummy order list data
    private ArrayList<Order> createDummyOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        orders.add(new Order(R.drawable.img_ice_cream, "Ice Cream", "Table 7", "Served"));
        orders.add(new Order(R.drawable.img_rootbeer, "Root Beer", "Table 5", "Ready"));
        orders.add(new Order(R.drawable.img_chicken_popcorn, "Chicken Popcorn", "Table 3", "Ready"));
        orders.add(new Order(R.drawable.img_cheesecake,"Chocolate Cheesecake", "Table 5", "Cooking"));
        orders.add(new Order(R.drawable.img_chicken_manchurian, "Chicken Manchurian", "Table 3", "On Line"));
        orders.add(new Order(R.drawable.img_iced_tea, "Ice Tea", "Table 4", "Ready"));
        orders.add(new Order(R.drawable.img_french_fries, "French Fries", "Table 5", "Cooking"));
        orders.add(new Order(R.drawable.img_chicken_popcorn,"Fish Popcorn", "Table 1", "Ready"));
        orders.add(new Order(R.drawable.img_iced_tea, "Iced Tea", "Table 8", "Served"));
        orders.add(new Order(R.drawable.img_wine, "Red wine", "Table 9", "On Line"));
        orders.add(new Order(R.drawable.img_ramen, "Ramen", "Table 10", "Cooking"));

        return orders;
    }
}
