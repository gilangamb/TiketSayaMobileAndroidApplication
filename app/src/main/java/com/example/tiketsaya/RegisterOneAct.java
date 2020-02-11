package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterOneAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue;
    EditText username, password, email_address;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_one);

        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email_address = findViewById(R.id.email_address);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //after clicked disable
                btn_continue.setEnabled(false);
                btn_continue.setText("Loading...");

                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, username.getText().toString());
                editor.apply();

                final String conUsername = username.getText().toString();
                final String conPassword = password.getText().toString();
                final String conEmailAddress = email_address.getText().toString();

                if (conUsername.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi biodata diatas",Toast.LENGTH_SHORT).show();
                    //after clicked disable
                    btn_continue.setEnabled(true);
                    btn_continue.setText("Continue");
                }
                else if (conPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi biodata diatas",Toast.LENGTH_SHORT).show();
                    //after clicked disable
                    btn_continue.setEnabled(true);
                    btn_continue.setText("Continue");
                }
                else if (conEmailAddress.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Lengkapi biodata diatas",Toast.LENGTH_SHORT).show();
                    //after clicked disable
                    btn_continue.setEnabled(true);
                    btn_continue.setText("Continue");
                }
                else {
                    //simpan kepada database
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(username.getText().toString());
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataSnapshot.getRef().child("username").setValue(conUsername);
                            dataSnapshot.getRef().child("password").setValue(conPassword);
                            dataSnapshot.getRef().child("email_address").setValue(conEmailAddress);
                            dataSnapshot.getRef().child("user_balance").setValue(900);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    Intent gotoregistertwo = new Intent(RegisterOneAct.this, RegisterTwoAct.class);
                    startActivity(gotoregistertwo);
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregistertwo = new Intent(RegisterOneAct.this, GetStarted.class);
                startActivity(gotoregistertwo);
            }
        });

    }
}
