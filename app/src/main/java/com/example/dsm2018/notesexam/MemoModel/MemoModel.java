package com.example.dsm2018.notesexam.MemoModel;

import java.util.HashMap;
import java.util.Map;

public class MemoModel {
    private String title;
    private String contents;
    private String date;

    public MemoModel(){}
    public MemoModel(String title, String contents, String date){
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public Map<String, Object> toMap(String path){
        HashMap<String, Object> map = new HashMap<>();
        map.put(path + "title", this.title);
        map.put(path + "contents", this.contents);
        map.put(path + "date", this.date);

        return map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
