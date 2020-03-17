package com.example.launcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AppsDrawer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_drawer);
    }

   /* RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RView);
    RAdapter radapter = new RAdapter(this);
    recyclerView.setAdapter(RAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
}
