package com.example.restaurantmanagement.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.restaurantmanagement.EventListenerInterface.IFooItemTypeTabEventListener;
import com.example.restaurantmanagement.Fragments.tableOrder.FoodListFragment;
import com.example.restaurantmanagement.Models.FoodItem;

import java.util.ArrayList;

public class FoodTypeTabAdapter extends FragmentStatePagerAdapter {

    private Context myContext;
    int totalTabs;
    ArrayList foodLists;
    ArrayList<String>  itemTypes;
    IFooItemTypeTabEventListener listener;

    public FoodTypeTabAdapter(IFooItemTypeTabEventListener listener, Context context, FragmentManager fm, int totalTabs, ArrayList<FoodItem> foodLists, ArrayList<String> itemTypes) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.foodLists = foodLists;
        this.itemTypes = itemTypes;
        this.listener = listener;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

                FoodListFragment foodList = new FoodListFragment(listener, position,foodLists,itemTypes);
                return foodList;


    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}