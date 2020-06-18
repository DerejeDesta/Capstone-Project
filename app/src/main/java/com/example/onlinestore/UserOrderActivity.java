package com.example.onlinestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinestore.Classes.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserOrderActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);


        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        ordersList = findViewById(R.id.orders_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart()
    {
        super.onStart();


        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef, AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, UserOrderActivity.UserOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, UserOrderActivity.UserOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserOrderActivity.UserOrdersViewHolder holder, final int position, @NonNull final AdminOrders model)
                    {
                        if(model.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                            holder.email.setText("Email: " + model.getEmail());
                            holder.totalPrice.setText("Total price: " + "$" + model.getTotalAmount());
                            holder.date.setText("order date:" + model.getDate());
                            holder.time.setText("time: " + model.getTime() + "  " + model.getTime());
                            holder.status.setText("Status: " + model.getStatus());
                        }

                    }

                    @NonNull
                    @Override
                    public UserOrderActivity.UserOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new UserOrderActivity.UserOrdersViewHolder(view);
                    }
                };

        ordersList.setAdapter(adapter);
        adapter.startListening();
    }




    public static class UserOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView email, totalPrice, date, time,status;


        public UserOrdersViewHolder(View itemView)
        {
            super(itemView);


            email = itemView.findViewById(R.id.order_email);
            totalPrice = itemView.findViewById(R.id.order_total_price);
            date = itemView.findViewById(R.id.order_date);
            time = itemView.findViewById(R.id.order_time);
            status = itemView.findViewById(R.id.order_satus);
        }
    }




    private void RemoverOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }
}
