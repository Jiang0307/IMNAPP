package com.example.imnapp;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Monster
{
    int num_monster, monster_index;
    String monster_name;
    String[] monster_answer;

    public void set_values(int num, int index, String name, String[] answer)
    {
        Log.d("monster_index 8888", String.valueOf(index));
        Log.d("monster_name 8888", String.valueOf(name));
        num_monster = num;
        monster_index = index;
        monster_name = name;
        monster_answer = answer;
    }
    public Monster()
    {
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
                int num = monsters.size(); // 總共幾隻
                int index = (int) (Math.random() * (num)); // 隨機產生妖獸的index
                String name = monsters.get(index);

                // 用monster_name到妖獸階層，再用answer取出妖獸的答案
                int idx = 0;
                char[] temp_answer = dataSnapshot.child(name).child("answer").getValue().toString().toCharArray();
                String[] answer = new String[225];
                for(int i=0; i<temp_answer.length; i++)
                {
                    if( temp_answer[i] != ',' )
                    {
                        answer[idx++] = String.valueOf(temp_answer[i]);
                    }
                }
                set_values(num, index, name, answer);
            }
            @Override
            public void onCancelled(DatabaseError error){ }
        });
    }
}
