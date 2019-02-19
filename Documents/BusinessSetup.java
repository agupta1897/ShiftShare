package com.example.shiftshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Buisiness_Setup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buisiness__setup);

        final TextView input_name = findViewById(R.id.txt_input_bsn_name);
        final TextView input_num = findViewById(R.id.input_str_num);
        final TextView input_street = findViewById(R.id.input_bsn_street);
        final TextView input_city = findViewById(R.id.input_bsn_city);
        final TextView input_state = findViewById(R.id.input_bsn_state);
        final TextView input_zip = findViewById(R.id.input_bsn_zip);
        final Button btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bsn_name = input_name.getText().toString();
                String bsn_store_num = input_num.getText().toString();
                String bsn_street = input_street.getText().toString();
                String bsn_city = input_city.getText().toString();
                String bsn_state = input_state.getText().toString();
                String bsn_zip = input_zip.getText().toString();
                //actual submit stuff here
                Toast.makeText(v.getContext(), "Data Submitted", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
