package com.example.firebasesetup;

/*
* RecyclerView.Adapter
* RecyclerView.ViewHolder
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;



import java.util.List;
import java.util.Random;

public class EmployeeAdapter extends  RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context mCtx;
    private List<Employee> employeeList;

    public EmployeeAdapter(Context mCtx, List<Employee> employeeList) {
        this.mCtx = mCtx;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    //Creates View Holder Instance
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.search_results_list_layout, null);

        return new EmployeeViewHolder(view);

    }

    //Binds Our Data to the viewHolder
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {

        Employee employee = employeeList.get(position);

        holder.textViewEmail.setText(employee.getEmail());
        holder.textViewName.setText(employee.getName());
        Random r = new Random();
        double x = 2 + r.nextDouble() * (5 -2);
        holder.textViewRating.setText( String.format("%.2f", x));
        holder.textViewNumber.setText(String.valueOf(employee.getNumber()));


        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.employee_icon));

    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    //ViewHolderClass - UI Elements
    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmail, textViewName, textViewRating, textViewNumber;
       ImageView imageView;

        public EmployeeViewHolder(View itemView) {
            super(itemView);

            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
           imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
