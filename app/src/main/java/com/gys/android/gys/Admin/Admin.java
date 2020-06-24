package com.gys.android.gys.Admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gys.android.gys.R;

public class Admin extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView =  findViewById(R.id.gov);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Admin.this , AddPlaces.class);
                String gov = parent.getItemAtPosition(position).toString();
                intent.putExtra("gov", gov);
                startActivity(intent);
            }
        });
    }
}

