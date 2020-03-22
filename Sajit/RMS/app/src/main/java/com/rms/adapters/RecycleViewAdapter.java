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
package com.rms.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rms.R;
import com.rms.models.FoodItem;

import java.util.ArrayList;

/**
 * Recycle view for list of items in menu
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.NumberViewHolder> {

    private static final String TAG = RecycleViewAdapter.class.getSimpleName();



    private ArrayList<FoodItem> foodItems;
    int mNumberItems;

    private Context context;
    final private ListItemClickListener mOnClickListener;

    //Interface for item click listener
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public RecycleViewAdapter(ArrayList<FoodItem> fooditems, ListItemClickListener listener) {
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



        public NumberViewHolder(View itemView) {
            super(itemView);

            food_item = (TextView) itemView.findViewById(R.id.food_item);
            itemView.setOnClickListener(this);

        }


        void bind(int listIndex) {
            for(int i =0;i<=listIndex;i++) {
                food_item.setText(foodItems.get(i).getName());
            }

            //just to display the color in list of odd items in alternative order
            if(listIndex % 2 != 0) {
                this.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.listColor));
            }
        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
