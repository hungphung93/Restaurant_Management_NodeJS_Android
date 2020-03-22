package com.rms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.rms.Data.MenuItems;
import com.rms.adapters.RecycleViewReceiptAdapter;
import com.rms.adapters.TabAdapter;
import com.rms.models.FoodItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    /**
     *
     * This code is in main activity here; however in overall project this code can be put in a new activity such as FoodMenuActivity
     * Same goes with activity_main.xml --> food_menu.xml
     *
     */

    //Declaration
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecycleViewReceiptAdapter mAdapter;
    private RecyclerView mNumbersList;
    private ArrayList<FoodItem> orderedFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        //Tab is dynamic and each tab is according to the food type for now.
        //get food item type for the tab
        MenuItems menuItems = new MenuItems();
        String[] itemTypes = menuItems.getItemType();
        final ArrayList<FoodItem> allfood = menuItems.getFoodItems();


        /**
         * TAB part
         */


        //Get all the itemtypes and create tab.
        for (String c:itemTypes  ) {
            tabLayout.addTab(tabLayout.newTab().setText(c));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        //Create Adapter for viewpager
        //The adapter initializes as many fragment containing recycle View.
        //and the viewpager will be filled with fragment according to the position of tab whenever a tab is selected
        final TabAdapter adapter = new TabAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount(), allfood, itemTypes);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /**
         * Receipt section where all orders is listed.
         */
        TextView subtotalView = (TextView) findViewById(R.id.subtotal);
        TextView HSTView = (TextView) findViewById(R.id.hst);
        TextView totalView = (TextView) findViewById(R.id.totalPrice);

        mNumbersList = (RecyclerView) findViewById(R.id.receipt_items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        mNumbersList.setHasFixedSize(true);

        orderedFood = new ArrayList<>();
        mAdapter = new RecycleViewReceiptAdapter(orderedFood,subtotalView,HSTView,totalView);
        mNumbersList.setAdapter(mAdapter);



    }

    //This method is invoked whenever certain food item is ordered and is invoked from RecycleViewAdapter
    public void orderFood(FoodItem item){
        mAdapter.updateReceiptsList(item);
    }
}