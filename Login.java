package com.example.android.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    TextView signup_text;
    Button but;
    EditText Email,Pass,Name;
    Button login_button;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = (EditText)findViewById(R.id.email);
        Pass = (EditText)findViewById(R.id.password);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        signup_text = (TextView)findViewById(R.id.sign_up);
        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
            }
        });

        login_button=(Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    BackgroundTask backgroundTask = new BackgroundTask(Login.this);
                    backgroundTask.execute("login", Email.getText().toString(), Pass.getText().toString());

            }
        });



    }


}
