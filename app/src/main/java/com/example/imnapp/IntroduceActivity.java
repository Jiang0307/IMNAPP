package com.example.imnapp;

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

    ImageView introBackimg, introimg;
    TextView introtitle, intromsg;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);
        init();
    }

    private void init()
    {
        introBackimg = findViewById(R.id.introbackimgbtn);
        Log.e("init","init");
        introBackimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        introimg = findViewById(R.id.intro_img);
        introtitle = findViewById(R.id.intro_title_text);
        intromsg = findViewById(R.id.intro_message_text);

        String introKey = getIntent().getStringExtra("getIntroduceKey");

        databaseReference = FirebaseDatabase.getInstance().getReference("monsters").child(introKey);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {

                    intromsg.setText(snapshot.child("introduce").getValue().toString());
                    introtitle.setText(introKey);
                    Glide.with(introimg.getContext()).load(snapshot.child("pic_url").getValue().toString()).into(introimg);

                    introtitle.setVisibility(View.VISIBLE);
                    intromsg.setVisibility(View.VISIBLE);

                    viewAnimation();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    //動畫設定
    private void viewAnimation()
    {
        introtitle.setTranslationY(300);
        intromsg.setTranslationY(300);

        float alpha = (float) 0.1;

        introtitle.setAlpha(alpha);
        intromsg.setAlpha(alpha);

        introtitle.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();
        intromsg.animate().translationY(0).alpha(1).setDuration(700).setStartDelay(300).start();
    }
}