package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

public class signinpage extends AppCompatActivity {

    private long backpress;
    private EditText inusername,inpassword;
    private String username,password;
    dbHelp dbHelp;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinpage);
        inusername =(EditText) findViewById(R.id.username_signin);
        inpassword = (EditText) findViewById(R.id.password_signin);
        dbHelp = new dbHelp(this);

        sharedPreferences = getSharedPreferences("profile_user",MODE_PRIVATE);
        sharedPreferences.contains("username");
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
        Intent goback = new Intent(signinpage.this,gestarted.class);
        startActivity(goback);
        finish();
    }

    public void signup_login_page(View view) {
        Intent signup = new Intent(signinpage.this,singuppage.class);
        startActivity(signup);
        finish();
    }

    public void signin(View view) {
            username = inusername.getText().toString();
            password = inpassword.getText().toString();
            try {
                if (username.isEmpty()||password.isEmpty()){
                    message.SHORT(getApplicationContext(),"Error signing in, please try again");
                } else {
                    if (username.length() > 0 && password.length() > 0) {
                        dbHelp = new dbHelp(this);
                        SQLiteDatabase db = dbHelp.getReadableDatabase();

                        if (dbHelp.signin(username, password)) {
                            message.SHORT(getApplicationContext(),"Checking...");
                            inusername.setText("");
                            inpassword.setText("");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username", username);
                            editor.apply();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ;
                                    Intent gologin = new Intent(signinpage.this, home.class);
                                    startActivity(gologin);
                                    finish();
                                }
                            }, 2000);
                            message.SHORT(getApplicationContext(), "Signed In");
                        } else {
                            message.SHORT(getApplicationContext(),"Checking...");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ;
                                }
                            }, 1500);
                            message.SHORT(getApplicationContext(), "Wrong Username / Password");
                        }
                    }
                }
            } catch (Exception e) {
                message.LONG(getApplicationContext(), "Error " + e);
            }
    }

    public void forgot(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(signinpage.this);
        builder.setMessage("Please contact our customer service to get new password");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
