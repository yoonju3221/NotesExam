package com.example.dsm2018.notesexam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.dsm2018.notesexam.R;

public class MemoViewHolder extends RecyclerView.ViewHolder{
    TextView titleTextView;
    TextView dateTextView;

    public MemoViewHolder(View itemView){
        super(itemView);
        titleTextView = itemView.findViewById(R.id.itemmemo_textview_title);
        dateTextView = itemView.findViewById(R.id.itemmemo_textview_date);
    }

}
