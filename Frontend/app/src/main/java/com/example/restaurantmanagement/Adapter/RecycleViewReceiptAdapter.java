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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;


/**
 *Recycle view adapter for list for ordered items
 */

public class RecycleViewReceiptAdapter extends RecyclerView.Adapter<RecycleViewReceiptAdapter.NumberViewHolder> {

    private static final String TAG = RecycleViewReceiptAdapter.class.getSimpleName();



    private ArrayList<FoodItem> foodItems;
    int mNumberItems;

    TextView subtotalView;
    TextView HSTView;
    TextView totalView;

    public RecycleViewReceiptAdapter(ArrayList<FoodItem> fooditems,TextView subtotalView,TextView HSTView,TextView totalView) {
        mNumberItems = fooditems.size();
        this.foodItems = fooditems;
        this.totalView = totalView;
        this.subtotalView = subtotalView;
        this.HSTView = HSTView;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.receipt_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void updateReceiptsList(FoodItem newItem) {


        foodItems.add(newItem);
        mNumberItems = foodItems.size();
        double subtotal = 0;
        for (FoodItem food:foodItems) {

            System.out.println("food name" +food.getName());
            subtotal += food.getPrice();
        }
        this.notifyDataSetChanged();


        //Also update the receipt total and price


        subtotalView.setText("$"+ subtotal);
        double hst = subtotal*13/100;
        HSTView.setText("$"+hst);
        totalView.setText("$"+(subtotal+hst));
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {
        TextView food_item;
        TextView food_item_price;



        public NumberViewHolder(View itemView) {
            super(itemView);

            food_item = (TextView) itemView.findViewById(R.id.food_item_name);
            food_item_price = (TextView) itemView.findViewById(R.id.food_item_price);

        }


        void bind(int listIndex) {
            for(int i =0;i<=listIndex;i++) {
                food_item.setText(foodItems.get(i).getName());
                food_item_price.setText(String.valueOf(foodItems.get(i).getPrice()));
            }
        }
    }
}
