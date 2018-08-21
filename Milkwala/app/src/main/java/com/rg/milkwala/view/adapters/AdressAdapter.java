package com.rg.milkwala.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.rg.milkwala.R;
import com.rg.milkwala.model.user.AdressPrp;
import com.rg.milkwala.view.activities.AddAddressActivity;
import com.rg.milkwala.view.controls.ButtonRegular;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mobile on 1/17/2017.
 */

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder> {

    List<AdressPrp> listAdsress = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    Context context;
    ;
    private View view;
    int SELECTED_POSITION = 0;

    public AdressAdapter(Context context, List<String> keys, List<AdressPrp> listAdsress) {
        this.context = context;
        this.listAdsress = listAdsress;
        this.keys = keys;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addressadapterlayout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.chechkBoxTick.setText(listAdsress.get(position).getAddress() + "," + listAdsress.get(position).getCity() + "-" + listAdsress.get(position).getPincode() + "," + listAdsress.get(position).getCountry());
        if (SELECTED_POSITION == position) {
            holder.chechkBoxTick.setChecked(true);
        } else {
            holder.chechkBoxTick.setChecked(false);
        }
        holder.chechkBoxTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECTED_POSITION = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAdsress.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ButtonRegular buttonClickEdit;
        CheckBox chechkBoxTick;

        public ViewHolder(View itemView) {
            super(itemView);
            buttonClickEdit = (ButtonRegular) itemView.findViewById(R.id.buttonClickEdit);
            chechkBoxTick = (CheckBox) itemView.findViewById(R.id.chechkBoxTick);
            buttonClickEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddAddressActivity.class);
                    intent.putExtra("value", "editAddress");
                    intent.putExtra("address", listAdsress.get(getAdapterPosition()).getAddress());
                    intent.putExtra("city", listAdsress.get(getAdapterPosition()).getCity());
                    intent.putExtra("state", listAdsress.get(getAdapterPosition()).getState());
                    intent.putExtra("pinCode", listAdsress.get(getAdapterPosition()).getPincode());
                    intent.putExtra("country", listAdsress.get(getAdapterPosition()).getCountry());
                    intent.putExtra("latitude", listAdsress.get(getAdapterPosition()).getLatitude());
                    intent.putExtra("longitude", listAdsress.get(getAdapterPosition()).getLongitude());
                    intent.putExtra("key", keys.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }

    }

}
