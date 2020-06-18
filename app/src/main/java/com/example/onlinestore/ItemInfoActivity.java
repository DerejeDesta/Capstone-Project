package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.onlinestore.Classes.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ItemInfoActivity extends AppCompatActivity {


        private Button addToCartButton;
        private ImageView itemImage;
        private ElegantNumberButton numberButton;
        private TextView itemPrice, itemDescription, itemName;
        private String price,description ;
        private String itemId = "", status = "Normal";


        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_info);

            itemId = getIntent().getStringExtra("itemId");

            addToCartButton = (Button) findViewById(R.id.pd_add_to_cart_button);
            numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
            itemImage = (ImageView) findViewById(R.id.item_image_info);
            itemName = (TextView) findViewById(R.id.item_name_info);
            itemDescription = (TextView) findViewById(R.id.item_description_info);

            itemPrice = (TextView) findViewById(R.id.item_price_info);


            getItemInfo(itemId);


            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (status.equals("Order Placed") || status.equals("Order Shipped"))
                    {
                        Toast.makeText(ItemInfoActivity.this, "you can add purchase more items, once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        addingToCartList();
                    }
                }
            });
        }


        @Override
        protected void onStart()
        {
            super.onStart();

           CheckOrderState();
        }

        private void addingToCartList()
        {
            String saveCurrentTime, saveCurrentDate;

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentDate.format(calForDate.getTime());

            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

            final HashMap<String, Object> cart = new HashMap<>();
            cart.put("itemId", itemId);
            cart.put("itemName", itemName.getText().toString());
            cart.put("description", description);

            cart.put("price", price);
            cart.put("date", saveCurrentDate);
            cart.put("time", saveCurrentTime);
            cart.put("quantity", numberButton.getNumber());

            cartListRef.child("cartUsers").child( FirebaseAuth.getInstance().getCurrentUser().getUid() )
                    .child("Items").child(itemId)
                    .updateChildren(cart)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(ItemInfoActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ItemInfoActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        }
                    }
                    );
        }


        private void getItemInfo(String itemId)
        {
            DatabaseReference ItemRef = FirebaseDatabase.getInstance().getReference().child("Items");

            ItemRef.child(itemId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        Item item = dataSnapshot.getValue(Item.class);

                        itemName.setText(item.getItemName());
                        price =item.getPrice();
                        description = item.getDescription();
                        itemPrice.setText("$" + item.getPrice());
                        itemDescription.setText(item.getDescription() + " " +item.getDetail());
                        Picasso.get().load(item.getImage()).into(itemImage);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

       private void CheckOrderState()
    {
            DatabaseReference ordersRef;
            ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            ordersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.exists())
                    {
                        String shippingState = dataSnapshot.child("status").getValue().toString();

                        if (shippingState.equals("shipped"))
                        {
                            status = "Order Shipped";
                        }
                        else if(shippingState.equals("not shipped"))
                        {
                            status = "Order Placed";
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
      }
    }
