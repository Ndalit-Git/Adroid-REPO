package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class home extends AppCompatActivity {

    private long backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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


    public void bt_buy(View view) {
        Intent booking = new Intent(home.this,buy_ticket.class);
        startActivity(booking);
        finish();
    }

    public void bt_destination(View view) {
        Intent desti = new Intent(home.this,destination.class);
        startActivity(desti);
        finish();
    }

    public void bt_myticket(View view) {
        Intent golist = new Intent(home.this,my_booking_list.class);
        startActivity(golist);
        finish();
    }

    public void bt_account(View view) {
        Intent myacc = new Intent(home.this,my_account.class);
        startActivity(myacc);
        finish();
    }
}
