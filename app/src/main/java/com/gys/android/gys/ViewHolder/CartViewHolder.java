package com.gys.android.gys.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.gys.android.gys.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtName , txtPrice;
    public ItemClickListener listener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.name_rec);
        txtPrice = itemView.findViewById(R.id.price_rec);

    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);

    }
}
