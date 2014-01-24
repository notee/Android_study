package com.example.chat.Datebase;

import android.content.ContentValues;

public class DBRecord {
    String comment;
    String user_name;
    long posted_at;

    public DBRecord(String input_comment, String input_user_name, Long input_posted_at){
        comment 	= input_comment;
        user_name 	= input_user_name;
        posted_at	= input_posted_at;
    }

    public ContentValues toContentValues(){
        ContentValues cv = new ContentValues();

        cv.put("comment", comment);
        cv.put("user_name", user_name);
        cv.put("posted_at", posted_at);

        return cv;
    }
}
