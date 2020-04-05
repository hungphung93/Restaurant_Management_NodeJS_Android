package com.example.restaurantmanagement.Fragments.tableOrder;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.restaurantmanagement.Adapter.FoodTypeTabAdapter;
import com.example.restaurantmanagement.Adapter.ReceiptRecycleViewAdapter;
import com.example.restaurantmanagement.EventListenerInterface.IFooItemTypeTabEventListener;
import com.example.restaurantmanagement.EventListenerInterface.IReceiptItemEventListener;
import com.example.restaurantmanagement.Fragments.TableDetailFragment;
import com.example.restaurantmanagement.Models.AddOrderToTableRequest;
import com.example.restaurantmanagement.Models.ApiResponse;
import com.example.restaurantmanagement.Models.BaseResponse;
import com.example.restaurantmanagement.Models.FoodItem;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;
import com.example.restaurantmanagement.Services.Implementation.OrderServices;
import com.example.restaurantmanagement.Services.Implementation.TableServices;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;


public class TableOrderFragment extends Fragment implements IReceiptItemEventListener, IFooItemTypeTabEventListener, View.OnClickListener {

    //Declaration
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ReceiptRecycleViewAdapter mAdapter;
    private RecyclerView mNumbersList;
    private ArrayList<FoodItem> orderedFood;
    private ConstraintLayout receipt;
    private LinearLayout menu_container;
    private ImageButton cancelButton;
    private ImageButton deleteButton;
    private Button confirmButton;
    private String tableName;

    private Context context;


    public TableOrderFragment(String tableName){
        this.tableName = tableName;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.table_order, container, false);

        context = getContext();

        //Initialization
        tabLayout=(TabLayout)view.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager);

        //Buttons
        cancelButton = view.findViewById(R.id.cancel_delete);
        deleteButton = view.findViewById(R.id.remove_receipt_items);
        confirmButton = view.findViewById(R.id.buttonOrderConfirm);
        //Initially set these invisible
        cancelButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);


        //set click event listener to buttons

        cancelButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        //Tab is dynamic and each tab is according to the food type for now.
        //get food item type for the tab
        String[] itemTypes = OrderServices.getItemType();

        ArrayList<FoodItem> allfood = OrderServices.getFoodItems();
        ArrayList<FoodItem> orderedItem;



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
        final FoodTypeTabAdapter adapter = new FoodTypeTabAdapter(this, context ,getFragmentManager(), tabLayout.getTabCount(), allfood, itemTypes);
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
        TextView subtotalView = (TextView) view.findViewById(R.id.subtotal);
        TextView HSTView = (TextView) view.findViewById(R.id.hst);
        TextView totalView = (TextView) view.findViewById(R.id.totalPrice);

        mNumbersList = (RecyclerView) view.findViewById(R.id.receipt_items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNumbersList.setLayoutManager(layoutManager);

        mNumbersList.setHasFixedSize(true);

        orderedFood = new ArrayList<>();
        mAdapter = new ReceiptRecycleViewAdapter(orderedFood,subtotalView,HSTView,totalView, this);
        mNumbersList.setAdapter(mAdapter);

        //Set Receipt invisible at the beginning
        receipt = view.findViewById(R.id.receipt);
        menu_container = view.findViewById(R.id.menu_container);
        if(mAdapter.getItemCount()>0){
            receipt.setVisibility(View.VISIBLE);
            menu_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }else{
            receipt.setVisibility(View.GONE);
            menu_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    //This method is invoked whenever certain food item is ordered and is invoked from FoodMenuRecycleViewAdapter
    public void orderFood(FoodItem item){
        mAdapter.updateReceiptsList(item);
        System.out.println(mAdapter.getItemCount());
        if(mAdapter.getItemCount()>0){

            receipt.setVisibility(View.VISIBLE);
            menu_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }else{
            receipt.setVisibility(View.GONE);
            menu_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }


    @Override
    public void menuFoodClick(FoodItem foodItem) {
        orderFood(foodItem);
    }

    @Override
    public void onLongClickReceiptItem() {

        cancelButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);


    }

    public void onClickCancel(){
        cancelButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);

        confirmButton.setVisibility(View.VISIBLE);
        mAdapter.setCheckBoxChecked = false;
        mAdapter.notifyDataSetChanged();
    }

    public void onClickRemoveReceipt(){
        mAdapter.removeItem();
        if(mAdapter.getItemCount()<=0){
            receipt.setVisibility(View.GONE);
            menu_container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        cancelButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove_receipt_items:
                onClickRemoveReceipt();
                break;
            case R.id.cancel_delete:
                onClickCancel();
                break;
            case R.id.buttonOrderConfirm:
                addOrderToTransaction(tableName, orderedFood);
            default:

        }
    }

    private void addOrderToTransaction(String tableName, ArrayList<FoodItem> lstFood){

        AddOrderToTableRequest req =  new AddOrderToTableRequest(tableName, lstFood);

        ApiResponse<Boolean> lstTables = TableServices.addFoodsToTable(req);
        lstTables.Subscribe(this::handleAddOrderToTransactionSuccess, this::handleAPIFailure);
    }

    private void handleAddOrderToTransactionSuccess(BaseResponse response) {
        if(!response.IsSuccess()){
            Toast.makeText(context, response.GetMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        TableDetailFragment fragment = new TableDetailFragment(tableName);

        getFragmentManager().beginTransaction()
                .replace(R.id.main_activity_frame, fragment)
                .commit();
    }

    private void handleAPIFailure(Throwable t){
        Toast.makeText(context, "Internal Error happened.", Toast.LENGTH_SHORT).show();
    }
}
