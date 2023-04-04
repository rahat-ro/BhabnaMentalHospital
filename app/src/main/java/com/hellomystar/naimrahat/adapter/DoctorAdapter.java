package com.hellomystar.naimrahat.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hellomystar.naimrahat.bhabnamentalhospital.AppointmentActivity;
import com.hellomystar.naimrahat.bhabnamentalhospital.R;
import com.hellomystar.naimrahat.helper.DoctorModel;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{

    private Context mCtx;
    private List<DoctorModel> doctorModelList;

    public DoctorAdapter(Context mCtx, List<DoctorModel> doctorModelList) {
        this.mCtx = mCtx;
        this.doctorModelList = doctorModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.doctor_model_card_view,viewGroup, false);
        return new DoctorAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        DoctorModel doctorModel = doctorModelList.get(i);
        Glide.with(mCtx)
                .load(doctorModel.getDoctor_img())
                .into(holder.doctor_img);


        holder.doctor_name.setText(doctorModel.getDoctor_name());
        holder.doctor_qualification.setText(doctorModel.getDoctor_qualification());
        holder.doctor_expertise.setText(doctorModel.getDoctor_expertise());
        holder.doctor_designation.setText(doctorModel.getDoctor_designation());
        holder.doctor_chamber.setText(doctorModel.getDoctor_chamber());


        holder.btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mCtx, AppointmentActivity.class);
                //mIntent.putExtra("BarTitle",getString());
                mIntent.putExtra("dr img",doctorModelList.get(holder.getAdapterPosition()).getDoctor_img());
                mIntent.putExtra("dr name", doctorModelList.get(holder.getAdapterPosition()).getDoctor_name());
                mIntent.putExtra("dr expertise",doctorModelList.get(holder.getAdapterPosition()).getDoctor_expertise());
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorModelList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView doctor_name,doctor_qualification,doctor_designation,doctor_expertise,
                doctor_chamber;
        Button btnAppointment;
        ImageView doctor_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doctor_name = (TextView) itemView.findViewById(R.id.doctor_name);
            doctor_img = (ImageView) itemView.findViewById(R.id.doctor_img);
            doctor_qualification = (TextView) itemView.findViewById(R.id.doctor_qualification);
            doctor_designation = (TextView) itemView.findViewById(R.id.doctor_designation);
            doctor_expertise = (TextView) itemView.findViewById(R.id.doctor_expertise);
            doctor_chamber = (TextView) itemView.findViewById(R.id.doctor_chamber);
            btnAppointment = (Button) itemView.findViewById(R.id.btn_appointment);

        }
    }
}
