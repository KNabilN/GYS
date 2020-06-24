package com.gys.android.gys.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gys.android.gys.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtName , txtDes , txtPrice;
    public ImageView img;
    public ItemClickListener listener;

    public PlaceViewHolder(@NonNull View itemView) {
        super(itemView);

        txtName = itemView.findViewById(R.id.product_name);
        txtDes = itemView.findViewById(R.id.product_description);
        txtPrice = itemView.findViewById(R.id.product_price);
        img = itemView.findViewById(R.id.product_image);

    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);

    }
}
