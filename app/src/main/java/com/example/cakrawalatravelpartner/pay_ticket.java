package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class pay_ticket extends AppCompatActivity {

    private long backpress;
    private TextView pay_id,pay_total;
    private EditText pay_from,pay_to,pay_passenger,pay_seat,pay_date,pay_fare;
    private String id,from,to,passenger,seat,date,fare,total;
    private dbHelp dbHelp;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ticket);

        pay_id = findViewById(R.id.idbooking);
        pay_total = findViewById(R.id.totalfare);

        pay_from = findViewById(R.id.edit_from);
        pay_to = findViewById(R.id.edit_to);
        pay_passenger = findViewById(R.id.edit_name);
        pay_date = findViewById(R.id.edit_time);
        pay_seat = findViewById(R.id.edit_passenger);
        pay_fare = findViewById(R.id.edit_class);

        dbHelp = new dbHelp(this);
        setEnable(false);
        data();
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


    public void setEnable(Boolean A){
        pay_from.setEnabled(A);
        pay_to.setEnabled(A);
        pay_passenger.setEnabled(A);
        pay_date.setEnabled(A);
        pay_seat.setEnabled(A);
        pay_fare.setEnabled(A);
    }

    private void data(){
        id = getIntent().getStringExtra("book_id");
        pay_id.setText(id);
        String[] idargs = {id};
        dbHelp = new dbHelp(this);
        SQLiteDatabase db = dbHelp.getReadableDatabase();
        cursor = db.query("tb_booking",null,"id_booking=?",idargs,null,null,null);
        int i = 0 ;
        try{
            while (cursor.moveToNext()){
                cursor.moveToPosition(i);
                from = cursor.getString(cursor.getColumnIndex("book_from"));
                to = cursor.getString(cursor.getColumnIndex("book_to"));
                passenger = cursor.getString(cursor.getColumnIndex("passenger"));
                seat = cursor.getString(cursor.getColumnIndex("seat"));
                date = cursor.getString(cursor.getColumnIndex("date"));
                fare = cursor.getString(cursor.getColumnIndex("fare"));
                total = cursor.getString(cursor.getColumnIndex("total_fare"));
                i++;
            }
        } catch (Exception e){
            message.LONG(getApplicationContext(),"Error "+ e);
        } finally {
            pay_from.setText(" "+ from);
            pay_to.setText(" "+ to);
            pay_passenger.setText(" "+ passenger);
            pay_seat.setText(" "+ seat);
            pay_date.setText(" "+ date);
            pay_fare.setText(" "+ fare);
            pay_total.setText(total);
            cursor.close();
        }
    }

    public void gopay(View view) {
        id = pay_id.getText().toString();
        dbHelp.update_payment(id,"PAID");
        message.LONG(getApplicationContext(),"Please Wait...");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() { ;
                Intent gobook = new Intent(pay_ticket.this,my_booking_list.class);
                startActivity(gobook);
                finish();
            }
        }, 2000);
        message.SHORT(getApplicationContext(),"Payment Successful");
    }

    public void gocancel(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(pay_ticket.this);
        builder.setMessage("Are you sure want to delete your booking from booking list?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                id = pay_id.getText().toString();
                dbHelp.delete_booking(id);
                message.SHORT(getApplicationContext(), "Canceling...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ;
                        Intent gologin = new Intent(pay_ticket.this, my_booking_list.class);
                        startActivity(gologin);
                        finish();
                        message.SHORT(getApplicationContext(), "Booking Canceled");
                    }
                }, 2000);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void back(View view) {
        Intent goback = new Intent(pay_ticket.this,my_booking_list.class);
        startActivity(goback);
        finish();
    }
}
