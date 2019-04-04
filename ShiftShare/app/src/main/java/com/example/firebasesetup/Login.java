package com.example.firebasesetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
//import android.provider.ContactsContract;
//import android.util.Log;
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
import java.util.regex.Pattern;


public class Login extends AppCompatActivity {

    AppPreferences preferences;
    FirebaseAuth mAuth;
    DatabaseReference dbManager;
//    DatabaseReference dbEmployee;

 //   private List<Manager> managerList;
    EditText editTextEmail, editTextPassword, editTextSignup;
    //ProgressBar progressBar;

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
        editTextSignup.setClickable(true);
//        managerList = new ArrayList<>();

        if(preferences.getLoginPref()){
            if("managers".equals(preferences.getDb())){
                Intent intent = new Intent(getApplicationContext(), ManagerPortal.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(getApplicationContext(), EPortal.class);
                startActivity(intent);
            }
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

        if (isEmptyEmailAddress(email)) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!isValidEmailAddress(email)) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (isEmptyPassword(password)) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (!isValidPassword(password)) {
            editTextPassword.setError("Minimum lenght of password should be 6");
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

    public boolean isEmptyEmailAddress(String email)
    {
        return email.isEmpty();
    }

    public boolean isValidPassword(String password){

        if( password.length() <6)
            return false;
        else
            return true;
    }

    public boolean isEmptyPassword(String password)
    {
        return password.isEmpty();
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
                preferences.setId(dataSnapshot.getKey());
                preferences.setDb("managers");
                preferences.setLoginPref(true);
                Intent intent = new Intent( getApplicationContext(), ManagerPortal.class);
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
                preferences.setId(dataSnapshot.getKey());
                preferences.setDb("Employees");
                preferences.setLoginPref(true);
                Intent intent = new Intent( getApplicationContext(), EPortal.class);
                startActivity(intent);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



}
