package com.example.firebasesetup;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.Toast;
        import android.widget.TextView;


        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;


public class EPortal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private static final int CHOOSE_IMAGE = 101;

    FirebaseAuth mAuth;
    DatabaseReference databaseSchedules;
    DatabaseReference databaseEmployees;
    String scheduleId = new String();
    Schedule schedule;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_portal);
        mAuth = FirebaseAuth.getInstance();
        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");
        databaseSchedules = FirebaseDatabase.getInstance().getReference("Schedules");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);



        final FirebaseUser user = mAuth.getCurrentUser();
        Query query = databaseSchedules.orderByChild("empl_id").equalTo(user.getEmail());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot schedule : dataSnapshot.getChildren()) {
                        scheduleId = schedule.getKey();
                    }
                }
                else {
                    scheduleId = databaseSchedules.push().getKey();
                    schedule = new Schedule(scheduleId, user.getEmail());
                    databaseSchedules.child(scheduleId).setValue(schedule);
                    for (int x = 0; x < 2359; x+=0){
                        databaseSchedules.child(scheduleId).child("Monday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Tuesday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Wednesday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Thursday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Friday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Saturday").child(Integer.toString(x)).setValue("False");
                        databaseSchedules.child(scheduleId).child("Sunday").child(Integer.toString(x)).setValue("False");

                        //availability[0] = availability[0].concat(startTime + ", ");
                        x += 30;
                        if (x%100 == 60){x += 40;}

                    }
                }
                updateDisplayedAvailability("Monday");
                updateDisplayedAvailability("Tuesday");
                updateDisplayedAvailability("Wednesday");
                updateDisplayedAvailability("Thursday");
                updateDisplayedAvailability("Friday");
                updateDisplayedAvailability("Saturday");
                updateDisplayedAvailability("Sunday");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        ImageButton settingsButton = findViewById(R.id.btn_editProfile);
        Button Submit = findViewById(R.id.btn_submit);
        Button Clear = findViewById(R.id.btn_clear);

        findViewById(R.id.btn_editProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), ESettings.class);
                startActivity(startIntent);
            }
        });
        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);


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
        Hours.add("00:00"); Hours.add("00:30");
        Hours.add("01:00"); Hours.add("01:30");
        Hours.add("02:00"); Hours.add("02:30");
        Hours.add("03:00"); Hours.add("03:30");
        Hours.add("04:00"); Hours.add("04:30");
        Hours.add("05:00"); Hours.add("05:30");
        Hours.add("06:00"); Hours.add("06:30");
        Hours.add("07:00"); Hours.add("07:30");
        Hours.add("08:00"); Hours.add("08:30");
        Hours.add("09:00"); Hours.add("09:30");
        Hours.add("10:00"); Hours.add("10:30");
        Hours.add("11:00"); Hours.add("11:30");
        Hours.add("12:00"); Hours.add("12:30");
        Hours.add("13:00"); Hours.add("13:30");
        Hours.add("14:00"); Hours.add("14:30");
        Hours.add("15:00"); Hours.add("15:30");
        Hours.add("16:00"); Hours.add("16:30");
        Hours.add("17:00"); Hours.add("17:30");
        Hours.add("18:00"); Hours.add("18:30");
        Hours.add("19:00"); Hours.add("19:30");
        Hours.add("20:00"); Hours.add("20:30");
        Hours.add("21:00"); Hours.add("21:30");
        Hours.add("22:00"); Hours.add("22:30");
        Hours.add("23:00"); Hours.add("23:30");
        Hours.add("23:59");



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterDays = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Days);
        ArrayAdapter<String> dataAdapterHours = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Hours);

        // Drop down layout style - list view with radio button
        dataAdapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapterDays);
        spinner2.setAdapter(dataAdapterHours);
        spinner3.setAdapter(dataAdapterHours);



        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = spinner.getSelectedItem().toString();
                final String[] availability = {day + ": "};
                String start = spinner2.getSelectedItem().toString();
                String end = spinner3.getSelectedItem().toString();
                int startTime = timeToInt(start);
                int endTime = timeToInt(end);
                if (!validHours(startTime, endTime))
                {
                    Toast.makeText(spinner.getContext(), "Incorrect Time Slot", Toast.LENGTH_LONG).show();
                }
                while (startTime < endTime){
                    databaseSchedules.child(scheduleId).child(day).child(Integer.toString(startTime)).setValue("True");
                    //availability[0] = availability[0].concat(startTime + ", ");
                    startTime += 30;
                    if (newHour(startTime)){startTime += 40;}

                }
                updateDisplayedAvailability(day);
                Toast.makeText(spinner.getContext(), availability[0], Toast.LENGTH_LONG).show();
              //  if (day.equals("Monday"))
              //      schedule.setMonday(availability);
              //  databaseSchedules.child(scheduleId).child("Monday").setValue(availability);

            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String day = spinner.getSelectedItem().toString();
                String availability = day+": ";
                String start = spinner2.getSelectedItem().toString();
                String end = spinner3.getSelectedItem().toString();
                int startTime = timeToInt(start);
                int endTime = timeToInt(end);
                if (startTime >= endTime)
                {
                    Toast.makeText(spinner.getContext(), "Incorrect Time Slot", Toast.LENGTH_LONG).show();
                }
                while (startTime < endTime){
                    databaseSchedules.child(scheduleId).child(day).child(Integer.toString(startTime)).setValue("False");
                    availability = availability.concat(startTime + ", ");
                    startTime += 30;
                    if (startTime%100 == 60){startTime += 40;}

                }
                updateDisplayedAvailability(day);
                // Toast.makeText(spinner.getContext(), availability, Toast.LENGTH_LONG).show();
                //  String day = spinner.getSelectedItem().toString();
              //  databaseSchedules.child(scheduleId).child(day).setValue(null);
            }
        }
        );
    }

    void updateDisplayedAvailability(final String day) {
        TextView t0;
        if (day.equals("Monday")) {
            t0 = (TextView) findViewById(R.id.mondayAvailability);
        }
        else if (day.equals("Tuesday")){
            t0 = (TextView) findViewById(R.id.tuesdayAvailability);
        }
        else if (day.equals("Wednesday")){
            t0 = (TextView) findViewById(R.id.wednesdayAvailability);
        }
        else if (day.equals("Thursday")){
            t0 = (TextView) findViewById(R.id.thursdayAvailability);
        }
        else if (day.equals("Friday")){
            t0 = (TextView) findViewById(R.id.fridayAvailability);
        }
        else if (day.equals("Saturday")){
            t0 = (TextView) findViewById(R.id.saturdayAvailability);
        }
        else {
            t0 = (TextView) findViewById(R.id.sundayAvailability);
        }

        final TextView t = t0;
        t.setText("");
        databaseSchedules.child(scheduleId).child(day).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            String availability = "";
            DataSnapshot previousSnap = null;
            String prevChildKey;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                if (dataSnapshot1.exists()) {
                    for (DataSnapshot dataSnapshot : dataSnapshot1.getChildren()) {
                        System.out.println("hour:" + dataSnapshot.getKey() + " value: " + dataSnapshot.getValue());
                        if (previousSnap == null) {
                            prevChildKey = null;
                            previousSnap = dataSnapshot;
                        }
                        else
                            prevChildKey = previousSnap.getKey();
                        System.out.println("phour:" + previousSnap.getKey() + " pvalue: " + previousSnap.getValue());
                        if (dataSnapshot.getValue().equals("True")) {
                            if (prevChildKey == null || previousSnap.getValue().equals("False"))
                                availability = availability.concat(stringToTime(dataSnapshot.getKey()) + "-");
                            if (dataSnapshot.getKey().equals("2330"))
                                availability = availability.concat("23:59;");

                        }
                        if (dataSnapshot.getValue().equals("False")) {
                            if (prevChildKey != null && previousSnap.getValue().equals("True"))
                                availability = availability.concat(stringToTime(dataSnapshot.getKey()) + "; ");
                        }
                        //availability = availability.concat(dataSnapshot.getKey() + "- ");
                        System.out.println(availability);
                        // TextView t = (TextView) findViewById(R.id.mondayAvailability);
                        t.setText(availability);
                        previousSnap = dataSnapshot;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


      //  TextView t = (TextView) findViewById(R.id.mondayAvailability);
        //t.setText(availability[0]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public static Integer timeToInt(String s){
        String[] sHourMin = s.split(":");
        int Hour = Integer.parseInt(sHourMin[0]);
        int Mins = Integer.parseInt(sHourMin[1]);
        if (Mins%60 == 0 && Mins != 0){
                Hour += 1;
                Mins = 0;
        }
        if (Hour%24 == 0){
            Hour = 0;
        }
        int Time = Hour*100 + Mins;
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

    //Commenting out this codes removes the "settings" icon in the top right
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.employee_portal, menu);
//        return true;
//    }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //this should be EmployeeProfile but the page crashes, so temporarily using Manager's
        if (id == R.id.Profile) {
            Intent startIntent = new Intent(getApplicationContext(), EmployeeProfile.class);
            startActivity(startIntent);

        } else if (id == R.id.ViewEmployees) {

        } else if (id == R.id.Logout) {

            AppPreferences prefs = new AppPreferences(getApplicationContext());
            prefs.setLoginPref(false);
            prefs.setId(null);
            prefs.setDb(null);
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.About) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean validHours(int startTime, int endTime){
        return (startTime < endTime);
    }

    public boolean newHour(int time){
        return (time%100 == 60);
    }

    public boolean isAvailable(DataSnapshot snap){
        return snap.getValue().equals("True");
    }
}

