package com.example.onlinestore;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.Classes.Item;
import com.example.onlinestore.Classes.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference itemsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type = "";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        itemsRef = FirebaseDatabase.getInstance().getReference().child("Items");




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView emailTextView = headerView.findViewById(R.id.user_email);


        emailTextView.setText(auth.getCurrentUser().getEmail());

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Item> options =
                new FirebaseRecyclerOptions.Builder<Item>()
                        .setQuery(itemsRef, Item.class)
                        .build();


        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter =
                new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull final Item model)
                    {
                        holder.itemNameTextView.setText(model.getItemName());
                        holder.itemDescriptionTextView.setText(model.getDescription());
                        holder.itemPriceTextView.setText("$" + model.getPrice());
                        Picasso.get().load(model.getImage()).into(holder.itemImageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {

                                // product detail activirty
                                Intent intent = new Intent(HomeActivity.this, ItemInfoActivity.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
        }
        else if (id == R.id.nav_search)
        {
                // search activity
                Intent intent = new Intent(HomeActivity.this, SearchItemActivity.class);
                startActivity(intent);

        }
        else if (id == R.id.nav_orders)

        {
            Intent intent = new Intent(HomeActivity.this, UserOrderActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_about)
        {
                Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                startActivity(intent);

        }
        else if (id == R.id.nav_signOut)
        {
            auth.signOut();
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class);
            startActivity(intent);



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}