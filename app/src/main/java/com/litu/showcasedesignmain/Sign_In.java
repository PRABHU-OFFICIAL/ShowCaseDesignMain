package com.litu.showcasedesignmain;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Objects;

public class Sign_In extends AppCompatActivity {

    public static String PREFS_NAME = "MyPrefsFile";

    EditText email , password;
    Button register , login;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        register = findViewById(R.id.signup_link_btn);
        login = findViewById(R.id.login_btn);

        checkUserLogin();


        register.setOnClickListener(view -> {
            startActivity(new Intent(Sign_In.this , Sign_Up.class));
            finish();
        });

        login.setOnClickListener(view -> {
            checkValidity(email);
            checkValidity(password);

            if(valid){
                fAuth.signInWithEmailAndPassword(email.getText().toString() , password.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            Toast.makeText(Sign_In.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(Objects.requireNonNull(fAuth.getUid()));

                            String dataS = "Seller DashBoard";
                            String dataB = "Buyer DashBoard";
                            df.get().addOnSuccessListener(documentSnapshot -> {
                                if (documentSnapshot.get("chooseSeller") != null){
                                    startActivity(new Intent(Sign_In.this , MainActivity.class)
                                    .putExtra("name" , dataS));
                                    finish();
                                }
                                else {
                                    startActivity(new Intent(Sign_In.this, MainActivity.class)
                                    .putExtra("name" , dataB));
                                    finish();
                                }
                            }).addOnFailureListener(e -> Toast.makeText(Sign_In.this, e.getMessage() , Toast.LENGTH_SHORT).show());
                        }).addOnFailureListener(e -> Toast.makeText(Sign_In.this, e.getMessage() , Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void checkUserLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // DocumentReference df = fStore.collection("Users").document(Objects.requireNonNull(fAuth.getUid()));

        if (user != null) {
            // User is signed in
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }

        else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
    }

    public void checkValidity(EditText editText){
        if (editText.getText().toString().isEmpty()){
            editText.setError("Can't left Empty");
            valid = false;
        }else{
            valid = true;
        }
    }

}