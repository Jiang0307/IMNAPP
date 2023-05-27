package com.example.imnapp;

import android.annotation.SuppressLint;
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

public class FavoriteActivity extends AppCompatActivity
{
    adapter adapter;
    RecyclerView recyclerView;
    ImageView back_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = findViewById(R.id.favorite_recycler);
        back_button = findViewById(R.id.favorite_back_button);
        back_button.setOnClickListener(new View.OnClickListener()
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

        // 到firebase取出所有妖怪
        FirebaseRecyclerOptions<get_monster_info> options =
                new FirebaseRecyclerOptions.Builder<get_monster_info>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("monsters").orderByChild("favorite").equalTo(true), get_monster_info.class)
                        .build();
//--------------------------------------------------------------------------------------------------
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
