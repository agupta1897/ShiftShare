//Database parts are probably not correct
//Fix then uncomment relevent lines
package com.example.employeesignuppage;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.FirebaseApp;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    TextView input_name = findViewById(R.id.txt_Name);
    TextView input_num = findViewById(R.id.txt_phoneNumber);
    TextView input_email = findViewById(R.id.email);
    TextView input_password = findViewById(R.id.password);
    Button btn_register = findViewById(R.id.btn_register);

  //  DatabaseReference databaseEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        String empl_name = input_name.getText().toString().trim();
        String empl_num = input_num.getText().toString().trim();
        String empl_email = input_email.getText().toString().trim();
        String empl_password = input_password.getText().toString().trim();

        if(TextUtils.isEmpty(empl_name) ||
                TextUtils.isEmpty(empl_email) ||
                TextUtils.isEmpty(empl_num) ||
                TextUtils.isEmpty(empl_password))
        {
            Toast.makeText(this, "There are required fields empty", Toast.LENGTH_SHORT).show();}
        else{
            String empl_id = "4"; //databaseManagers.push().getKey();
            Employee employee = new Employee(empl_id, empl_name, empl_num, empl_password);
     //       databaseEmployees.child(empl_id).setValue(employee);
        //    Toast.makeText(v.getContext(), "Data Submitted", Toast.LENGTH_SHORT).show();
        }
    }
}
