package com.rg.milkwala.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rg.milkwala.MyApplication;
import com.rg.milkwala.R;
import com.rg.milkwala.model.user.product.Product;
import com.rg.milkwala.view.activities.DescriptionActivity;
import com.rg.milkwala.view.controls.TextViewBold;
import com.rg.milkwala.view.controls.TextViewRegular;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mobile on 1/10/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<Product> productList = new ArrayList<>();
    private Context context;

    public HomeAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homeadapter, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String withTwoDecimalPoint = df.format(productList.get(position).getOnetimePrice());
        Picasso.with(context).load(productList.get(position).getImage1()).into(holder.imageViewIcon);
        holder.textViewTitle.setText(productList.get(position).getName());
        holder.textViewDescription.setText(productList.get(position).getDescription());
        holder.textViewPrice.setText(withTwoDecimalPoint+""+"RS");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.SELECTED_PRODUCT=productList.get(position);
                context.startActivity(new Intent(context, DescriptionActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewIcon)
        ImageView imageViewIcon;
        @BindView(R.id.textViewTitle)
        TextViewBold textViewTitle;
        @BindView(R.id.textViewDescription)
        TextViewRegular textViewDescription;
        @BindView(R.id.textViewPrice)
        TextViewBold textViewPrice;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
