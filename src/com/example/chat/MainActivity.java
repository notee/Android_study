package com.example.chat;

import com.example.chat.Datebase.DBOpenHelper;
import com.example.chat.Datebase.DBRecord;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.gson.*;

//import com.example.chat.DBOpenHelper;

public class MainActivity extends Activity {

    //objects
    boolean is_connecting = false;

    Button button_connect = null;
    Button button_leave = null;
    Button button_post = null;

    EditText text_box = null;

    ListView body = null;
    ArrayAdapter<String> adapter_to_body = null;
    DBOpenHelper dbOpenHelper = null;


    //objectsのイベントリスナー
    Button.OnClickListener b_connect_listener = new Button.OnClickListener() {
        public void onClick(View v){
            Log.d( "Button", "かわしたやーくそくーわすれーないよー" );
            //adapter_to_body.insert( "connect", 0 );
            //サーバ接続処理を書きます

            button_post.setEnabled(true);
            is_connecting = true;
        }
    };

    Button.OnClickListener b_leave_listener = new Button.OnClickListener() {
        public void onClick(View v){
            Log.d( "Button", "leave" );
            //adapter_to_body.insert( "leave", 0 );

            //切断処理を書きます

            button_post.setEnabled(false);
            is_connecting = false;
        }
    };

    Button.OnClickListener b_post_listener = new Button.OnClickListener() {
        public void onClick(View v){
            Log.d( "Button", "post" );

            if (is_connecting) {
                if (!text_box.getText().toString().equals("")) {
                    post( text_box.getText().toString() );
                    text_box.setText("");
                }
                reload();
            }
        }
    };



    //methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("chaaaaaaaaaaaaaaaaaaaat","app start!");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //画面上のみなさん
        button_connect   = (Button) findViewById(R.id.button_connect);
        button_leave     = (Button) findViewById(R.id.button_leave);
        button_post      = (Button) findViewById(R.id.button_post);

        button_connect.setOnClickListener(b_connect_listener);
        button_leave.setOnClickListener(b_leave_listener);
        button_post.setOnClickListener(b_post_listener);

        button_post.setEnabled(false);

        text_box    = (EditText) findViewById(R.id.text_box);

        body            = (ListView) findViewById(R.id.body);
        //以下のnewの第2引数は定義済みレイアウトファイルのID
        adapter_to_body = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        body.setAdapter(adapter_to_body);

        //データベースオープン, これでDBOpenHelperに書かれたDBがオープンする。
        dbOpenHelper	= new DBOpenHelper(this);

        reload();
        Log.d("chaaaaaaaaaaaaaaaaaaaat","activity loaded");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //ここから自由に作る
    public boolean post(String comment){
        Log.d("DB","post");

        //登録用のレコード作成
        DBRecord record = new DBRecord(comment, "nanashi", System.currentTimeMillis());

        //DBオープン
        SQLiteDatabase db   = dbOpenHelper.getWritableDatabase();

        if(db.insert("timeline", null, record.toContentValues()) != 0){
            return true;
        }

        return false;
    }


    public boolean reload(){
        Log.d("DB","reload");

        //DBオープン
        SQLiteDatabase db   = dbOpenHelper.getReadableDatabase();

        Cursor records = db.query(
                            "timeline", //from
                            new String[] {"comment", "user_name", "posted_at"}, //cols
                            null, //where_binds
                            null, //where_params
                            null, //groupby
                            null, //having
                            "posted_at desc", //orderby
                            "5" //limit
                        );

        Log.d("query", "select has done");
        //ListView更新処理
        if(records.moveToFirst()){
            adapter_to_body.clear();
            boolean record_exists = true;
            while (record_exists){
                adapter_to_body.add(records.getString(1) + " : " + records.getString(0));
                record_exists = records.moveToNext(); //次の行あれば移動
            }
            return true;
        }

        return false;
    }

}