package com.gys.android.gys;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.gys.android.gys.Model.PlacesM;
import com.gys.android.gys.Prevalent.Prevalent;
import com.gys.android.gys.Registration.LoginActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.paperdb.Paper;

import android.view.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gys.android.gys.ViewHolder.PlaceViewHolder;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference placesRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager ;
    private Button srcbtn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //dec

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        //Paper
        Paper.init(this);

        //firebase with governorate extra
        placesRef = FirebaseDatabase.getInstance().getReference().child("Places").child("Cairo");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(Home.this , CartActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //header of navigator
        View headerView = navigationView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.nav_name);
        String userName = Paper.book().read(Prevalent.currentUser);
        //name of user
        name.setText(userName);

        //recycler view
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        //form
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        srcbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                search();
//            }
//        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        //here is to get items from database on firebase
        //we need model for place
        FirebaseRecyclerOptions<PlacesM> options =
                new FirebaseRecyclerOptions.Builder<PlacesM>()
                        //refrence
                        .setQuery(placesRef, PlacesM.class)
                        .build();

        //adapter itself using the model and the viewer
        FirebaseRecyclerAdapter<PlacesM, PlaceViewHolder> adapter =
                new FirebaseRecyclerAdapter<PlacesM, PlaceViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PlaceViewHolder holder, int i, @NonNull final PlacesM model) {
                        //put data

                        holder.txtPrice.setText("Price : " + model.getPrice() + " L.E " );
                        //holder.txtDes.setText(model.getDescription());
                        holder.txtName.setText(model.getPname());
                        //images
                        Picasso.get().load(model.getImage()).into(holder.img);

                        //on item click
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Home.this , PlaceDetActivity.class);
                                intent.putExtra("price", model.getPrice());
                                intent.putExtra("name", model.getPname());
                                intent.putExtra("des", model.getDescription());
                                intent.putExtra("image", model.getImage());
                                intent.putExtra("id", model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        //where to show
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.places_items_layout, parent, false);
                        PlaceViewHolder holder = new PlaceViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "hhhh", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_signout) {

            Paper.book().destroy();
            Intent intent = new Intent(Home.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void signOut(View v ){
        Paper.book().destroy();
        Intent intent = new Intent(Home.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSearch(View view){

        Intent intent = new Intent(Home.this , Search.class);
        startActivity(intent);
    }

}
