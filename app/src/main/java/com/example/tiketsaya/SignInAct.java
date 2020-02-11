package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInAct extends AppCompatActivity {

    TextView new_acc;
    Button btn_signin;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        new_acc = findViewById(R.id.new_acc);
        btn_signin = findViewById(R.id.btn_signin);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //after clicked disable
                btn_signin.setEnabled(false);
                btn_signin.setText("Loading...");

                String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if (username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"masukkan username atau password",Toast.LENGTH_SHORT).show();
                    btn_signin.setEnabled(true);
                    btn_signin.setText("Sign in");
                }
                else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"masukkan username atau password",Toast.LENGTH_SHORT).show();
                    btn_signin.setEnabled(true);
                    btn_signin.setText("Sign in");
                }
                else {
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child(username);

                    //interaksi dengan database
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                //ambil data password dari firebase
                                String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();

                                //validasi input password dengan password firebase
                                if (password.equals(passwordFromFirebase)){

                                    //simpan data account (key) ke local
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, xusername.getText().toString());
                                    editor.apply();

                                    //lanjut ke home
                                    Intent gotohome = new Intent(SignInAct.this,HomeAct.class);
                                    startActivity(gotohome);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Password Salah!", Toast.LENGTH_SHORT).show();
                                    btn_signin.setEnabled(true);
                                    btn_signin.setText("Sign in");
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Username Tidak Ada!", Toast.LENGTH_SHORT).show();
                                btn_signin.setEnabled(true);
                                btn_signin.setText("Sign in");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"Kesalahan Database!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregis = new Intent(SignInAct.this,RegisterOneAct.class);
                startActivity(gotoregis);
                finish();
            }
        });
    }
}
