package com.example.firebasesetup;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;


public class EPortal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int CHOOSE_IMAGE = 101;

    FirebaseAuth mAuth;
    DatabaseReference databaseSchedules;
    DatabaseReference databaseEmployees;
    String scheduleId = new String();
    Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eportal);
        mAuth = FirebaseAuth.getInstance();
        databaseEmployees = FirebaseDatabase.getInstance().getReference("Employees");
        databaseSchedules = FirebaseDatabase.getInstance().getReference("Schedules");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                }

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
                String availability = day+": ";
                String start = spinner2.getSelectedItem().toString();
                String end = spinner3.getSelectedItem().toString();
                int startTime = timeToInt(start);
                int endTime = timeToInt(end);
                while (startTime < endTime){
                    databaseSchedules.child(scheduleId).child(day).child(Integer.toString(startTime)).setValue("False");
                    availability = availability.concat(startTime + ", ");
                    startTime += 30;
                    if (startTime%100 == 60){startTime += 40;}

                }
                Toast.makeText(spinner.getContext(), availability, Toast.LENGTH_LONG).show();
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
                while (startTime < endTime){
                    databaseSchedules.child(scheduleId).child(day).child(Integer.toString(startTime)).setValue("True");
                    availability = availability.concat(startTime + ", ");
                    startTime += 30;
                    if (startTime%100 == 60){startTime += 40;}

                }
                Toast.makeText(spinner.getContext(), availability, Toast.LENGTH_LONG).show();
                //  String day = spinner.getSelectedItem().toString();
              //  databaseSchedules.child(scheduleId).child(day).setValue(null);
            }
        }
        );
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

    public String intsToTime(Integer h, Integer m){
        String s = Integer.toString(h) + ":" + Integer.toString(m);
        return s;
    }

}

