package com.example.firebasesetup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ManagerPortal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<Employee> employeeList;
    DatabaseReference dbEmployee;
    DatabaseReference databaseSchedules;

    Spinner spinnerDay,spinnerTo, spinnerFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_portal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
         spinnerTo = (Spinner) findViewById(R.id.spinnerTo);
         spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpSpinners();
        employeeList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EmployeeAdapter(this, employeeList);
        recyclerView.setAdapter(adapter);
        databaseSchedules = FirebaseDatabase.getInstance().getReference("Schedules");


        dbEmployee = FirebaseDatabase.getInstance().getReference("Employees");
        dbEmployee.addListenerForSingleValueEvent(valueEventListener);

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            employeeList.clear();
            if (dataSnapshot.exists()) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    employeeList.add(employee);
                }
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };


    public void setUpSpinners() {
        // Spinner element


        // Spinner click listener
        spinnerDay.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);
        spinnerFrom.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        List<String> Days = new ArrayList<String>();
        Days.add("Monday");
        Days.add("Tuesday");
        Days.add("Wednesday");
        Days.add("Thursday");
        Days.add("Friday");
        Days.add("Saturday");
        Days.add("Sunday");

        List<String> Hours = new ArrayList<String>();
        Hours.add("00:00");
        Hours.add("00:30");
        Hours.add("01:00");
        Hours.add("01:30");
        Hours.add("02:00");
        Hours.add("02:30");
        Hours.add("03:00");
        Hours.add("03:30");
        Hours.add("04:00");
        Hours.add("04:30");
        Hours.add("05:00");
        Hours.add("05:30");
        Hours.add("06:00");
        Hours.add("06:30");
        Hours.add("07:00");
        Hours.add("07:30");
        Hours.add("08:00");
        Hours.add("08:30");
        Hours.add("09:00");
        Hours.add("09:30");
        Hours.add("10:00");
        Hours.add("10:30");
        Hours.add("11:00");
        Hours.add("11:30");
        Hours.add("12:00");
        Hours.add("12:30");
        Hours.add("13:00");
        Hours.add("13:30");
        Hours.add("14:00");
        Hours.add("14:30");
        Hours.add("15:00");
        Hours.add("15:30");
        Hours.add("16:00");
        Hours.add("16:30");
        Hours.add("17:00");
        Hours.add("17:30");
        Hours.add("18:00");
        Hours.add("18:30");
        Hours.add("19:00");
        Hours.add("19:30");
        Hours.add("20:00");
        Hours.add("20:30");
        Hours.add("21:00");
        Hours.add("21:30");
        Hours.add("22:00");
        Hours.add("22:30");
        Hours.add("23:00");
        Hours.add("23:30");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Days);
        ArrayAdapter<String> dataAdapterHours = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Hours);

        // Drop down layout style - list view with radio button
        dataAdapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDay.setAdapter(dataAdapterDays);
        spinnerTo.setAdapter(dataAdapterHours);
        spinnerFrom.setAdapter(dataAdapterHours);

        findViewById(R.id.btn_submit_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = spinnerDay.getSelectedItem().toString();
                String availability = day + ": ";
                String start = spinnerFrom.getSelectedItem().toString();
                String end = spinnerTo.getSelectedItem().toString();
                int startTime = timeToInt(start);
                int endTime = timeToInt(end);
                if (startTime < endTime) {

                }
                Toast.makeText(spinnerDay.getContext(), "Search Conducted " + availability, Toast.LENGTH_LONG).show();
            }
        });


    }


    public static Integer timeToInt(String s) {
        String[] sHourMin = s.split(":");
        int Hour = Integer.parseInt(sHourMin[0]);
        int Mins = Integer.parseInt(sHourMin[1]);
        if (Mins % 60 == 0 && Mins != 0) {
            Hour += 1;
            Mins = 0;
        }
        if (Hour % 24 == 0) {
            Hour = 0;
        }
        int Time = Hour * 100 + Mins;
        return Time;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manager_portal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Profile) {
            Intent startIntent = new Intent(getApplicationContext(), ManagerProfile.class);
            startActivity(startIntent);

        } else if (id == R.id.ViewEmployees) {

        } else if (id == R.id.Logout) {

            AppPreferences prefs = new AppPreferences(getApplicationContext());
            prefs.setLoginPref(false);
            prefs.setId(null);
            prefs.setDb(null);
            finish();

        } else if (id == R.id.About) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
