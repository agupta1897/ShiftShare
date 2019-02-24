package com.example.firebasesetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

EditText editTextName;
EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextContactNumber;
Button btnAddManager;


DatabaseReference databaseManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManagers = FirebaseDatabase.getInstance().getReference("managers");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContactNumber = (EditText) findViewById(R.id.editTextContactNumber);
        btnAddManager = (Button) findViewById(R.id.btnAddManager);


        btnAddManager.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
            addManager();
            }
        });
    }


    private void addManager()
    {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString().trim();



        if(!TextUtils.isEmpty(name) ||  !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(contactNumber) )
        {
            String id = databaseManagers.push().getKey();
            Manager manager = new Manager(id,name,email, password, contactNumber);
            //databaseManagers.child(id).setValue(manager);
            GlobalClass.manager = manager;
            Toast.makeText(this, "Manager Added", Toast.LENGTH_LONG).show();
            //Intent next = new Intent(this, BusinessSetup.class);
            //startActivity(next);
        }
        else
        {
            Toast.makeText(this,"You should complete all fields ", Toast.LENGTH_LONG).show();

        }
    }
}
