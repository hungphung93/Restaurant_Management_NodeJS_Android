package com.example.restaurantmanagement.Fragments.tableOrder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanagement.Adapter.FoodMenuRecycleViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.IFooItemTypeTabEventListener;
import com.example.restaurantmanagement.EventListenerInterface.ITableOrderEventListener;
import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;

public class FoodListFragment extends Fragment implements ITableOrderEventListener {
    private ArrayList<FoodItem> foodLists;
    private ArrayList<String>  itemTypes;
    private int tabnumber;
    private ArrayList<FoodItem> dynamicfooditem;
    IFooItemTypeTabEventListener listener;
    public FoodListFragment(IFooItemTypeTabEventListener listener, int position, ArrayList<FoodItem> foodLists, ArrayList<String>  itemTypes) {
        // Required empty public constructor
        this.listener = listener;
        this.foodLists = foodLists;
        this.tabnumber = position;
        this.itemTypes = itemTypes;
    }
    private FoodMenuRecycleViewAdapter mAdapter;
    private RecyclerView mNumbersList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.food_list_tab, container, false);
        mNumbersList =(RecyclerView) rootView.findViewById(R.id.rv_numbers);

        dynamicfooditem = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);


        String category = itemTypes.get(tabnumber);
        for (FoodItem item: foodLists) {
            if(item.getFoodType().equals(category)){
                dynamicfooditem.add(item);
            }
        }

        mAdapter = new FoodMenuRecycleViewAdapter(dynamicfooditem, this);
        mNumbersList.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onMenuFoodClick(int clickedItemIndex) {
        listener.menuFoodClick(dynamicfooditem.get(clickedItemIndex));
    }
}