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
    List<Business> businessList;
    Manager manager;
    DatabaseReference databaseBusiness;
    DatabaseReference databaseManager;
    AppPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_select);

        preferences = new AppPreferences(getApplicationContext());
        businessList = new ArrayList<>();
        recyclerView = findViewById(R.id.bsn_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        databaseManager = FirebaseDatabase.getInstance().getReference("managers");

        //recyclerView.setHasFixedSize(true);

        businessAdapter = new BusinessAdapter(this, businessList);
        recyclerView.setAdapter(businessAdapter);

        manager = GlobalClass.getManager();
        Toast.makeText(this, manager.getName(),Toast.LENGTH_LONG).show();

        databaseBusiness = FirebaseDatabase.getInstance().getReference("Businesses");
        for(int i = 0; i < manager.getBusiness().size(); i++) {

            final String id = manager.getBusiness().get(i);
            final int pos = i;
            System.out.println("Bsn ID @ " + i + " = " + id);
            Query query = databaseBusiness.orderByChild("id").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Business business = snapshot.getValue(Business.class);
                        if(business.getId().equals(id)){
                            System.out.println("BSN Name = " + business.getName());
                            businessList.add(pos, business);
                            businessAdapter.notifyItemInserted(pos);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        businessAdapter.setOnItemClickListener( new BusinessAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                Business business = businessList.get(position);
                GlobalClass.setBusiness(business);
                System.out.println("Position " + position + ": ID " + business.getId());
                Intent intent = new Intent(getApplicationContext(), ManagerPortal.class);
                startActivity(intent);
            }
        });

    }

}
