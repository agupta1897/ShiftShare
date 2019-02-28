package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmployeeSetup extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextContactNumber;
    Button btnAddEmployee;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

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
        String password = editTextPassword.getText().toString();

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

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    addEmployee();
                    finish();
                    // startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
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
            Employee employee = new Employee(id,name,contactNumber, password, email);
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
