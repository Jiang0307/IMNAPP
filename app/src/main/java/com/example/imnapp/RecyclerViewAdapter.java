package com.example.imnapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, String[] data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.nonogram_style, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int current_index)
    {
        holder.myButton.setText(mData[current_index]);
//        Log.e("current_index", String.valueOf(current_index));

        int row = current_index/15;
        int col = current_index%15;
//        Log.e("row: ", String.valueOf(row));
//        Log.e("col: ", String.valueOf(col));

        if (row > 3 && col > 3)
        {
            holder.myButton.setClickable(false);
        }
        else
        {
            holder.myButton.setClickable(true);
        }
    }

    // total number of cells
    @Override
    public int getItemCount()
    {
        return mData.length;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Button myButton;

        ViewHolder(View itemView)
        {
            super(itemView);
            myButton = itemView.findViewById(R.id.nonogram_button);
            itemView.setOnClickListener(this);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View view)
        {
            myButton.setBackgroundColor(R.color.purple_200);
            if (mClickListener != null)
            {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    // convenience method for getting data at click position
    String getItem(int idx)
    {
        return mData[idx];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}