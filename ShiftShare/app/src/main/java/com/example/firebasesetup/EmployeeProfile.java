package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class EmployeeProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView resetPW;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Button save = findViewById(R.id.saveChanges);
        Button back = findViewById(R.id.backButton);
        resetPW = (TextView) findViewById(R.id.editPassword);
        resetPW.setClickable(true);
        resetPW.setFocusable(false);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        //non functioning
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        //non functioning
        resetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

}
