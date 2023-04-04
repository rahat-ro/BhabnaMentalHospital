package com.hellomystar.naimrahat.bhabnamentalhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.URLs;
import com.hellomystar.naimrahat.helper.User;
import com.hellomystar.naimrahat.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    EditText editTextName,editTextAge,editTextUsername, editTextPassword,editTextGender;
    //RadioGroup radioGroupGender;

    TextView textViewId,textName,editTextEmail;

    private ImageView back;

    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        textViewId = (TextView) findViewById(R.id.textViewId1);
        //......
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (TextView) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextGender = (EditText) findViewById(R.id.editTextGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();
        textViewId.setText(String.valueOf(user.getId()));
        editTextName.setText(user.getName());
        editTextAge.setText(user.getAge());
        editTextUsername.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());
        editTextGender.setText(user.getGender());

        textName = (TextView) findViewById(R.id.textViewName);
        textName.setText(user.getName());
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        update = (Button) findViewById(R.id.buttonUpdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                Toast.makeText(UpdateActivity.this, "Updating and log out", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void updateData() {
        final String id = textViewId.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String age = editTextAge.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String gender = editTextGender.getText().toString().trim();

        //first we will do the validations


        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter Name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(age)) {
            editTextAge.setError("Please enter Age");
            editTextAge.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Confirm your password");
            editTextPassword.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(gender)) {
            editTextPassword.setError("Enter your gender");
            editTextPassword.requestFocus();
            return;
        }


        ///......................

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_UPDATE,
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




                                //storing the user in shared preferences



                                //starting the profile activity
                                SharedPrefManager.getInstance(getApplicationContext()).logout();

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
                params.put("id",id);
                params.put("name", name);
                params.put("age",age);
                params.put("username", username);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
}
