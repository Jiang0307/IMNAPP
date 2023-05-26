package com.example.imnapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
    RecyclerViewAdapter adapter;
    static int num_monster;
    static int monster_index; //隨機選一隻怪物
    static String monster_name;
    static String[] monster_answer;

    private String[] new_game()
    {
        String[] answer = new String[225];
        ArrayList<String> monsters = new ArrayList<String>();
        DatabaseReference root_ref = FirebaseDatabase.getInstance().getReference().child("monsters");
        root_ref.addValueEventListener(new ValueEventListener()
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
                NonogramActivity.num_monster = monsters.size(); // 總共幾隻
                for(int i=0; i<num_monster; i++)
                {
                    Log.d("name", monsters.get(i));
                }
                NonogramActivity.monster_index = (int)(Math.random()*(num_monster)); // 隨機產生妖獸的index
                NonogramActivity.monster_name = monsters.get(monster_index);
                Log.d("monster_name", monster_name);

                // 用monster_name到妖獸階層，再用answer取出妖獸的答案
                int idx = 0;
                char[] temp_answer = dataSnapshot.child(monster_name).child("answer").getValue().toString().toCharArray();
                for(int i=0; i<temp_answer.length; i++)
                {
                    if( temp_answer[i] != ',' )
                    {
                        answer[idx++] = String.valueOf(temp_answer[i]);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error){ }
        });
        return answer;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        int numberOfColumns = 15;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonogram);
 
        String[] answer = new_game();

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new RecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Log.d("number ", String.valueOf(position));
    }
}