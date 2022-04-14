package com.litu.showcasedesignmain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    EditText name , email , password , username;
    CheckBox chooseSeller , chooseBuyer;
    Button register , login;
    Boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.fullname_signup);
        username = findViewById(R.id.username_signup);
        password = findViewById(R.id.password_signup);
        email = findViewById(R.id.email_signup);

        chooseBuyer = findViewById(R.id.checkBuyer);
        chooseSeller = findViewById(R.id.checkSeller);

        register = findViewById(R.id.signup_btn);
        login = findViewById(R.id.signin_link_btn);

        login.setOnClickListener(view -> {
            startActivity(new Intent(Sign_Up.this , Sign_In.class));
            finish();
        });

        // Logic for check box selection (Single Checkbox at a time)
        chooseSeller.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()){
                chooseBuyer.setChecked(false);
            }
        });

        chooseBuyer.setOnCheckedChangeListener((compoundButton, b) -> {
            if (compoundButton.isChecked()){
                chooseSeller.setChecked(false);
            }
        });

        register.setOnClickListener(view -> {
            checkValidity(name);
            checkValidity(email);
            checkValidity(password);
            checkValidity(username);

            if (valid){
                fAuth.createUserWithEmailAndPassword(email.getText().toString() , password.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(Sign_Up.this, "Account Created", Toast.LENGTH_SHORT).show();
                            assert user != null;
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", name.getText().toString());
                            userInfo.put("UserEmail", email.getText().toString());
                            userInfo.put("UserName", username.getText().toString());
                            userInfo.put("Bio", "Hey there , I am new here");
                            userInfo.put("ProfilePic", "gs://showcase-3322d.appspot.com/Default Images/profile.png");
                           // userInfo.put("Password", password.getText().toString());

                            // Specifying whether the user is a Seller or a Buyer

                            String dataS = "Seller DashBoard";
                            String dataB = "Buyer DashBoard";

                            if (!(chooseSeller.isChecked() || chooseBuyer.isChecked())){
                                Toast.makeText(Sign_Up.this, "Select Account Type", Toast.LENGTH_SHORT).show();
                            }else if (chooseBuyer.isChecked()){
                                userInfo.put("chooseBuyer" , 1);
                                df.set(userInfo);
                                startActivity(new Intent(Sign_Up.this , Sign_In.class)
                                .putExtra("name" , dataS));
                                finish();
                            }else{
                                userInfo.put("chooseSeller" , 1);
                                df.set(userInfo);
                                startActivity(new Intent(Sign_Up.this , Sign_In.class)
                                .putExtra("name" , dataB));
                                finish();
                            }
                        }).addOnFailureListener(e -> Toast.makeText(Sign_Up.this, "Failed to create Account", Toast.LENGTH_SHORT).show());
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