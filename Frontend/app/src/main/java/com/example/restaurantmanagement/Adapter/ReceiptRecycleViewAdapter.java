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
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.EventListenerInterface.IReceiptItemEventListener;
import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;


/**
 *Recycle view adapter for list for ordered items
 */

public class ReceiptRecycleViewAdapter extends RecyclerView.Adapter<ReceiptRecycleViewAdapter.NumberViewHolder>{

    private static final String TAG = ReceiptRecycleViewAdapter.class.getSimpleName();



    private ArrayList<FoodItem> foodItems;
    int mNumberItems;
    TextView subtotalView;
    TextView HSTView;
    TextView totalView;
    IReceiptItemEventListener listener;

    ArrayList<Integer> toBeDeleted = new ArrayList<>();

    public boolean setCheckBoxChecked = false;



    public ReceiptRecycleViewAdapter(ArrayList<FoodItem> fooditems, TextView subtotalView, TextView HSTView, TextView totalView, IReceiptItemEventListener listener) {
        mNumberItems = fooditems.size();
        this.foodItems = fooditems;
        this.totalView = totalView;
        this.subtotalView = subtotalView;
        this.HSTView = HSTView;
        this.listener = listener;
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
        holder.itemCheckBox.setChecked(false);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public void updateReceiptsList(FoodItem newItem) {


        foodItems.add(newItem);
        mNumberItems = foodItems.size();
        this.notifyDataSetChanged();
        calculate();
    }

    void calculate(){
        double subtotal = 0;
        for (FoodItem food:foodItems) {
            subtotal += food.getPrice();
        }



        //Also update the receipt total and price
        subtotalView.setText("$"+ subtotal);
        double hst = subtotal*13/100;
        HSTView.setText("$"+hst);
        totalView.setText("$"+(subtotal+hst));
    }


    public void removeItem() {

            int size = foodItems.size();
            for(int i = size-1; i>=0;i--){
                for (int position: toBeDeleted ) {
                System.out.println(position+" "+i);
                if(i==position){
                    this.foodItems.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount() - position);
                    System.out.println("REMOVED"+i);
                }
            }
        }
        mNumberItems = foodItems.size();
        toBeDeleted.clear();

        calculate();
        this.setCheckBoxChecked = false;
        notifyDataSetChanged();
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener, CheckBox.OnCheckedChangeListener {
        TextView food_item;
        TextView food_item_price;
        CheckBox itemCheckBox;


        public NumberViewHolder(View itemView) {
            super(itemView);

            food_item = (TextView) itemView.findViewById(R.id.food_item_name);
            food_item_price = (TextView) itemView.findViewById(R.id.food_item_price);
            itemCheckBox = itemView.findViewById(R.id.item_checkBox);
            itemCheckBox.setChecked(false);
            itemView.setOnLongClickListener(this);
            itemCheckBox.setOnCheckedChangeListener(this);


        }


        void bind(int listIndex) {
            for(int i =0;i<=listIndex;i++) {
                food_item.setText(foodItems.get(i).getName());
                food_item_price.setText(String.valueOf(foodItems.get(i).getPrice()));
                itemCheckBox.setVisibility(setCheckBoxChecked?View.VISIBLE:View.INVISIBLE);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            setCheckBoxChecked = true;
            notifyDataSetChanged();
            listener.onLongClickReceiptItem();
            return true;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(!itemCheckBox.isPressed()) {
                return;
            }

            if(isChecked){
                toBeDeleted.add(getAdapterPosition());
            }else{
                try {
                    toBeDeleted.remove(getAdapterPosition());
                }
                catch (IndexOutOfBoundsException ex){
                    ex.printStackTrace();
                }
            }
            System.out.println(toBeDeleted);
        }
    }
}
