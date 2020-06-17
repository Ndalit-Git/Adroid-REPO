package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class my_booking_list extends AppCompatActivity {

    private long backpress;
    dbHelp dbHelp;
    String user;
    ArrayList<String> book_id, from, to, passenger, date, status;
    RecyclerView recyclerView;
    cardview_bookinglist custom;
    SharedPreferences shared;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking_list);
        recyclerView = findViewById(R.id.recycleview);
        dbHelp = new dbHelp(this);
        book_id = new ArrayList<>();
        from = new ArrayList<>();
        to = new ArrayList<>();
        passenger = new ArrayList<>();
        date = new ArrayList<>();
        status = new ArrayList<>();
        custom = new cardview_bookinglist(my_booking_list.this,this,book_id,from,to,passenger,date,status);
        recyclerView.setAdapter(custom);
        recyclerView.setLayoutManager(new LinearLayoutManager(my_booking_list.this));
        tampildata();
    }
    @Override
    public void onBackPressed() {
        if (backpress + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        } else {
            message.SHORT(getApplicationContext(),"Press Again To Exit Application");
        }
        backpress = System.currentTimeMillis();
    }


    public void back(View view) {
        Intent goback = new Intent(my_booking_list.this,home.class);
        startActivity(goback);
        finish();
    }

    public void tampildata(){
        shared = getSharedPreferences("profile_user", MODE_PRIVATE);
        user = shared.getString("username", null);
        String[] userArgs = {user};
        dbHelp = new dbHelp(this);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        cursor = db.query("tb_booking", null, "user=?", userArgs, null, null, null);
        //Cursor cursor = dbHelp.read_booking();
            if(cursor.getCount()==0){
                message.SHORT(getApplicationContext(),"No Booking List");
            } else{
                while (cursor.moveToNext()){
                    book_id.add(cursor.getString(0));
                    from.add(cursor.getString(2));
                    to.add(cursor.getString(3));
                    passenger.add(cursor.getString(4));
                    date.add(cursor.getString(6));
                    status.add(cursor.getString(9));
                }
            }
        }
    }