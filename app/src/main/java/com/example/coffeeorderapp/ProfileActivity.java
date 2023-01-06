package com.example.coffeeorderapp;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.example.coffeeorderapp.Model.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView emailTv, UsernameTv,UsernameTv2,phoneTv;
    private String fullname,emaill,phonenumber;
    private FirebaseAuth authProfile;
    FloatingActionButton  fab;
    Button signout;
    NavController navController;
    ImageView home, profile, order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        emailTv = findViewById(R.id.emailTv);
        UsernameTv = findViewById(R.id.UsernameTv);
        phoneTv = findViewById(R.id.phoneTv);
        UsernameTv2 = findViewById(R.id.UsernameTv2);
        authProfile = FirebaseAuth.getInstance();
        signout = findViewById(R.id.signOut);
        home = findViewById(R.id.home);
        profile = findViewById(R.id.profile);
        fab = findViewById(R.id.fab);
        order=findViewById(R.id.order);
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if (firebaseUser == null) {
            Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            showAllUser(firebaseUser);
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));


            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));

            }
        });

        }


    private void showAllUser(FirebaseUser firebaseUser) {
            String userId=firebaseUser.getUid();
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("User");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel=snapshot.getValue(UserModel.class);
                if(userModel !=null){
                    fullname=userModel.username;
                    emaill=userModel.email;
                    phonenumber=userModel.phoneNumber;
                    emailTv.setText(emaill);
                    phoneTv.setText(phonenumber);
                    UsernameTv.setText(fullname);
                    UsernameTv2.setText(fullname);
                    
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
