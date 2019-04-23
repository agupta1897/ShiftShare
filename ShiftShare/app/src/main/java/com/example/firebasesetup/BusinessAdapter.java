package com.example.firebasesetup;

/*
 * RecyclerView.Adapter
 * RecyclerView.ViewHolder
 */

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;



import java.util.List;
import java.util.Random;

public class BusinessAdapter extends  RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {

    private Context mCtx;
    private List<Business> businessList;

    public BusinessAdapter(Context mCtx, List<Business> businessList) {
        this.mCtx = mCtx;
        this.businessList = businessList;
    }

    @NonNull
    @Override
    //Creates View Holder Instance
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.business_list_layout, null);
        return new BusinessViewHolder(view);

    }

    //Binds Our Data to the viewHolder
    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {

        Business business = businessList.get(position);

        /* data from EmployeeAdapter: used for reference
        holder.textViewEmail.setText(employee.getEmail());
        holder.textViewName.setText(employee.getName());
        Random r = new Random();
        double x = 2 + r.nextDouble() * (5 -2);
        holder.textViewRating.setText( String.format("%.2f", x));
        holder.textViewNumber.setText(String.valueOf(employee.getNumber()));
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.employee_icon));
        */

        holder.textViewName.setText(business.getName());
        holder.textViewNumber.setText(business.getNumber());
        holder.textViewStreet.setText(business.getStreet());
        holder.textViewCity.setText(business.getCity());
        holder.textViewState.setText(business.getState());

    }

    @Override
    public int getItemCount() {
        return businessList.size();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    //ViewHolderClass - UI Elements
    public class BusinessViewHolder extends RecyclerView.ViewHolder implements OnClickListener{
        /*From EmployeeAdapter: used for reference
        TextView textViewEmail, textViewName, textViewRating, textViewNumber;
        ImageView imageView;
        */
        TextView textViewName, textViewNumber, textViewStreet, textViewCity, textViewState;

        public BusinessViewHolder(final View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewStreet = itemView.findViewById(R.id.textViewStreet);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            textViewState = itemView.findViewById(R.id.textViewState);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View view){

        }
    }
}
