package com.rms.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rms.MainActivity;
import com.rms.R;
import com.rms.adapters.RecycleViewAdapter;
import com.rms.models.FoodItem;

import java.util.ArrayList;

public class FoodListFragment extends Fragment implements RecycleViewAdapter.ListItemClickListener{
    private ArrayList<FoodItem> foodLists;
    private String[] itemTypes;
    private int tabnumber;
    private ArrayList<FoodItem> dynamicfooditem;
    public FoodListFragment(int position, ArrayList<FoodItem> foodLists, String[] itemTypes) {
        // Required empty public constructor
        this.foodLists = foodLists;
        this.tabnumber = position;
        this.itemTypes = itemTypes;
    }
    private RecycleViewAdapter mAdapter;
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



        for (FoodItem item: foodLists) {
            if(item.getFoodType() == itemTypes[tabnumber]){
                dynamicfooditem.add(item);
            }
        }

        System.out.println(dynamicfooditem.size());
        mAdapter = new RecycleViewAdapter(dynamicfooditem, this);
        mNumbersList.setAdapter(mAdapter);

        return rootView;

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ((MainActivity)getActivity()).orderFood(dynamicfooditem.get(clickedItemIndex));
    }
}