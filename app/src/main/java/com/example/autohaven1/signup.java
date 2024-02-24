package com.example.autohaven1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    Button btnsign;

    EditText edtname,edtemail,edtpass,edtmob;

    String txtname,txtemail,txtpass,txtmob;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnsign = findViewById(R.id.signbtn);
        edtname = findViewById(R.id.uname);
        edtemail = findViewById((R.id.mail));
        edtmob = findViewById(R.id.mob);
        edtpass = findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();



        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtname = edtname.getText().toString();
                txtemail = edtemail.getText().toString().trim();
                txtmob = edtmob.getText().toString().trim();
                txtpass = edtpass.getText().toString().trim();

                if (!TextUtils.isEmpty(txtname))
                {
                    if (!TextUtils.isEmpty(txtemail))
                    {
                        if(txtemail.matches(emailPattern))
                        {
                            if(!TextUtils.isEmpty(txtpass))
                            {
                               if(!TextUtils.isEmpty(txtmob))
                               {
                                   SignUpUser();
                               }
                               else
                               {
                                   edtmob.setError("Mobile Number Field Can't Be Empty");
                               }
                            }
                            else
                            {
                                edtpass.setError("Password Field Can't Be Empty");
                            }
                        }
                        else
                        {
                            edtemail.setError("Invalid Email");
                        }
                    }
                    else
                    {
                        edtemail.setError("Email Field can't be Empty");
                    }
                }
                else
                {
                    edtname.setError("Full name Field can't be empty");
                }
            }
        });
    }

    private void SignUpUser() {
        btnsign.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(txtemail,txtpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String , Object> user = new HashMap<>();
                user.put("Name",txtname);
                user.put("Email",txtemail);
                user.put("Mobile",txtmob);
                user.put("pass",txtpass);

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(signup.this, "Successfully !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signup.this, login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnsign.setVisibility(View.VISIBLE);
            }
        });
    }
}