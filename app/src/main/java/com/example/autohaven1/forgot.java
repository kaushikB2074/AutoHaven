package com.example.autohaven1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {
  EditText edtemail;
  Button btn;
  ImageButton back;
 String txtemail;

 FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        edtemail = findViewById(R.id.reemail);
        btn = findViewById(R.id.fpbtn);
        back = findViewById(R.id.backbtn);
        mAuth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgot.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtemail = edtemail.getText().toString().trim();
                if (!TextUtils.isEmpty(txtemail))
                {
                    ResetPassword();
                }
                else
                {
                    edtemail.setError("cant be empty");
                }
            }
        });

    }

    private void ResetPassword() {
        btn.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(txtemail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(forgot.this, "Reset Password Link Has Been Sent To Your Email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(forgot.this,login.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(forgot.this, "Error -"+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}