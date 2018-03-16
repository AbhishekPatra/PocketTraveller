package com.example.android.remindme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class DetailActivity extends AppCompatActivity {
    ArrayList<task_time> t;
    RecyclerView view;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    int clicked_day;
    int clicked_month;
    int clicked_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundel=getIntent().getExtras();
        t=bundel.getParcelableArrayList("list");
        clicked_day=bundel.getInt("day");
        clicked_month=bundel.getInt("month");
        clicked_year=bundel.getInt("year");

       // Toast.makeText(this,clicked_date,Toast.LENGTH_SHORT).show();

        //t=getArguments().getParcelableArrayList("list");
//t.add(new task_time("me","ku"));
        Toast.makeText(this,""+t.size(),Toast.LENGTH_SHORT).show();
        view= (RecyclerView) findViewById(R.id.recycle);
        customAdapter=new CustomAdapter(this,t,clicked_day,clicked_month,clicked_year);
        layoutManager=new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(customAdapter);
    }



}
