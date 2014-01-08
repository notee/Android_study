package com.example.chat.Datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DBNAME 	= "chat";
    private static final int VERSION 	= 1;


    private static final String SQL_CREATE
        = 	"CREATE TABLE IF NOT EXISTS timeline ("
            + "	_id INTEGER PRIMARY KEY,"
            + "	comment TEXT NOT NULL,"
            + "	user_name TEXT NOT NULL,"
            + "	posted_at INTEGER NOT NULL"
            + ")";

    public DBOpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
        Log.d("hogegheghoeghoehigahopgha", "constructed!!!!!!!!!!!!!!!!!!!!");
        // TODO 自動生成されたコンストラクター・スタブ
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
    	Log.d("SQL", "onOpen");
        super.onOpen(db);
        db.execSQL(SQL_CREATE);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQL","onCreate");

        // TODO 自動生成されたメソッド・スタブ
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 自動生成されたメソッド・スタブ
        db.execSQL("DROP TABLE IF EXISTS timeline");
        onCreate(db);
    }
}