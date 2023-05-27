package com.example.imnapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IntroduceActivity extends AppCompatActivity
{
    private DatabaseReference databaseReference;
    Context context;
    ImageView back_button, favorite_button, introduce_img;
    TextView introduce_title, introduce_text;
    String monster_name;
    boolean favorite_state;
    boolean from_nonogram;
    View root_view;

    @Override
    public void onBackPressed()
    {
        Log.d("from_nonogram", String.valueOf(from_nonogram));
        if(from_nonogram == true) // 回到menu
        {
            Intent intent = new Intent(root_view.getContext(), MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        else
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // getStringExtra : 用name(key)得到資料(string)
//        context = /
        root_view = findViewById(android.R.id.content).getRootView();
        monster_name = getIntent().getStringExtra("monster_name");
        from_nonogram = Boolean.valueOf(getIntent().getStringExtra("from_nonogram"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        init();
    }

    private void update_favorite_state()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("monsters").child(monster_name).child("favorite");
        databaseReference.setValue(favorite_state);
    }

    private void check_favorite_state(boolean initial)
    {
        Log.d("favorite_state", String.valueOf(favorite_state));
        if(initial == true)
        {
            if(favorite_state == false) // 改成1
                favorite_button.setImageResource(R.drawable.not_favorite);
            else // 改成0
                favorite_button.setImageResource(R.drawable.favorite);
        }
        else
        {
            if(favorite_state == false) // 改成1
            {
                favorite_button.setImageResource(R.drawable.favorite);
                favorite_state = !favorite_state;
                update_favorite_state();
            }
            else // 改成0
            {
                favorite_button.setImageResource(R.drawable.not_favorite);
                favorite_state = !favorite_state;
                update_favorite_state();
            }
        }
    }

    private void init()
    {
        back_button = findViewById(R.id.introduce_back_button);
        favorite_button = findViewById(R.id.favorite_button);
        favorite_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                check_favorite_state(false);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("from_nonogram", String.valueOf(from_nonogram));
                if(from_nonogram == true) // 回到menu
                {
                    Intent intent = new Intent(v.getContext(), MenuActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                else
                    finish();
            }
        });

        introduce_img = findViewById(R.id.introduce_img);
        introduce_title = findViewById(R.id.introduce_title);
        introduce_text = findViewById(R.id.introduce_text);

        databaseReference = FirebaseDatabase.getInstance().getReference("monsters").child(monster_name);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    favorite_state = (boolean) snapshot.child("favorite").getValue();
                    check_favorite_state(true);
                    introduce_text.setText(snapshot.child("introduce").getValue().toString());
                    introduce_title.setText(monster_name);
                    Glide.with(introduce_img.getContext()).load(snapshot.child("pic_url").getValue().toString()).into(introduce_img);
                    introduce_title.setVisibility(View.VISIBLE);
                    introduce_text.setVisibility(View.VISIBLE);
                    viewAnimation();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error){}
        });
    }

    //動畫設定
    private void viewAnimation()
    {
        introduce_title.setTranslationY(300);
        introduce_text.setTranslationY(300);

        float alpha = (float) 0.1;

        introduce_title.setAlpha(alpha);
        introduce_text.setAlpha(alpha);

        introduce_title.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();
        introduce_text.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();
    }
}