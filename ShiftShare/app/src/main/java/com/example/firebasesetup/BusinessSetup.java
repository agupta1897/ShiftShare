package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusinessSetup extends AppCompatActivity {

    EditText input_name;
    EditText input_num;
    EditText input_street;
    EditText input_city;
    EditText input_state;
    EditText input_zip;
    Button btn_submit;

    DatabaseReference databaseManagers;
    DatabaseReference databaseBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_setup);

        databaseManagers = FirebaseDatabase.getInstance().getReference("managers");
        databaseBusiness = FirebaseDatabase.getInstance().getReference("Businesses");

        input_name = findViewById(R.id.input_bsn_name);
        input_num = findViewById(R.id.input_str_num);
        input_street = findViewById(R.id.input_bsn_street);
        input_city = findViewById(R.id.input_bsn_city);
        input_state = findViewById(R.id.input_bsn_state);
        input_zip = findViewById(R.id.input_bsn_zip);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {add_business();}

        });

    }

    private void add_business(){
        String bsn_name = input_name.getText().toString();
        String bsn_store_num = input_num.getText().toString();
        String bsn_street = input_street.getText().toString();
        String bsn_city = input_city.getText().toString();
        String bsn_state = input_state.getText().toString();
        String bsn_zip = input_zip.getText().toString();

        if(     TextUtils.isEmpty(bsn_name) ||
                TextUtils.isEmpty(bsn_street) ||
                TextUtils.isEmpty(bsn_city) ||
                TextUtils.isEmpty(bsn_state) ||
                TextUtils.isEmpty(bsn_zip))
        {Toast.makeText(this, "There are required fields empty", Toast.LENGTH_LONG).show();}
        else{
            Manager manager = GlobalClass.manager;
            String id = databaseBusiness.push().getKey();
            Business bsn = new Business(id, bsn_name, bsn_store_num, bsn_street, bsn_city, bsn_state, bsn_zip);
            manager.addBusiness(bsn.getId());
            bsn.addManager(manager.getId());
            GlobalClass.business = bsn;
            databaseManagers.child(manager.getId()).setValue(manager);
            //I'm not sure the above line is needed; the manager is added on the previous screen. -Carter
            //If I understand this correctly, it should update the manager in the database with the business object added to its businesses list. -Murray
            databaseBusiness.child(id).setValue(bsn);
            Toast.makeText(this, "Data Submitted", Toast.LENGTH_LONG).show();

            Intent next = new Intent(getApplicationContext(), Login.class);
            startActivity(next);
            // Intent next = new Intent(getApplicationContext(), ManagerPortal.class);
            // startActivity(next);
        }
    }
}
