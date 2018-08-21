package com.rg.milkwala.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rg.milkwala.MyApplication;
import com.rg.milkwala.R;
import com.rg.milkwala.view.controls.TextViewBold;
import com.rg.milkwala.view.controls.TextViewRegular;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mobile on 1/16/2017.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    Context context;
    Clicklistner clicklistner;

    public ShoppingCartAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopingcartadapterlayout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Picasso.with(context).load(MyApplication.CART_ITEMS.get(position).getImage()).into(holder.imageViewIcon);
        holder.textViewProductName.setText(MyApplication.CART_ITEMS.get(position).getProductName());
        if (MyApplication.CART_ITEMS.get(position).getMonthly()) {
            holder.textViewSubscription.setText("Monthly Subscription");
            holder.textViewRate.setText(stringWithTwoDecimal(MyApplication.CART_ITEMS.get(position).getMothlyPrice())+ "");
        } else {
            holder.textViewSubscription.setText("Once");
            holder.textViewRate.setText(stringWithTwoDecimal(MyApplication.CART_ITEMS.get(position).getOncePrice())+ "");
        }
        holder.textViewQuantity.setText("Quantity : " + "" + MyApplication.CART_ITEMS.get(position).getQuantity());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              clicklistner.onItemCLick(position,v);
            }
        });

    }

    @Override
    public int getItemCount() {
        return MyApplication.CART_ITEMS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewIcon)
        ImageView imageViewIcon;
        @BindView(R.id.textViewProductName)
        TextViewRegular textViewProductName;
        @BindView(R.id.textViewSubscription)
        TextViewRegular textViewSubscription;
        @BindView(R.id.textViewRate)
        TextViewBold textViewRate;
        @BindView(R.id.textViewQuantity)
        TextViewRegular textViewQuantity;
        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setOnItemClickListner(Clicklistner clickListner) {
        this.clicklistner = clickListner;
    }
    public interface Clicklistner {
        void onItemCLick(int position, View v);
    }

    public String stringWithTwoDecimal(Float value){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String withTwoDecimalPoint = df.format(value);
        return withTwoDecimalPoint;
    }
}
