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

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hellomystar.naimrahat.adapter.DoctorAdapter;
import com.hellomystar.naimrahat.helper.DoctorModel;
import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private static final String URL_PRODUCTS = "https://hellomystar.com/apps/project/doctor.php";
    RecyclerView recyclerView1;
    private List<DoctorModel> doctorModelList;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView back;
    private TextView textViewName;
    SwipeRefreshLayout swipeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        recyclerView1 = findViewById(R.id.dr_rv);
        recyclerView1.setHasFixedSize(true);
        //recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true));
        layoutManager = new LinearLayoutManager(this);
        //linearLayoutManager.setReverseLayout(true);
        // linearLayoutManager.setStackFromEnd(true);
        recyclerView1.setLayoutManager(layoutManager);


        textViewName = (TextView) findViewById(R.id.textName);
        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();
        textViewName.setText(user.getName());

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        doctorModelList = new ArrayList<>();
        loadDoctorList();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                loadDoctorList();

            }
        });
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void loadDoctorList() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            /**ProgressDialog pDialog = new ProgressDialog(PressActivity.this);
                             pDialog.setMessage("Loading...");
                             pDialog.show();**/

                            JSONArray array = new JSONArray(response);

                            // clear data and couldn't double
                            if(doctorModelList != null){
                               doctorModelList.clear();
                            }


                            for (int i = 0; i < array.length(); i++) {

                                JSONObject doctor = array.getJSONObject(i);
                                doctorModelList.add(new DoctorModel(
                                        doctor.getInt("doctor_id"),
                                        doctor.getString("doctor_name"),
                                        doctor.getString("doctor_img"),
                                        doctor.getString("doctor_qualification"),
                                        doctor.getString("doctor_designation"),
                                        doctor.getString("doctor_expertise"),
                                        doctor.getString("doctor_chamber")


                                ));
                            }

                            DoctorAdapter adapter = new DoctorAdapter(DoctorActivity.this,doctorModelList);
                            recyclerView1.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }

        );
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);


    }
}
