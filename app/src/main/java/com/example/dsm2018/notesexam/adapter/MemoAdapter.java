package com.example.dsm2018.notesexam.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dsm2018.notesexam.MemoModel.MemoModel;
import com.example.dsm2018.notesexam.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoViewHolder> {

    private List<MemoModel> memoList = new ArrayList<>();
    private List<String> mMemolds = new ArrayList<>();
    private DatabaseReference mDatabaseReference;
    private RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener{
        //아이템 클릭
        void onItemClicked(int position, MemoModel memo, String id);
        void onItemLongClicked(int position, MemoModel memo, String id);
    }
    public  void setOnClickListener(RecyclerViewClickListener mListener)
    {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_memo, viewGroup, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int position) {

        MemoModel memo = memoList.get(position);
        holder.titleTextView.setText(memo.getTitle());
        holder.dateTextView.setText(memo.getDate());

        String key = mMemolds.get(position);

        if(mListener != null){
            final int pos = position;
            final MemoModel memoModel = memo;
            final String memokey = key;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(pos, memoModel, memokey);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mListener.onItemLongClicked(pos, memoModel, memokey);
                    return true;
                    }

                });
        }
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public MemoAdapter(DatabaseReference ref) {
        mDatabaseReference = ref;

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMemolds.clear();
                memoList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mMemolds.add(snapshot.getKey());
                    memoList.add(snapshot.getValue(MemoModel.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }
        });
    }

}


