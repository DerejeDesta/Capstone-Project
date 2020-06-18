package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlinestore.Classes.Item;
import com.example.onlinestore.Classes.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class SearchItemActivity extends AppCompatActivity
        {
            private Button SearchBtn;
            private EditText inputText;
            private RecyclerView searchList;
            private String SearchInput;



            @Override
            protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_search_item);


                inputText = findViewById(R.id.search_product_name);
                SearchBtn = findViewById(R.id.search_btn);
                searchList = findViewById(R.id.search_list);
                searchList.setLayoutManager(new LinearLayoutManager(SearchItemActivity.this));


                SearchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        SearchInput = inputText.getText().toString();

                        onStart();
                    }
                });
            }



            @Override
            protected void onStart()
            {
                super.onStart();


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Items");

                FirebaseRecyclerOptions<Item> options =
                        new FirebaseRecyclerOptions.Builder<Item>()
                                .setQuery(reference.orderByChild("itemName").startAt(SearchInput), Item.class)
                                .build();

                FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                        new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
                            @Override
                            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Item model)
                            {
                                holder.itemNameTextView.setText(model.getItemName());
                                holder.itemDescriptionTextView.setText(model.getDescription());
                                holder.itemPriceTextView.setText("Price = " + model.getPrice() + "$");
                                Picasso.get().load(model.getImage()).into(holder.itemImageView);

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        Intent intent = new Intent(SearchItemActivity.this, ItemInfoActivity.class);
                                        intent.putExtra("itemId", model.getItemId());
                                        startActivity(intent);
                                    }
                                });
                            }

                            @NonNull
                            @Override
                            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                            {
                                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                                ItemViewHolder holder = new ItemViewHolder(view);
                                return holder;
                            }
                        };

                searchList.setAdapter(adapter);
                adapter.startListening();
            }
        }