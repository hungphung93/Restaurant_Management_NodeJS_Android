package com.example.restaurantmanagement.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<Order> orders;

    public OrderListAdapter(Context context, ArrayList<Order> orders) {
        this.mContext = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_orderlist, parent, false);
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
        holder.orderMenuItem.setText(order.getOrderMenuItem());

        // set the table name
        holder.tableName.setText(order.getTableName());

        // set the order status
        holder.orderStatus.setText(order.getOrderStatus());

        if (order.getOrderStatus().equalsIgnoreCase("On Line")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkYellow));


            holder.orderItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Item is clicked", Toast.LENGTH_SHORT).show();
                    
                    /*
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                    AlertDialog alertDialog = alertBuilder.setPositiveButton("Cook", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "Cook is clicked", Toast.LENGTH_SHORT).show();
                        }
                    }).setNeutralButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(mContext, "Remove is clicked", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
                    alertDialog.show();

                    */
                }
            });
        } else if (order.getOrderStatus().equalsIgnoreCase("Ready")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkGreen));
        } else if (order.getOrderStatus().equalsIgnoreCase("Cooking")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout orderItemContainer;
        CircleImageView orderMenuImage;
        TextView orderMenuItem;
        TextView tableName;
        TextView orderStatus;

        ViewHolder(View itemView) {
            super(itemView);

            orderItemContainer = itemView.findViewById(R.id.order_item_container);
            orderMenuImage = itemView.findViewById(R.id.order_menu_image);
            orderMenuItem = itemView.findViewById(R.id.order_menu_item);
            tableName = itemView.findViewById(R.id.table_name);
            orderStatus = itemView.findViewById(R.id.order_status);
        }
    }
}
