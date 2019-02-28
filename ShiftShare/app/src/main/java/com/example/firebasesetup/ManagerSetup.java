package com.example.firebasesetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.util.Patterns;
import android.widget.Toast;
import android.support.annotation.NonNull;


import com.google.firebase.FirebaseApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ManagerSetup extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextContactNumber;
    Button btnAddManager;
    DatabaseReference databaseManagers;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();

        btnAddManager.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerManager();
            }
        });
    }


    private void registerManager()
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
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "You are successfully registered", Toast.LENGTH_SHORT).show();
                    addManager();
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
            //Toast.makeText(this, "Manager Added", Toast.LENGTH_LONG).show();
            Intent next = new Intent(getApplicationContext(), BusinessSetup.class);
            startActivity(next);
        }
        else
        {
            Toast.makeText(this,"You should complete all fields ", Toast.LENGTH_LONG).show();

        }
    }
}
