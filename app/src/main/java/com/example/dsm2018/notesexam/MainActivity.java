package com.example.dsm2018.notesexam;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.dsm2018.notesexam.MemoModel.MemoModel;
import com.example.dsm2018.notesexam.adapter.MemoAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private MemoAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recyclerList);

        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
            }
        });

        DividerItemDecoration decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(getResources().getString(R.string.notechild));

        mAdapter = new MemoAdapter(mDatabaseReference);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new MemoAdapter.RecyclerViewClickListener() {
            @Override
            public void onItemClicked(int position, MemoModel memo, String key) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                intent.putExtra("memokey", key);
                intent.putExtra("title", memo.getTitle());
                intent.putExtra("contents", memo.getContents());

                startActivity(intent);
            }
            @Override
            public void onItemLongClicked(int position, MemoModel memo, String key){
                final String memokey = key;
                //삭제
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("메모 삭제");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //데이터 삭제
                        mDatabaseReference.child(memokey).removeValue();

                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();
            }
        });



    }


}
