package com.example.firebasesetup;

import android.content.Intent;
import android.support.annotation.NonNull;
//import android.provider.ContactsContract;
//import android.util.Log;
import android.support.constraint.solver.widgets.Snapshot;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import java.util.ArrayList;
//import java.util.List;
//import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;




import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



import java.security.PrivateKey;
import java.util.prefs.PreferenceChangeEvent;


public class Login extends AppCompatActivity {

    AppPreferences preferences;
    FirebaseAuth mAuth;
    DatabaseReference dbManager;
    DatabaseReference dbEmployee;
    android.support.constraint.ConstraintLayout page;
    //   private List<Manager> managerList;
    EditText editTextEmail, editTextPassword, editTextSignup;
    //ProgressBar progressBar;
    Manager manager;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = new AppPreferences(getApplicationContext());
        Button login = findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextSignup = (EditText) findViewById(R.id.textSignup);
        page = findViewById(R.id.page);
        editTextSignup.setClickable(true);
        editTextSignup.setFocusable(false);
//        managerList = new ArrayList<>();

        if(preferences.getLoginPref()){
            dbManager = FirebaseDatabase.getInstance().getReference("managers");
            Query query = dbManager.orderByChild("id").equalTo(preferences.getId());
            query.addListenerForSingleValueEvent(valueEventListener);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
                //   Intent startIntent = new Intent(getApplicationContext(), Login.class);
                //startActivity(startIntent);
            }
        });

        editTextSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SignupChoice.class);
                startActivity(startIntent);

            }
        });


    }


    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

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

        // progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    isManager(email);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void isManager( String email)
    {
        dbManager = FirebaseDatabase.getInstance().getReference("managers");
        Query query = FirebaseDatabase.getInstance().getReference("managers")
                .orderByChild("email")
                .equalTo(email);
        query.addListenerForSingleValueEvent(valueEventListener);

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
//            managerList.clear();
            if (dataSnapshot.exists()) {

                Toast.makeText(getApplicationContext(), "SUCCESSFUL Manager LOGIN!!", Toast.LENGTH_SHORT).show();
                if(preferences.getLoginPref()){
                    manager = dataSnapshot.child(preferences.getId()).getValue(Manager.class);
                }
                else{
                    String email = editTextEmail.getText().toString();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        manager = snapshot.getValue(Manager.class);
                        if(manager.getEmail().equals(email)) break;
                    }
                }
                preferences.setId(manager.getId());
                preferences.setDb("managers");
                preferences.setLoginPref(true);
                GlobalClass.setManager(manager);
                Intent intent = new Intent( getApplicationContext(), BusinessSelect.class);
                System.out.println("Shared Prefs ID = " + preferences.getId());
                startActivity(intent);

                //************ This is a working example of how to capture query results into array list**********/////////////////////
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Manager manager = snapshot.getValue(Manager.class);
//                    managerList.add(manager);
//                }
//           Log.d( "Manager List ", managerList.toString());
                //*************Example Finished******************/////////


            }
            else
            {
                Toast.makeText(getApplicationContext(), "SUCCESSFUL Employee LOGIN!!", Toast.LENGTH_SHORT).show();
                dbEmployee = FirebaseDatabase.getInstance().getReference("Employees");
                if(preferences.getLoginPref()){
                    final String id = preferences.getId();
                    dbEmployee.orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                employee = snapshot.getValue(Employee.class);
                                if(employee.getId().equals(id)) break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    final String email = editTextEmail.getText().toString();
                    dbEmployee.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                employee = snapshot.getValue(Employee.class);
                                if(employee.getEmail().equals(email)) break;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                System.out.println("Employee ID: " + employee.getId());
                preferences.setId(employee.getId());
                preferences.setDb("Employees");
                preferences.setLoginPref(true);
                GlobalClass.setEmployee(employee);
                Intent intent = new Intent( getApplicationContext(), EPortal.class);
                startActivity(intent);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


}
