package com.example.imnapp;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WrapContentGridLayoutManager extends GridLayoutManager
{
    public WrapContentGridLayoutManager(Context context,AttributeSet attrs, int defStyleAttr,int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        super.setSpanCount(2);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}