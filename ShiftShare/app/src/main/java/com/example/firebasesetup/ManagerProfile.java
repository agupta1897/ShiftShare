package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ManagerProfile extends AppCompatActivity {


    DatabaseReference databaseManagers;
    FirebaseAuth mAuth;
    EditText name;
    EditText email;
    EditText phone;
    Manager man;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView resetPW;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Button save = findViewById(R.id.saveChanges);
        Button back = findViewById(R.id.backButton);
        resetPW = (TextView) findViewById(R.id.editPassword);
        resetPW.setClickable(true);
        resetPW.setFocusable(false);

        mAuth = FirebaseAuth.getInstance();
        databaseManagers = FirebaseDatabase.getInstance().getReference("managers");

        name = findViewById(R.id.editName);
        email = findViewById(R.id.editEmail);
        phone = findViewById(R.id.editPhone);

        final FirebaseUser user = mAuth.getCurrentUser();
        final AppPreferences prefs = new AppPreferences(getApplicationContext());

        Query query = FirebaseDatabase.getInstance().getReference("managers")
                .orderByChild("email")
                .equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(valueEventListener);

        email.setText(user.getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseManagers.child(prefs.getId()).child("name").setValue(name.getText().toString().trim());
                databaseManagers.child(prefs.getId()).child("email").setValue(email.getText().toString().trim());
                databaseManagers.child(prefs.getId()).child("number").setValue(phone.getText().toString().trim());

                finish();

            }
        });

        //non functioning
        resetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                man = snapshot.getValue(Manager.class);
                name.setText(man.getName());
                phone.setText(man.getContactNumber());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
