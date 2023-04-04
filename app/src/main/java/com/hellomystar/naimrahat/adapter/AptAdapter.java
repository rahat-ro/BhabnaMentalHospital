package com.hellomystar.naimrahat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hellomystar.naimrahat.bhabnamentalhospital.MainActivity;
import com.hellomystar.naimrahat.bhabnamentalhospital.R;
import com.hellomystar.naimrahat.helper.Appointment;
import com.hellomystar.naimrahat.helper.DoctorModel;
import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.URLs;
import com.hellomystar.naimrahat.helper.User;
import com.hellomystar.naimrahat.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AptAdapter extends RecyclerView.Adapter<AptAdapter.ViewHolder>{

    private Context mCtx;
    private List<Appointment> appointmentList;

    public AptAdapter(Context mCtx, List<Appointment> appointmentList) {
        this.mCtx = mCtx;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.apt_card_view,viewGroup, false);
        return new AptAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        Appointment appointment = appointmentList.get(i);
        Glide.with(mCtx)
                .load(appointment.getDrImg())
                .into(viewHolder.drImg);

        viewHolder.appId.setText(appointment.getAp_id());
        viewHolder.doctorName1.setText(appointment.getDrname());
        viewHolder.doctorExpertise1.setText(appointment.getExpertise());
        viewHolder.textName1.setText(appointment.getName());
        viewHolder.textUsername1.setText(appointment.getId());
        viewHolder.textViewEmail1.setText(appointment.getEmail());
        viewHolder.editTextDate1.setText(appointment.getDate());
        viewHolder.status.setText(appointment.getStatus());

        final String ap_id = viewHolder.appId.getText().toString().trim();
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteAppoint(id);
                //if everything is fine
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_DELETE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //progressBar.setVisibility(View.GONE);

                                try {
                                    //converting response to json object
                                    JSONObject obj = new JSONObject(response);

                                    //if no error in response
                                    if (!obj.getBoolean("error")) {
                                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();




                                        //starting the profile activity


                                    } else {
                                        Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("ap_id", ap_id);

                        return params;
                    }
                };

                VolleySingleton.getInstance(mCtx).addToRequestQueue(stringRequest);


            }
        });


    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView doctorName1,doctorExpertise1,textName1,textUsername1,textViewEmail1,editTextDate1,status,appId;
        ImageView drImg,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorName1 = (TextView) itemView.findViewById(R.id.doctorName1);
            doctorExpertise1 = (TextView) itemView.findViewById(R.id.doctorExpertise1);
            textName1 = (TextView) itemView.findViewById(R.id.textName1);
            textUsername1 = (TextView) itemView.findViewById(R.id.textUsername1);
            textViewEmail1 = (TextView) itemView.findViewById(R.id.textViewEmail1);
            editTextDate1 = (TextView) itemView.findViewById(R.id.editTextDate1);
            status = (TextView) itemView.findViewById(R.id.status);
            drImg = (ImageView) itemView.findViewById(R.id.drImg);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            appId = (TextView) itemView.findViewById(R.id.appID);



        }
    }


}
