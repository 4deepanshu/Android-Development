package com.rg.milkwala.view.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rg.milkwala.R;
import com.rg.milkwala.model.user.Orders;
import com.rg.milkwala.view.controls.TextViewRegular;

import java.util.List;

/**
 * Created by Mobile on 4/11/2017.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {
    List<Orders> ordersList;
    Activity activity;

    public OrderHistoryAdapter(Activity activity, List<Orders> ordersList) {
        this.ordersList = ordersList;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordrehistorylayout, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.prdctName.setText(ordersList.get(position).getProductName());
        holder.prdctQnty.setText(ordersList.get(position).getQuantity()+" items");
        holder.date.setText(ordersList.get(position).getOrdereddate());
        if (ordersList.get(position).isDelivered()) {
            holder.delevryStatus.setText(activity.getString(R.string.delivered));
            holder.delevryStatus.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.delevryStatus.setText(activity.getString(R.string.pending));
            holder.delevryStatus.setTextColor(activity.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextViewRegular prdctName;
        TextViewRegular delevryStatus;
        TextViewRegular prdctQnty;
        TextViewRegular date;

        public ViewHolder(View itemView) {
            super(itemView);
            prdctName = (TextViewRegular) itemView.findViewById(R.id.prdctName);
            delevryStatus = (TextViewRegular) itemView.findViewById(R.id.delevryStatus);
            prdctQnty = (TextViewRegular) itemView.findViewById(R.id.prdctQnty);
            date = (TextViewRegular) itemView.findViewById(R.id.date);
        }
    }
}
