package com.hellomystar.naimrahat.bhabnamentalhospital;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.URLs;
import com.hellomystar.naimrahat.helper.User;
import com.hellomystar.naimrahat.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AppointmentActivity extends AppCompatActivity {

    private TextView doctorName,doctorExpertise,textName,userID,textViewEmail,textViewName;
    private EditText editTextDate;
    private ImageView drImg,back;
    private Button ConAppointment;


    private static final String URL_APPOINTMENT = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        drImg = (ImageView) findViewById(R.id.drImg);
        doctorName = (TextView) findViewById(R.id.doctorName);
        doctorExpertise = (TextView) findViewById(R.id.doctorExpertise);
        textName = (TextView) findViewById(R.id.textName);
        userID = (TextView) findViewById(R.id.textUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        editTextDate = (EditText) findViewById(R.id.editTextDate);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();
        textName.setText(user.getName());
        userID.setText(String.valueOf(user.getId()));
        textViewEmail.setText(user.getEmail());

        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){

            Glide.with(this)
                    .load(mBundle.get("dr img"))
                    .into(drImg);
            // titleBar30.setText(mBundle.getString("BarTitle"));
            //Blog_BackgroundImage.setImageResource(mBundle.getInt("Image1"));
            doctorName.setText(mBundle.getString("dr name"));
            doctorExpertise.setText(mBundle.getString("dr expertise"));

        }

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewName.setText(user.getName());
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AppointmentActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        ConAppointment = (Button) findViewById(R.id.Con_appointment);
        ConAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmAppointment();
            }
        });


    }

    private void ConfirmAppointment() {
        final String drname = doctorName.getText().toString().trim();
        final String expertise = doctorExpertise.getText().toString().trim();
        final String name = textName.getText().toString().trim();
        final String id = userID.getText().toString().trim();
        final String email = textViewEmail.getText().toString().trim();
        final String date = editTextDate.getText().toString().trim();

        //first we will do the validations


        if (TextUtils.isEmpty(date)) {
            editTextDate.setError("Please enter Date");
            editTextDate.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_APPOINTMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                //JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                /*User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("age"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("gender")
                                );*/

                                //storing the user in shared preferences
                                //SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                //startActivity(new Intent(getApplicationContext(), AppointmentActivity.class));
                                //finish();
                                Intent i = new Intent(AppointmentActivity.this,AppointmentHistory.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
                params.put("drname", drname);
                params.put("expertise",expertise);
                params.put("name", name);
                params.put("id", id);
                params.put("email", email);
                params.put("date", date);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
