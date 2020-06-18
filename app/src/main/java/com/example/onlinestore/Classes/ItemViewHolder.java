package com.example.onlinestore.Classes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;

import static androidx.recyclerview.widget.RecyclerView.*;

public class ItemViewHolder extends ViewHolder implements View.OnClickListener
{
    public TextView itemNameTextView,itemDescriptionTextView, itemPriceTextView;
    public ImageView itemImageView;
    public ItemClickListner clickListner;


    public ItemViewHolder(View itemView)
    {
        super(itemView);


        itemImageView = (ImageView) itemView.findViewById(R.id.layout_item_image);
        itemNameTextView = (TextView) itemView.findViewById(R.id.layout_item_name);
        itemDescriptionTextView = (TextView) itemView.findViewById(R.id.layout_item_description);
        itemPriceTextView = (TextView) itemView.findViewById(R.id.layout_item_price);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.clickListner = listner;
    }

    @Override
    public void onClick(View view)
    {
        clickListner.onClick(view, getAdapterPosition(), false);
    }
}