package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class gestarted extends AppCompatActivity {

    private long backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestarted);
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


    public void signin_bt(View view) {
        Intent signin = new Intent(gestarted.this,signinpage.class);
        startActivity(signin);
        finish();
    }

    public void signup_bt(View view) {
        Intent signup = new Intent(gestarted.this,singuppage.class);
        startActivity(signup);
        finish();
    }

    public void info(View view) {
        Intent infoaps = new Intent(gestarted.this,appinfo.class);
        startActivity(infoaps);
        finish();
    }

}
