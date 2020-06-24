package com.gys.android.gys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gys.android.gys.Model.PlacesM;
import com.gys.android.gys.ViewHolder.PlaceViewHolder;
import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {
    private String srcstr;
    private EditText srcedt;
    private DatabaseReference placesRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        srcedt = findViewById(R.id.search_name);
        placesRef = FirebaseDatabase.getInstance().getReference().child("Places").child("Cairo");
        //recycler view
        recyclerView = findViewById(R.id.recycler_menu_search);
        recyclerView.setHasFixedSize(true);
        //form
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }
    private void search() {
        srcstr = srcedt.getText().toString().trim();
//        if (!TextUtils.isEmpty(srcstr)) {
//            FirebaseRecyclerOptions<PlacesM> options =
//                    new FirebaseRecyclerOptions.Builder<PlacesM>()
//                            //refrence
//                            .setQuery(placesRef.orderByChild("pname").startAt(srcstr).endAt(srcstr), PlacesM.class)
//                            .build();
//
//            //adapter itself using the model and the viewer
//            FirebaseRecyclerAdapter<PlacesM, PlaceViewHolder> adapter =
//                    new FirebaseRecyclerAdapter<PlacesM, PlaceViewHolder>(options) {
//                        @Override
//                        protected void onBindViewHolder(@NonNull PlaceViewHolder holder, int i, @NonNull final PlacesM model) {
//                            //put data
//
//                            holder.txtPrice.setText("Price : " + model.getPrice() + " L.E ");
//                            //holder.txtDes.setText(model.getDescription());
//                            holder.txtName.setText(model.getPname());
//                            //images
//                            Picasso.get().load(model.getImage()).into(holder.img);
//
//                            //on item click
//                            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent intent = new Intent(Search.this, PlaceDetActivity.class);
//                                    intent.putExtra("price", model.getPrice());
//                                    intent.putExtra("name", model.getPname());
//                                    intent.putExtra("des", model.getDescription());
//                                    intent.putExtra("image", model.getImage());
//                                    intent.putExtra("id", model.getPid());
//                                    startActivity(intent);
//
//
//                                }
//                            });
//                        }
//
//                        @NonNull
//                        @Override
//                        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                            //where to show
//                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_items_layout, parent, false);
//                            PlaceViewHolder holder = new PlaceViewHolder(view);
//                            return holder;
//                        }
//                    };
//            recyclerView.setAdapter(adapter);
//            adapter.startListening();
//        }else {
        Toast.makeText(this, "Still not available", Toast.LENGTH_SHORT).show();
        }
    }


