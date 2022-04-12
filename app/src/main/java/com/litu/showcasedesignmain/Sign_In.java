package com.litu.showcasedesignmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public void checkValidity(EditText editText){
        if (editText.getText().toString().isEmpty()){
            editText.setError("Can't left Empty");
            valid = false;
        }else{
            valid = true;
        }
    }
}