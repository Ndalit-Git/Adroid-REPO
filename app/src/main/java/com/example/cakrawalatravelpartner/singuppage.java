package com.example.cakrawalatravelpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class singuppage extends AppCompatActivity {

    private long backpress;
    private RadioButton rb_male, rb_female;
    private EditText cr_username, cr_fullname, cr_phone, cr_password, confirm_password;
    private String username, full_name, gender, phone, password, confirm_pw;
    dbHelp dbHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singuppage);
        cr_username = (EditText) findViewById(R.id.edit_username_signup);
        cr_fullname = (EditText) findViewById(R.id.edit_fullname_signup);
        cr_phone = (EditText) findViewById(R.id.edit_phone_signup);
        cr_password = (EditText) findViewById(R.id.edit_password_signup);
        confirm_password = (EditText) findViewById(R.id.edit_confirm_password_signup);

        //radioGroup = findViewById(R.id.radioGroup);
        //rb_gender = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        rb_male = (RadioButton) findViewById(R.id.rb_male);
        rb_female = (RadioButton) findViewById(R.id.rb_female);
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


    public void signup(View view) {
        if (rb_male.isChecked()){gender = rb_male.getText().toString();}
        else if (rb_female.isChecked()){gender = rb_female.getText().toString();}
        username = cr_username.getText().toString();
        full_name = cr_fullname.getText().toString();
        phone = cr_phone.getText().toString();
        password = cr_password.getText().toString();
        confirm_pw = confirm_password.getText().toString();

            if (username.isEmpty() || full_name.isEmpty() || gender.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm_pw.isEmpty()) {
                message.SHORT(getApplicationContext(), "Please Fill All Column");
            } else {
                if (password.matches(confirm_pw)) {
                    long id = dbHelp.insert_user(username, full_name, gender, phone, password);
                    if (id <= 0) {
                        message.SHORT(getApplicationContext(), "Username didn't available");
                        cr_username.setText("");
                    } else {
                        message.LONG(getApplicationContext(), "Create Account, Please Wait...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ;
                                Intent gologin = new Intent(singuppage.this, signinpage.class);
                                startActivity(gologin);
                                finish();
                            }
                        }, 2000);
                        message.SHORT(getApplicationContext(), "Account Created !");
                    }
                } else {
                    message.SHORT(getApplicationContext(), "Password aren't matching");
                }
            }
        }

    public void back (View view){
        Intent signin = new Intent(singuppage.this, signinpage.class);
        startActivity(signin);
        finish();
    }
}