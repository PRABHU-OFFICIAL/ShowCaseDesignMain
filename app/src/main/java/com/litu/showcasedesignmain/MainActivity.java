package com.litu.showcasedesignmain;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.litu.showcasedesignmain.Fragment.HomeFragment;
import com.litu.showcasedesignmain.Fragment.NotificationFragment;
import com.litu.showcasedesignmain.Fragment.ProfileFragment;
import com.litu.showcasedesignmain.Fragment.SearchFragment;

import org.w3c.dom.Text;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button edit;
    BottomNavigationView bottomNavigationView;
    ChipNavigationBar chipNavigationBar;
    HomeFragment homeFragment =new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    NotificationFragment notificationFragment = new NotificationFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottomNavigationView = findViewById(R.id.bottom_nav_bar);
       chipNavigationBar = findViewById(R.id.bottom_nav_bar);
         getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();

        //badgeDrawable for notification

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int item) {
                switch (item){
                    case  R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,homeFragment).commit();
                        return ;
                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,searchFragment).commit();
                        return ;
                    case R.id.nav_notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,notificationFragment).commit();
                        return;
                    case R.id.nav_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,profileFragment).commit();
                        return ;
                }
                return ;
            }







         });



       // Objects.requireNonNull(getSupportActionBar()).hide();
       /* edit = findViewById(R.id.edit_profile);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EditProfile.class));
                finish();
            }
        });


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
            startActivity(new Intent(MainActivity.this , Sign_In.class));
            finish();
        });

        */
    }
}