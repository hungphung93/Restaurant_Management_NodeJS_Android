package com.example.restaurantmanagement.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restaurantmanagement.EventListenerInterface.ITableCashoutEventListener;
import com.example.restaurantmanagement.Models.Order;
import com.example.restaurantmanagement.Models.TableTransactionDetail;
import com.example.restaurantmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class TableCashOutRecyclerViewAdapter extends RecyclerView.Adapter<TableCashOutRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private TableTransactionDetail transaction;
    private ITableCashoutEventListener mListener;

    private int HSTPercent = 13;

    public TableCashOutRecyclerViewAdapter(Context context, TableTransactionDetail transaction, ITableCashoutEventListener listener) {
        this.context = context;
        this.transaction = transaction;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position){
        return (position == transaction.getOrderedFoods().size()) ? R.layout.fragment_table_cash_out_button : R.layout.fragment_table_cash_out_order_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == R.layout.fragment_table_cash_out_order_item)
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_table_cash_out_order_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_table_cash_out_button, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position == transaction.getOrderedFoods().size()){
            double transactionHST = transaction.getTotalAmount() * HSTPercent / 100;
            double total = transactionHST + transaction.getTotalAmount();

            holder.tvTransactionHST.setText(String.format("%.2f", transactionHST));
            holder.tvTransactionSubtotal.setText(String.format("%.2f", transaction.getTotalAmount()));
            holder.tvTransactionTotal.setText(String.format("%.2f", total));

            holder.tvCashOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onCashOutButtonEventClick();
                }
            });

            return;
        }

        holder.order = transaction.getOrderedFoods().get(position);
        holder.tvFoodName.setText(transaction.getOrderedFoods().get(position).getFoodName());
        holder.tvOrderQuantity.setText(Integer.toString(transaction.getOrderedFoods().get(position).getQuantity()) );
        holder.tvOrderPrice.setText(Double.toString(transaction.getOrderedFoods().get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return transaction.getOrderedFoods().size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView tvFoodName;
        TextView tvOrderQuantity;
        TextView tvOrderPrice;
        TextView tvCashOut;
        TextView tvTransactionHST;
        TextView tvTransactionSubtotal;
        TextView tvTransactionTotal;
        Order order;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvFoodName = (TextView) view.findViewById(R.id.tvFoodName);
            tvOrderQuantity = (TextView) view.findViewById(R.id.tvOrderQuantity);
            tvOrderPrice = (TextView) view.findViewById(R.id.tvOrderPrice);
            tvCashOut = (TextView) view.findViewById(R.id.tvTableDetailCashOut);
            tvTransactionHST = (TextView) view.findViewById(R.id.tvTransactionHST);
            tvTransactionSubtotal = (TextView) view.findViewById(R.id.tvTransactionSubtotal);
            tvTransactionTotal = (TextView) view.findViewById(R.id.tvTransactionTotal);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + (tvFoodName != null ? tvFoodName.getText() : "") + "'";
        }
    }
}
