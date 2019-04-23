package com.example.firebasesetup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ManagerPortal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    List<Employee> employeeList, employeeListFinal;
    List<Schedule> scheduleList;
    DatabaseReference dbEmployee, dbSchedules;
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpSpinners();
        employeeList = new ArrayList<>();
        employeeListFinal = new ArrayList<>();
        scheduleList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new EmployeeAdapter(this, employeeListFinal);
        recyclerView.setAdapter(adapter);
        dbSchedules = FirebaseDatabase.getInstance().getReference("Schedules");

        adapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));

                //This is Amber's number temporarily
                int num;
                num = 1234567890;


                sendIntent.setData(Uri.parse("sms:" + num));
                startActivity(sendIntent);
            }
        });


        findViewById(R.id.btn_submit_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = spinnerDay.getSelectedItem().toString();
                String availability = day + " Shifts. ";
                String start = spinnerFrom.getSelectedItem().toString();
                String end = spinnerTo.getSelectedItem().toString();
                availability = availability + "Shifts. From " + start  + " to " + end +".";
                int startTime = timeToInt(start);
                int endTime = timeToInt(end);
                if (startTime < endTime) {
                    Toast.makeText(spinnerDay.getContext(), "Searching Employees...", Toast.LENGTH_LONG).show();
                    dbEmployee = FirebaseDatabase.getInstance().getReference("Employees");
                    dbEmployee.addListenerForSingleValueEvent(valueEventListenerEmployee);
                }
                else
                {
                    Toast.makeText(spinnerDay.getContext(), "Incorrect Time Slot", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    ValueEventListener valueEventListenerEmployee = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            employeeList.clear();
            if (dataSnapshot.exists()) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Employee employee = snapshot.getValue(Employee.class);
                    employeeList.add(employee);
                }
                getSchedules();
            }
            Toast.makeText(spinnerDay.getContext(), employeeList.size() + " Results Found", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };


    void getSchedules ()
    {
        dbSchedules = FirebaseDatabase.getInstance().getReference("Schedules");
        Query query = FirebaseDatabase.getInstance().getReference("Schedules").orderByChild("Monday");
        query.addListenerForSingleValueEvent(valueEventListenerSchedule);
    }

    ValueEventListener valueEventListenerSchedule = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot2) {
            scheduleList.clear();
            final Integer start = timeToInt(spinnerFrom.getSelectedItem().toString());
            final Integer end = timeToInt(spinnerTo.getSelectedItem().toString());

            if (dataSnapshot2.exists()) {

                List<String> matchedEmployees = new ArrayList<>();

                for (DataSnapshot dataSnapShot1 : dataSnapshot2.getChildren()) {
                    DataSnapshot dataSnapShot = dataSnapShot1.child("Monday");

                    Boolean includeEmail = true;

                    for (DataSnapshot SnapShot : dataSnapShot.getChildren()) {

                        Integer hour = Integer.valueOf(SnapShot.getKey());
                        Boolean isAvailable = toBool(SnapShot.getValue().toString().toLowerCase());

                        if (hour >= start && hour < end) {
                            if (isAvailable != true) {
                                includeEmail = false;
                            }
                        }
                    }
                    if (includeEmail) {

                        matchedEmployees.add(dataSnapShot1.child("empl_id").getValue().toString());

                        Toast.makeText(spinnerDay.getContext(), "Show this employee: " + dataSnapShot1.child("empl_id").getValue().toString(), Toast.LENGTH_LONG).show();
                    }
                }


                for (int i = 0; i < employeeList.size(); i++) {
                    for (int j = 0; j < matchedEmployees.size(); j++) {
                        if (employeeList.get(i).getEmail() != null) {
                            if (employeeList.get(i).getEmail().contains(matchedEmployees.get(j))) {
                                employeeListFinal.add(employeeList.get(i));
                            }
                        }
                    }
                }

                adapter.notifyDataSetChanged();

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };


//    void getScheduleForDays( List<Schedule> scheduleList) {
//        Integer startTime = timeToInt(spinnerFrom.getSelectedItem().toString());
//        Integer endTime = timeToInt(spinnerTo.getSelectedItem().toString());
//        String day = spinnerDay.getSelectedItem().toString();
//        for (int i = 0; i < scheduleList.size(); i++) {
//            getScheduleForDay( scheduleList.get(i), day, startTime, endTime);
//        }
//    }

    boolean toBool(String  x)
    {
        if (x.contains("true"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
//
//
//    public interface MyCallBack{
//        void onCallback( Schedule value );
//    }

//
//    boolean getScheduleForDay(final Schedule sch, String day, final Integer start, final Integer end, final MyCallBack myCallBack) {
//        final TextView noView = findViewById(R.id.noView);
//        noView.setText("");
//
//        dbSchedules.child(sch.getId()).child(day).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
//
//            Integer hour;
//            Boolean isAvailable;
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
//                if (dataSnapshot1.exists()) {
//                    for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()) {
//
//                        hour = Integer.valueOf(dataSnapshot.getKey());
//                        isAvailable = toBool(dataSnapshot.getValue().toString().toLowerCase());
//
//                        if (hour >= start && hour < end) {
//                            if (isAvailable != true) {
//                                myCallBack.onCallback(null);
//                            }
//                        }
//                    }
//                    myCallBack.onCallback(sch);
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        if(noView.getText().toString().contains("NO"))
//        {
//            return false;
//        }
//        else
//        {
//            return  true;
//        }
//    }


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

    public String intsToTime(Integer i){
        Integer h = i/100;
        Integer m = i%100;
        String s;
        if (m < 10)
            s = Integer.toString(h) + ":0" + Integer.toString(m);
        else
            s = Integer.toString(h) + ":" + Integer.toString(m);
        return s;
    }

    public String stringToTime(String s){
        return intsToTime(Integer.valueOf(s));
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
            GlobalClass.clearGlobal();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            /*
            the intent is necessary as finish() simply tells Android that this activity is no longer
            needed. It doesn't pass info on what to do afterwards. Should either close the app or
            go to the main activity afterwards. For now, going to MainActivity. Suspect reason why
            one instance of app would go to main while the other repeated the same screen is
            the behaviour becomes undefined. finish() is still needed, though, to prevent a logged
            out user from hitting the back button and going to the screen he logged out from -Murray
            */


        } else if (id == R.id.About) {

        } else if (id == R.id.AddEmployee){

            Intent intent = new Intent(getApplicationContext(), EmployeeCreate.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
