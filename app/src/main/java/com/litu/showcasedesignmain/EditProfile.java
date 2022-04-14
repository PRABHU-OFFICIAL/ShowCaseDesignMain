package com.litu.showcasedesignmain;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    ImageView save;
    ImageView close_edit,image_profile;
    Button logout;
    Button delete_profile;
    EditText fullname,username,bio;
    FirebaseUser firebaseUser;
    StorageReference storageRef;
    private Uri mImageuri;
    private StorageTask uploadTask;
    TextView change_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        save = findViewById(R.id.save_info_profile_btn);
        fullname = findViewById(R.id.edit_fullname);
        username = findViewById(R.id.edit_username);
        bio = findViewById(R.id.edit_bio);
        close_edit = findViewById(R.id.close);
        logout = findViewById(R.id.logout_btn);
        delete_profile = findViewById(R.id.delete_account_btn);
        change_img = findViewById(R.id.change_image_text_btn);
        firebaseUser = fauth.getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();



        save.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                if(fullname.getText().toString().isEmpty() || username.getText().toString().isEmpty() || bio.getText().toString().isEmpty())
                {
                    Toast.makeText(EditProfile.this,"one or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }
            else {
                    DocumentReference docRef = fstore.collection("Users").document(firebaseUser.getUid());
                    Map<String, Object> edited = new HashMap<>();
                    edited.put("FullName", fullname.getText().toString());
                    edited.put("UserName", username.getText().toString());
                    edited.put("Bio", bio.getText().toString());
                    docRef.update(edited);
                    Toast.makeText(EditProfile.this, "Saved changes", Toast.LENGTH_SHORT).show();


                }
            }
        });

        close_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditProfile.this,MainActivity.class));
                finish();
            }
        });

       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EditProfile.this, Sign_In.class));

            }
        });

       delete_profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

               user.delete()
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {
                                   Log.d(TAG, "User account deleted.");
                                   startActivity(new Intent(EditProfile.this, Sign_In.class));
                               }
                           }
                       });
               FirebaseFirestore db = FirebaseFirestore.getInstance();
               db.collection("Users").document(firebaseUser.getUid())
                       .delete()
                       .addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               Log.d(TAG, "DocumentSnapshot successfully deleted!");
                               startActivity(new Intent(EditProfile.this, Sign_In.class));

                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Log.w(TAG, "Error deleting document", e);
                               startActivity(new Intent(EditProfile.this, Sign_In.class));
                           }
                       });

           }

       });
        






    }

}