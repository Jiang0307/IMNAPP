package com.example.imnapp;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    ImageView logo_image;
    TextView titletv;
    LinearLayout main_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setEnterTransition(null);

        logo_image = findViewById(R.id.logo_img);
        titletv = findViewById(R.id.main_title_tv);
        main_order = findViewById(R.id.main_order);

        getWindow().setSharedElementEnterTransition(new ChangeBounds().setDuration(1000));

        viewAnimation();
    }

    //開啟相機函式 -> 詢問權限
    public void nonogram_listener(View view)
    {
        Intent camIntent = new Intent(this, NonogramActivity.class);
        startActivity(camIntent);
    }

    //進入景點介紹
    public void monster_listener(View view)
    {
        Intent attractionIntent = new Intent(this, MonsterActivity.class);
        startActivity(attractionIntent);
    }

    //動畫設定
    private void viewAnimation() {
        main_order.setTranslationY(900);

        float alpha = (float) 0.1;
        main_order.setAlpha(alpha);
        main_order.animate().translationY(0).alpha(1).setDuration(1100).setStartDelay(300).start();
    }

    //呼叫動畫
    public void anim(View view) {
        viewAnimation();
    }

}