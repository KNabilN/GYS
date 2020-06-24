package com.gys.android.gys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.paperdb.Paper;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gys.android.gys.Model.Carts;
import com.gys.android.gys.Prevalent.Prevalent;
import com.gys.android.gys.ViewHolder.CartViewHolder;
import com.gys.android.gys.ViewHolder.PlaceViewHolder;

public class CartActivity extends AppCompatActivity {

//    private TextView pricetxt ;
    private Button conbtn;
    private RecyclerView cartRec ;
    private RecyclerView.LayoutManager layoutManager;
    private int overAllPrice = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //dec
//        pricetxt = findViewById(R.id.total_price_cart);
        conbtn = findViewById(R.id.conf_btn_cart);

        cartRec = findViewById(R.id.cart_rec);
        cartRec.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        cartRec.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("User View");
        FirebaseRecyclerOptions<Carts> options =
                new FirebaseRecyclerOptions.Builder<Carts>()
                        .setQuery(
                                cartRef.child(Paper.book()
                                        .read(Prevalent.UserPhone).toString())
                                        .child("Places")
                                , Carts.class)
                        .build();

        FirebaseRecyclerAdapter<Carts, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Carts, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull final Carts carts) {

                        holder.txtName.setText(carts.getName());
                        holder.txtPrice.setText("Price : " + carts.getPrice() + " L.E ");
                        overAllPrice = overAllPrice + Integer.valueOf(carts.getPrice());
                        //to edit or delete an item
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                CharSequence options[] = new CharSequence[]
                                        {
                                                "حذف"
                                        };
                                final AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            cartRef.child(Paper.book()
                                                    .read(Prevalent.UserPhone).toString())
                                                    .child("Places").child(carts.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(CartActivity.this, "تم الحذف", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });


                                        }
                                    }
                                });
                                builder.show();
                            }

                        });
                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };
        cartRec.setAdapter(adapter);
        adapter.startListening();

        conbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this , Home.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
