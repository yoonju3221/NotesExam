package com.example.dsm2018.notesexam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dsm2018.notesexam.MemoModel.MemoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MemoActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;

    private EditText mTitleEditText;
    private  EditText mContentEditText;

    private static final String REQUIRED_TITLE ="제목을 입력하세요.";
    private static final String REQUIRED_CONTENTS = "내용을 입력하세요.";

    private String mMemoKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mTitleEditText = findViewById(R.id.memoactivity_edittext_title);
        mContentEditText = findViewById(R.id.memoactivity_edittext_contents);

        Intent intent = getIntent();
        if(intent != null){
            mMemoKey = intent.getStringExtra("memokey");
            String title = intent.getStringExtra("title");
            String contents = intent.getStringExtra("contents");

            mTitleEditText.setText(title);
            mContentEditText.setText(contents);
        }

        findViewById(R.id.memoactivity_button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleEditText.getText().toString();
                String content = mContentEditText.getText().toString();
                //제목이 공백인지 체크
                if(TextUtils.isEmpty(title)){
                    mTitleEditText.setError(REQUIRED_TITLE);
                    return;
                }
                //내용
                if(TextUtils.isEmpty(content)){
                    mContentEditText.setError(REQUIRED_CONTENTS);
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");

                MemoModel model = new MemoModel(title, content, dateFormat.format(new Date()));


                if(mMemoKey == null){
                    mDatabaseReference.child(getResources().getString(R.string.notechild)).push().setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(MemoActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MemoActivity.this, "저장 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                }else{
                    Log.d("MemoActivity", "update item : " + mMemoKey);
                    String path = getResources().getString(R.string.notechild) + "/" +mMemoKey + "/";
                    Map<String, Object> childUpdates = model.toMap(path);

                    mDatabaseReference.updateChildren(childUpdates);
                    finish();
                }

            }
        });





    }
}
