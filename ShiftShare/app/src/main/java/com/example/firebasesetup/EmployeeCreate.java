package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/* Similar to EmployeeSetup. Called when a manager is adding an employee to a business */

public class EmployeeCreate extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextContactNumber;
    Button btnAddEmployee;
    Manager manager;
    Employee employee;
    Business business;
    List<Business> businessList;

    DatabaseReference databaseEmployees;
    DatabaseReference databaseBusiness;
    DatabaseReference databaseManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_create);

        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextContactNumber = (EditText) findViewById(R.id.editTextContactNumber);
        btnAddEmployee = (Button) findViewById(R.id.btnAddEmployee);

        btnAddEmployee.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerEmployee();
            }
        });

    }


    private void registerEmployee()
    {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        addEmployee();

    }

    private void addEmployee()
    {
        String email = editTextEmail.getText().toString().trim();
        String contactNumber = editTextContactNumber.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

        if(!TextUtils.isEmpty(name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(contactNumber))
        {
            String id = databaseEmployees.push().getKey();
            business = GlobalClass.getBusiness();
            employee = new Employee(id, name, contactNumber, null, email);
            System.out.println("New emp ID: " + employee.getId() + " : Bsn ID: " + GlobalClass.getBusiness().getId());
            business.addEmployee(employee.getId());
            GlobalClass.setBusiness(business);
            databaseBusiness = FirebaseDatabase.getInstance().getReference("Businesses");
            databaseBusiness.child(GlobalClass.getBusiness().getId()).setValue(GlobalClass.getBusiness());
            databaseEmployees.child(employee.getId()).setValue(employee);
            Intent next = new Intent(getApplicationContext(), ManagerPortal.class);
            startActivity(next);
        }

        else
        {
            Toast.makeText(this,"You should complete all fields ", Toast.LENGTH_LONG).show();
        }
    }

}
