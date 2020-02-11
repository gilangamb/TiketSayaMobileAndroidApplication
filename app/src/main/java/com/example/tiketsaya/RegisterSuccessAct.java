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

public class RegisterSuccessAct extends AppCompatActivity {

    Button btn_explore;
    Animation app_splash, btt, ttb;
    ImageView ic_success;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_success);

        btn_explore = findViewById(R.id.btn_explore);
        ic_success = findViewById(R.id.ic_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        ic_success.startAnimation(app_splash);
        app_title.setAnimation(ttb);
        app_subtitle.setAnimation(ttb);
        btn_explore.setAnimation(btt);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotohome = new Intent(RegisterSuccessAct.this,HomeAct.class);
                startActivity(gotohome);
                finish();
            }
        });
    }
}
