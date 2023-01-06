package com.example.coffeeorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etRegEmail,etRegPhone,etRegName;
    TextInputEditText etRegPassword;
    TextView tvLoginHere;
    Button btnRegister;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPass);
        tvLoginHere = findViewById(R.id.tvLoginHere);
        btnRegister = findViewById(R.id.btnRegister);
        etRegPhone=findViewById(R.id.etRegPhone);
        etRegName=findViewById(R.id.etRegName);

        databaseReference= FirebaseDatabase.getInstance().getReference("User");
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view ->{
            createUser();
        });

        tvLoginHere.setOnClickListener(view ->{
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
String email,password,name,phoneNumber;
    private void createUser(){
         email = etRegEmail.getText().toString().trim();
         password = etRegPassword.getText().toString().trim();
         name=etRegName.getText().toString().trim();
         phoneNumber=etRegPhone.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            etRegEmail.setError("Email cannot be empty");
            etRegEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            etRegPassword.setError("Password cannot be empty");
            etRegPassword.requestFocus();
        } else if (TextUtils.isEmpty(name)){
            etRegName.setError("Username cannot be empty");
            etRegName.requestFocus();
        }else if (TextUtils.isEmpty(phoneNumber)){
            etRegPhone.setError("Phone number cannot be empty");
            etRegPhone.requestFocus();
        } else if(phoneNumber.length()!=8){
            etRegPhone.setError("Invalid phone number");
            etRegPhone.requestFocus();

        } else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String timestamp=""+System.currentTimeMillis();
                        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("uid",uid);
                        hashMap.put("email",email);
                        hashMap.put("password",password);
                        hashMap.put("username",name);
                        hashMap.put("phoneNumber",phoneNumber);
                        hashMap.put("Type","User");
                        hashMap.put("timestamp",timestamp);
                        FirebaseDatabase.getInstance().getReference("User")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if( task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }else{
                                            Toast.makeText(RegisterActivity.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                }
                                });

                    }else{
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}