package com.example.tiketsaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GetStarted extends AppCompatActivity {

    Button btn_signin, btn_new_acc;
    ImageView emblem_app;
    TextView intro_app;
    Animation ttb,btt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);


        btn_signin = findViewById(R.id.btn_signin);
        btn_new_acc = findViewById(R.id.btn_new_acc);
        emblem_app = findViewById(R.id.emblem_app);
        intro_app = findViewById(R.id.intro_app);

        emblem_app.startAnimation(ttb);
        intro_app.startAnimation(ttb);
        btn_signin.startAnimation(btt);
        btn_new_acc.startAnimation(btt);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah02 = new Intent(GetStarted.this, SignInAct.class);
                startActivity(pindah02);
                finish();
            }
        });

        btn_new_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoregis = new Intent(GetStarted.this, RegisterOneAct.class);
                startActivity(gotoregis);
                finish();
            }
        });
    }
}
