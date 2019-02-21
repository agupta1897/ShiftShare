package com.example.firebasesetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignupChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_choice);

        Button btn_employee = findViewById(R.id.btn_employee);
        Button btn_manager = findViewById(R.id.btn_manager);

        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(v.getContext(), EmployeeSetup.class);
                startActivity(next);
            }
        });

        btn_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(v.getContext(), ManagerSetup.class);
                startActivity(next);
            }
        });

    }
}
