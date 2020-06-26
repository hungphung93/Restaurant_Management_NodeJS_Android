/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.restaurantmanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.restaurantmanagement.EventListenerInterface.ITableOrderEventListener;
import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Utilities.HttpHelper;

import java.util.ArrayList;

/**
 * Recycle view for list of items in menu
 */
public class FoodMenuRecycleViewAdapter extends RecyclerView.Adapter<FoodMenuRecycleViewAdapter.NumberViewHolder> {

    private static final String TAG = FoodMenuRecycleViewAdapter.class.getSimpleName();



    private ArrayList<FoodItem> foodItems;
    int mNumberItems;

    private Context context;
    final private ITableOrderEventListener mOnClickListener;

    public FoodMenuRecycleViewAdapter(ArrayList<FoodItem> fooditems, ITableOrderEventListener listener) {
        mNumberItems = fooditems.size();
        this.foodItems = fooditems;
        this.mOnClickListener = listener;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.food_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder  implements OnClickListener {

        TextView food_item;
        TextView food_price;
        ImageView food_image;



        public NumberViewHolder(View itemView) {
            super(itemView);

            food_item = (TextView) itemView.findViewById(R.id.food_item);
            food_price = itemView.findViewById(R.id.food_price);
            food_image = itemView.findViewById(R.id.food_image);
            itemView.setOnClickListener(this);

        }


        void bind(int listIndex) {
            for(int i =0;i<=listIndex;i++) {
                food_item.setText(foodItems.get(i).getName());
                food_price.setText("$"+foodItems.get(i).getPrice());
                String url = String.format("%simages/%s", HttpHelper.BASE_URL, foodItems.get(i).getUrl());

                // set the image
                Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .preload();

                Glide.with(context)
                        .asBitmap()
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // ALL works here too
                        .into(food_image);
            }

        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMenuFoodClick(clickedPosition);
        }
    }
}
