package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BusinessSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    BusinessAdapter businessAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Business> businessList;
    Manager manager;
    DatabaseReference databaseBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_select);

        recyclerView = findViewById(R.id.bsn_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        businessList = new ArrayList<>();
        businessAdapter = new BusinessAdapter(this, businessList);
        recyclerView.setAdapter(businessAdapter);

        manager = GlobalClass.getManager();
        Toast.makeText(this, manager.getName(),Toast.LENGTH_LONG).show();

        databaseBusiness = FirebaseDatabase.getInstance().getReference("Businesses");
        for(int i = 0; i < manager.getBusiness().size(); i++) {

            String id = manager.getBusiness().get(i).toString();
            Query query = databaseBusiness.equalTo(id);
            query.addListenerForSingleValueEvent(valueEventListener);

        }

        businessAdapter.setOnItemClickListener( new BusinessAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Business business = businessList.get(position);
                GlobalClass.setBusiness(business);
                Intent intent = new Intent(getApplicationContext(), ManagerPortal.class);
                startActivity(intent);
            }
        });

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Business business = dataSnapshot.getValue(Business.class);
            businessList.add(business);
            businessAdapter.notifyDataSetChanged();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
