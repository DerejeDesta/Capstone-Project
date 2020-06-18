package com.example.onlinestore.Classes;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtItemName, txtItemPrice, txtItemQuantity;
    private ItemClickListner itemClickListner;


    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtItemName = itemView.findViewById(R.id.cart_item_name);
        txtItemPrice = itemView.findViewById(R.id.cart_item_price);
        txtItemQuantity = itemView.findViewById(R.id.cart_item_quantity);
    }

    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}