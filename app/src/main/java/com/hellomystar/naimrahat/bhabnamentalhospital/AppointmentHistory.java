package com.hellomystar.naimrahat.bhabnamentalhospital;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hellomystar.naimrahat.adapter.AptAdapter;
import com.hellomystar.naimrahat.adapter.DoctorAdapter;
import com.hellomystar.naimrahat.helper.Appointment;
import com.hellomystar.naimrahat.helper.DoctorModel;
import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.URLs;
import com.hellomystar.naimrahat.helper.User;
import com.hellomystar.naimrahat.helper.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentHistory extends AppCompatActivity {

    private TextView userID,TextEmail;
    private ImageView back;
    private RecyclerView recyclerView1;
    private List<Appointment> appointmentList;
    private LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);


        recyclerView1 = findViewById(R.id.app_rv);
        recyclerView1.setHasFixedSize(true);
        //recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        linearLayoutManager = new LinearLayoutManager(AppointmentHistory.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager);


        //getting the current user
        userID = (TextView) findViewById(R.id.textUserName) ;
        TextEmail = (TextView) findViewById(R.id.textEmail);
        User user = SharedPrefManager.getInstance(this).getUser();
        userID.setText(String.valueOf(user.getId()));
        TextEmail.setText(user.getEmail());

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentHistory.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        appointmentList = new ArrayList<>();

        loadAppointment();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                loadAppointment();

            }
        });
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }


    private void loadAppointment() {
        //first getting the values
        final String id = userID.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();


        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_APPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);
                        swipeLayout.setRefreshing(false);
                        try {

                            //converting response to json object
                            //converting response to json object
                           // JSONObject obj = new JSONObject(response);

                            JSONArray array = new JSONArray(response);

                            // clear data and couldn't double
                            if(appointmentList != null){
                                appointmentList.clear();
                            }

                            for (int i = 0; i < array.length(); i++) {


                                JSONObject userJson = array.getJSONObject(i);
                                appointmentList.add(new Appointment(
                                        userJson.getString("ap_id"),
                                        userJson.getString("drname"),
                                        userJson.getString("expertise"),
                                        userJson.getString("name"),
                                        userJson.getString("id"),
                                        userJson.getString("email"),
                                        userJson.getString("date"),
                                        userJson.getString("status"),
                                        userJson.getString("drImg")

                                ));
                            }

                            AptAdapter adapter = new AptAdapter(AppointmentHistory.this,appointmentList);
                            adapter.notifyDataSetChanged();
                            recyclerView1.setAdapter(adapter);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("email", email);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
