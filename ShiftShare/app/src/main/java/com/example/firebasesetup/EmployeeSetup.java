package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeSetup extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextContactNumber;
    Button btnAddEmployee;


    DatabaseReference databaseEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_setup);

        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextContactNumber = (EditText) findViewById(R.id.editTextContactNumber);
        btnAddEmployee = (Button) findViewById(R.id.btnAddEmployee);


        btnAddEmployee.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addEmployee();
            }
        });
    }


    private void addEmployee()
    {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString().trim();



        if(!TextUtils.isEmpty(name) ||  !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(contactNumber) )
        {
            String id = databaseEmployees.push().getKey();
            Employee employee = new Employee(id,name,contactNumber, password);
            databaseEmployees.child(id).setValue(employee);
            GlobalClass.employee = employee;
           // Intent next = new Intent(getApplicationContext(), EmployeePortal.class);
           // startActivity(next);
        }
        else
        {
            Toast.makeText(this,"You should complete all fields ", Toast.LENGTH_LONG).show();

        }
    }
}
