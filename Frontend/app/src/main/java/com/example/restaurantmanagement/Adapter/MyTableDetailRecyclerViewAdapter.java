package com.example.restaurantmanagement.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.restaurantmanagement.EventListenerInterface.IOrderedFoodListEventListener;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Utilities.HttpHelper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTableDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyTableDetailRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Order> orders;
    private IOrderedFoodListEventListener listener;

    public MyTableDetailRecyclerViewAdapter(Context context, ArrayList<Order> orders, IOrderedFoodListEventListener _listner) {
        this.mContext = context;
        this.orders = orders;
        this.listener = _listner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tabledetail_item, parent, false);
        return new MyTableDetailRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get the order
        Order order = orders.get(position);

        String url = String.format("%simages/%s", HttpHelper.BASE_URL, order.getImageURL());

        // set the image
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload();

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // ALL works here too
                .into(holder.orderMenuImage);

        // set the order menu item name
        holder.foodName.setText(order.getFoodName());

        // set the order status
        holder.orderStatus.setText(order.getFoodStatus());

        if (order.getFoodStatus().equalsIgnoreCase("On Line")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkYellow));
        } else if (order.getFoodStatus().equalsIgnoreCase("Ready")) {
            holder.orderStatus.setTextColor(mContext.getColor(R.color.darkGreen));
        } else if (order.getFoodStatus().equalsIgnoreCase("Cooking")) {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mview;
        CircleImageView orderMenuImage;
        TextView foodName;
        TextView orderStatus;

        ViewHolder(View itemView) {
            super(itemView);

            mview = itemView;
            orderMenuImage = itemView.findViewById(R.id.table_food_image);
            foodName = itemView.findViewById(R.id.table_food_name);
            orderStatus = itemView.findViewById(R.id.table_order_status);
        }
    }
}
