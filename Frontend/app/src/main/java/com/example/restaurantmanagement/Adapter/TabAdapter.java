package com.example.restaurantmanagement.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restaurantmanagement.Fragments.FoodListFragment;
import com.example.restaurantmanagement.Models.FoodItem;

import java.util.ArrayList;

public class TabAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    ArrayList foodLists;
    String[] itemTypes;

    public TabAdapter(Context context, FragmentManager fm, int totalTabs, ArrayList<FoodItem> foodLists, String[] itemTypes) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.foodLists = foodLists;
        this.itemTypes = itemTypes;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

                FoodListFragment foodList = new FoodListFragment(position,foodLists,itemTypes);
                return foodList;


    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}