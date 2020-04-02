package com.example.restaurantmanagement.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderListRecyclerViewAdapter extends RecyclerView.Adapter<OrderListRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<Order> orders;
    private IOrderedFoodListEventListener listener;

    public OrderListRecyclerViewAdapter(Context context, ArrayList<Order> orders, IOrderedFoodListEventListener _listner) {
        this.mContext = context;
        this.orders = orders;
        this.listener = _listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_orderedfooditem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        // Get the order
        Order order = orders.get(position);

        // set the image
        Glide.with(mContext).asBitmap().load(order.getImage()).into(holder.orderMenuImage);

        // set the order menu item name
        holder.foodName.setText(order.getFoodName());

        // set the table name
        holder.tableName.setText(order.getTableName());

        // set the order status
        holder.orderStatus.setText(order.getOrderStatus());

        if (order.getOrderStatus().equalsIgnoreCase("On Line")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkYellow));
        } else if (order.getOrderStatus().equalsIgnoreCase("Ready")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkGreen));
        } else if (order.getOrderStatus().equalsIgnoreCase("Cooking")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.blue));
        }

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onOrderedFoodClick(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        View mview;
        CircleImageView orderMenuImage;
        TextView foodName;
        TextView tableName;
        TextView orderStatus;

        ViewHolder(View itemView) {
            super(itemView);

            mview = itemView;
            orderMenuImage = itemView.findViewById(R.id.order_menu_image);
            foodName = itemView.findViewById(R.id.food_name);
            tableName = itemView.findViewById(R.id.table_name);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
