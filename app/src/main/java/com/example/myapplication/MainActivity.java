package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.file.Watchable;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    EditText pwd;
    EditText usname;
    Button login;

    private String us;
    private String pw;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pwd= (EditText)findViewById(R.id.pwd);
        usname=(EditText)findViewById(R.id.usname);
        login=(Button) findViewById(R.id.button);
        usname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0){
                    us= s.toString();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0){
                    pw= s.toString();
                }

            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(us.equals("Anmol")&& pw.equals("anmol")){
                   Intent intent=new Intent(MainActivity.this,image.class);
                   startActivity(intent);

               }
               else {
                   AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                   builder.setMessage("Incorrect credential");
                   builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   AlertDialog dialog=builder.create();
                   dialog.show();

               }
           }
       });

    }
}
