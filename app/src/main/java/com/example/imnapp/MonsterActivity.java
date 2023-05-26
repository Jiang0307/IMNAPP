package com.example.imnapp;

import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;

public class MonsterActivity extends AppCompatActivity
{

    adapter adapter;
    RecyclerView recyclerView;
    ImageView attraction_backImg_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster);

        recyclerView = findViewById(R.id.attraction_recycler);

        attraction_backImg_Btn = findViewById(R.id.attraction_backimgbtn);
        attraction_backImg_Btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        XmlPullParser parser = getResources().getLayout(R.layout.recycler);
        AttributeSet attrs = Xml.asAttributeSet(parser);

        recyclerView.setLayoutManager(new WrapContentGridLayoutManager(this, attrs,0,0));
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        // recyclerView.setLayoutManager(layoutManager);

        // 到firebase取出所有妖怪
        // nonogram一開始選哪隻的時候來這邊幹code
        FirebaseRecyclerOptions<get_monster_info> options =
                new FirebaseRecyclerOptions.Builder<get_monster_info>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("monsters"), get_monster_info.class)
                        .build();

        adapter = new adapter(options, this);

        recyclerView.setAdapter(adapter);
        anim();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }


    private void anim()
    {
        recyclerView.setTranslationY(900);

        float alpha = (float) 0.1;
        recyclerView.setAlpha(alpha);
        recyclerView.animate().translationY(0).alpha(1).setDuration(900).setStartDelay(300).start();
    }
}
