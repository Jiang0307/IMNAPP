package com.example.imnapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    public static boolean state = true;
    private String monster_name;
    private String[] monster_answer;
    Context context;
    private String[] current_board = new String[400];

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public Button[] button_array = new Button[400];

    // data is passed into the constructor
    RecyclerViewAdapter(Context ctx,String name, String[] answer)
    {
        this.monster_name = name;
        this.monster_answer = answer;
        this.context = ctx;
        this.mInflater = LayoutInflater.from(ctx);
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
        holder.myButton.setText(monster_answer[current_index]);
        holder.myButton.setTag(String.valueOf(current_index));

        button_array[current_index] = holder.myButton;

        int row = current_index/20;
        int col = current_index%20;

        if (row > 4 && col > 4)
        {
            holder.myButton.setClickable(false);
            holder.myButton.setText(" ");
        }
        else
        {
            holder.myButton.setClickable(true);
            holder.myButton.setBackgroundColor(Color.rgb(169,169,169));
        }
    }

    // total number of cells
    @Override
    public int getItemCount()
    {
        return monster_answer.length;
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
            String tag = (String) myButton.getTag();
            int idx = Integer.valueOf(tag);
            Log.d("index",tag);

            if(state == true)
            {
                current_board[idx] = "O";
                myButton.setText("O");
                myButton.setBackgroundColor(Color.rgb(0,0,0));
            }
            else
            {
                current_board[idx] = "X";
                myButton.setText("X");
                myButton.setBackgroundColor(0);
            }
            if(check_answer()==true)
            {
                Intent introduceIntent = new Intent(context, IntroduceActivity.class);
                introduceIntent.putExtra("monster_name", monster_name);
                introduceIntent.putExtra("from_nonogram", "true");
//                introduceIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP + Intent.FLAG_ACTIVITY_NEW_TASK);
                introduceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(introduceIntent);
            }
        }

        public boolean check_answer()
        {
//            for(int i = 0; i < 400; i++)
//            {
//                Log.d("55555",monster_answer[i]);
////                if(monster_answer[i] != null){
////                    Log.d("55555",String.valueOf(i));
////                }
//            }
            for(int i = 105; i < 400; i++)
            {
                if(i % 20 < 5)
                {
                    continue;
                }

                Log.d("55555",String.valueOf(i));
                Log.d("55555",monster_answer[i]);
                if(current_board[i] == null && monster_answer[i].equals("O"))
                {
                    return false;
                }
                else if(current_board[i] == null && monster_answer[i].equals("X"))
                {
                    continue;
                }

                if(! current_board[i].equals(monster_answer[i]))
                {
                    Log.d("55555",String.valueOf(i));
                    Log.d("55555",current_board[i]);
                    Log.d("55555",monster_answer[i]);
                    return false;
                }
            }
            return true;
        }
    }

    // convenience method for getting data at click position
    String getItem(int idx)
    {
        return monster_answer[idx];
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