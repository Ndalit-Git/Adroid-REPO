package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.IpSecManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class buy_ticket extends AppCompatActivity {

    private TextView depart_date;
    private Spinner sp_from, sp_to, sp_passenger, sp_seat;
    private String user,from,to,passenger,seat_class,seat_class2,date;
    private Integer passenger1, fare, total_fare;
    private Calendar c;
    private DatePickerDialog dpick;
    private SimpleDateFormat dateFormatter;
    SharedPreferences shared;
    dbHelp dbHelp;
    private long backpress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        depart_date = (TextView)findViewById(R.id.textView25);
        sp_from = (Spinner) findViewById(R.id.spinner_from);
        sp_to = (Spinner) findViewById(R.id.spinner_to);
        sp_passenger = (Spinner) findViewById(R.id.spinner_passenger);
        sp_seat = (Spinner) findViewById(R.id.spinner_seat);

        dbHelp = new dbHelp(this);
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
        Intent goback = new Intent(buy_ticket.this,home.class);
        startActivity(goback);
        finish();
    }

    public void dd_date(View view) {
        c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        dpick = new DatePickerDialog(buy_ticket.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int myear, int mmonth, int mday) {
                depart_date.setText(mday +"/"+(mmonth+1)+"/"+myear);
            }
        },day,month,year);
        dpick.show();
    }

    public void add_date(View view){
        Calendar newCalendar = Calendar.getInstance();
        dpick = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                depart_date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        dpick.show();
    }

    public void gobooking(View view) {
        shared = getSharedPreferences("profile_user", MODE_PRIVATE);
        user = shared.getString("username", null);
        from = sp_from.getSelectedItem().toString();
        to = sp_to.getSelectedItem().toString();
        passenger = sp_passenger.getSelectedItem().toString();
        seat_class = sp_seat.getSelectedItem().toString();
        date = depart_date.getText().toString();

        passenger1 = Integer.parseInt(passenger);
        if(seat_class.equals("Economy - (IDR) 75,000")){
            seat_class2 = "Economy";
            fare = 75000;
            total_fare = passenger1 * fare;
        } else if(seat_class.equals("Business - (IDR) 100,000")){
            seat_class2 = "Business";
            fare = 100000;
            total_fare = passenger1 * fare;
        } else if(seat_class.equals("First - (IDR) 120,000")){
            seat_class2 = "First";
            fare = 120000;
            total_fare = passenger1 * fare;
        }

        if (date.isEmpty()){
            message.SHORT(getApplicationContext(),"Please Add Departure Date");
        } else {
            if ((from.equals("Taman Sari") && to.equals("Taman Sari"))
                || ((from.equals("Merapi") && to.equals("Merapi"))
                || ((from.equals("Prambanan") && to.equals("Prambanan"))
                || ((from.equals("Ratu Bokoh") && to.equals("Ratu Bokoh"))
                || ((from.equals("Indrayanti") && to.equals("Indrayanti"))
                || ((from.equals("Kalibiru") && to.equals("Kalibiru")))))))){
                message.SHORT(getApplicationContext(),"Destination places cannot be the same!");
            } else {
                long id = dbHelp.insert_booking(user,from,to,passenger,seat_class2,fare,total_fare,date,"UNPAID");
                if (id <= 0) {
                    message.SHORT(getApplicationContext(), "Unsuccessful!");
                } else {
                    message.LONG(getApplicationContext(),"Searching Bus, Please Wait...");
                    new Handler().postDelayed(new Runnable(){
                        @Override
                        public void run() { ;
                            Intent gobook = new Intent(buy_ticket.this,home.class);
                            startActivity(gobook);
                            finish();
                        }
                    }, 2000);
                    message.SHORT(getApplicationContext(),"Booking Success");
                }
            }
        }
    }
}
