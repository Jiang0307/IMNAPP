package com.example.imnapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NonogramActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonogram);

        // data to populate the RecyclerView with
//        String[] data =
//        {
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
//            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"
//        };

        String[] data =
        {
                " ", " ", " ", " ", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                " ", " ", " ", " ", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                " ", " ", " ", " ", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                " ", " ", " ", " ", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  ",
                "1", "2", "3", "4", " ", " ", " ", " ", " ", "  ", "  ", "  ", "  ", "  ", "  "
        };

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        int numberOfColumns = 15;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}