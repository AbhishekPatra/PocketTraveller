package com.example.android.remindme;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText Name,Email,Pass,ConPass;
    Button reg_button;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = (EditText)findViewById(R.id.reg_name);
        Email = (EditText)findViewById(R.id.reg_email);
        Pass = (EditText)findViewById(R.id.reg_password);
        ConPass = (EditText)findViewById(R.id.reg_con_password);
        reg_button = (Button)findViewById(R.id.reg_button);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Name.getText().toString().equals("")||Email.getText().toString().equals("")|| Pass.getText().toString().equals(""))
                {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Please fill all the fields");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }

                else if (!(Pass.getText().toString().equals(ConPass.getText().toString())))
                {
                    builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setTitle("Something went wrong");
                    builder.setMessage("Your password is not matching");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Pass.setText("");
                            ConPass.setText("");

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else
                {
                    BackgroundTask backgroundTask = new BackgroundTask(RegisterActivity.this);
                    backgroundTask.execute("register",Name.getText().toString(),Email.getText().toString(),Pass.getText().toString());

                }


            }
        });
    }
}
