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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


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

                    Toast.makeText(getApplicationContext(), "You are successfully registered", Toast.LENGTH_SHORT).show();

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

        if(isValidName(name) ||  isValidEmailAddress(email) || isValidPassword(password) || isValidPhoneNumber(contactNumber) )
        {
            databaseEmployees.addValueEventListener(valueEventListener);
            Intent next = new Intent(getApplicationContext(), Login.class);
            startActivity(next);
        }
        else
        {
            Toast.makeText(this,"You should complete all fields ", Toast.LENGTH_LONG).show();
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Boolean valid = false;
            String email = editTextEmail.getText().toString().trim();
            if(dataSnapshot.exists()){

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Employee dbEmployee = snapshot.getValue(Employee.class);
                    if(email.equals(dbEmployee.getEmail())){
                        String password = editTextPassword.getText().toString().trim();
                        dbEmployee.setPassword(password);
                        databaseEmployees.child(dbEmployee.getId()).setValue(dbEmployee);
                        valid = true;
                        break;
                    }
                }
            }
            if(!valid){

                Toast.makeText(getApplicationContext(), "Invalid email. Contact manager", Toast.LENGTH_LONG).show();

            }
            else{

                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_LONG).show();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    public static boolean isNumber(String string) {
        return string.matches("-?\\d+");
    }

    public boolean isValidPhoneNumber(String number)
    {
        if (number == null)
        {
            return false;
        }

        if (isNumber(number) && number.length()== 10)
        {
            return true;
        }
        else
            return false;
    }


    public boolean isValidEmailAddress(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public boolean isValidPassword(String password){

        if (isEmptyPassword(password)){
            return false;
        }

        if( password.length() <6)
            return false;
        else
            return true;
    }

    public boolean isEmptyPassword(String password)
    {
        return password.isEmpty();
    }

    public boolean isValidName(String name)
    {
        return !name.isEmpty();
    }


}
