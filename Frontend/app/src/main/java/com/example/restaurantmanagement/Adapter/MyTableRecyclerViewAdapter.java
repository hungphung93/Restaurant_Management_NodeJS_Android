package com.example.restaurantmanagement.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.restaurantmanagement.EventListenerInterface.ITableListEventListener;
import com.example.restaurantmanagement.Models.Table;
import com.example.restaurantmanagement.R;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTableRecyclerViewAdapter extends RecyclerView.Adapter<MyTableRecyclerViewAdapter.ViewHolder> {

    private final List<Table> lstTable;
    private final ITableListEventListener mListener;

    public MyTableRecyclerViewAdapter(List<Table> items, ITableListEventListener listener) {
        lstTable = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_table, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.table = lstTable.get(position);
        holder.tvTableName.setText(lstTable.get(position).getTableName());

        String openAt = lstTable.get(position).getOpenAt();
        holder.tvOpenAt.setText(openAt);

        holder.btnStatus.setText(lstTable.get(position).getTableStatus());

        if(lstTable.get(position).getTableStatus().equals("Serving"))
            holder.btnStatus.setBackgroundColor(Color.parseColor("#D59E17"));
        else
            holder.btnStatus.setBackgroundColor(Color.parseColor("#0A6D00"));

        holder.btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTableStatusClick(holder.table);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstTable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTableName;
        public final TextView tvOpenAt;
        public final Button btnStatus;
        public Table table;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTableName = (TextView) view.findViewById(R.id.tvTableName);
            tvOpenAt = (TextView) view.findViewById(R.id.tvOpenAt);
            btnStatus = (Button) view.findViewById(R.id.btnStatus);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTableName.getText() + "'";
        }
    }
}
