package com.example.autohaven1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
   Button btn;
   TextView fp;
   EditText logmail,logpass;
   String txtlogmail,txtlogpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn=findViewById(R.id.logbtn);
        logmail = findViewById(R.id.logemail);
        logpass = findViewById(R.id.logpass);
        fp = findViewById(R.id.fp);

        mAuth = FirebaseAuth.getInstance();
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, forgot.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtlogmail = logmail.getText().toString().trim();
                txtlogpass = logpass.getText().toString().trim();

                if(!TextUtils.isEmpty(txtlogmail))
                {
                    if(!TextUtils.isEmpty(txtlogpass))
                    {
                        SignInUser();
                    }
                    else
                    {
                        logpass.setError("Password Field Can't Be Empty");
                    }
                }
                else
                {
                   logmail.setError("Email Field Can't Be Empty");
                }
            }
        });
    }

    private void SignInUser() {
        btn.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(txtlogmail,txtlogpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(login.this, gooMapsActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btn.setVisibility(View.VISIBLE);
                Toast.makeText(login.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}