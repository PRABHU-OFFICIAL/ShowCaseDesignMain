package com.litu.showcasedesignmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Button logout = findViewById(R.id.logout);
        TextView dashboard = findViewById(R.id.dashboard);

        Intent intent = getIntent();
        String value = intent.getStringExtra("name");
        dashboard.setText(value);

        Button add_videos = findViewById(R.id.add_videos);
        String temp = dashboard.getText().toString();
        if (temp.contains("Buyer")){
            add_videos.setVisibility(View.GONE);
        }

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this , Sign_Up.class));
            finish();
        });
    }
}