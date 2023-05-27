package com.example.imnapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NonogramActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener
{
    Switch nonogram_switch;
    RecyclerViewAdapter adapter;
    private static Context ctx;
    int num_monster, monster_index;
    String monster_name;
    String[] monster_answer;
//    public sta boolean state = true; // true:O false:X

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        init();
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_nonogram);

        nonogram_switch = findViewById(R.id.nonogram_switch);
        nonogram_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                RecyclerViewAdapter.state = !RecyclerViewAdapter.state;
                Log.d("state", String.valueOf(RecyclerViewAdapter.state));
            }
        });

    }

    public void set_recyclerview()
    {
        int size = 20;
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvNumbers);
        recyclerView.setLayoutManager(new GridLayoutManager(ctx, size));
        adapter = new RecyclerViewAdapter(ctx, monster_name , monster_answer);
        recyclerView.setAdapter(adapter);
    }

    public void init()
    {
        //------------------------------------
        ArrayList<String> monsters = new ArrayList<String>();
        DatabaseReference root_ref = FirebaseDatabase.getInstance().getReference().child("monsters");
        root_ref.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                // 取出所有妖獸
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren() )
                {
                    String key = childSnapshot.getKey();
                    monsters.add(key);
                }
                num_monster = monsters.size(); // 總共幾隻
                monster_index = (int) (Math.random() * (num_monster)); // 隨機產生妖獸的index
                monster_name = monsters.get(monster_index);
                // 用monster_name到妖獸階層，再用answer取出妖獸的答案
                int idx = 0;
                char[] temp_answer = dataSnapshot.child(monster_name).child("answer").getValue().toString().toCharArray();
                monster_answer = new String[400];
                String temp = "";
                for(int i=0; i<temp_answer.length; i++)
                {
                    if( temp_answer[i] != ',' )
                    {
                        temp = temp + temp_answer[i];
                    }
                    else
                    {
                        monster_answer[idx++] = String.valueOf(temp);
                        temp = "";
                    }
                }
                // set recycler view
                set_recyclerview();
            }
            @Override
            public void onCancelled(DatabaseError error){ }
        });
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Log.d("number ", String.valueOf(position));
    }
}