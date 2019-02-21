package com.example.shiftshare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import business;

public class Buisiness_Setup extends AppCompatActivity {

    TextView input_name = findViewById(R.id.txt_input_bsn_name);
    TextView input_num = findViewById(R.id.input_str_num);
    TextView input_street = findViewById(R.id.input_bsn_street);
    TextView input_city = findViewById(R.id.input_bsn_city);
    TextView input_state = findViewById(R.id.input_bsn_state);
    TextView input_zip = findViewById(R.id.input_bsn_zip);
    Button btn_submit = findViewById(R.id.btn_submit);

    DatabaseReference databaseManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buisiness__setup);

        databaseManagers = FirebaseDatabase.getInstance().getReference("business");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    private void add_business(){
        String bsn_name = input_name.getText().toString();
        String bsn_store_num = input_num.getText().toString();
        String bsn_street = input_street.getText().toString();
        String bsn_city = input_city.getText().toString();
        String bsn_state = input_state.getText().toString();
        String bsn_zip = input_zip.getText().toString();
        
        if(TextUtils.isEmpty(bsn_name) || 
           TextUtils.isEmpty(bsn_street) || 
           TextUtils.isEmpty(bsn_city) || 
           TextUtils.isEmpty(bsn_state) ||
           TextUtils.isEmpty(bsn_zip))
        {Toast.makeText(this, "There are required fields empty", Toast.LENGTH_SHORT).show();}
        else{
            String id = databaseManagers.push().getKey();
            Business bsn = new Business(bsn_id, bsn_name, bsn_store_num, bsn_street, bsn_city, bsn_state, bsn_zip);
            databaseManagers.child(id).setValue(bsn);
            Toast.makeText(v.getContext(), "Data Submitted", Toast.LENGTH_SHORT).show();
        }
    }
}
