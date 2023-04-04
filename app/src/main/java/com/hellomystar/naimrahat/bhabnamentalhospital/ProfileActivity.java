package com.hellomystar.naimrahat.bhabnamentalhospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hellomystar.naimrahat.helper.SharedPrefManager;
import com.hellomystar.naimrahat.helper.User;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewName,textViewAge,textViewUsername,
            textViewEmail, textViewGender,textName;

    private ImageView back;
    Button editInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewName = (TextView) findViewById(R.id.textName);
        textViewAge = (TextView) findViewById(R.id.textAge);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewName.setText(user.getName());
        textViewAge.setText(user.getAge());
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());

        textName = (TextView) findViewById(R.id.textViewName);
        textName.setText(user.getName());
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        editInfo = (Button)findViewById(R.id.editInfo);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,UpdateActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });


    }
}
