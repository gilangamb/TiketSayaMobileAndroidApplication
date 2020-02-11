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

public class SuccessBuyAct extends AppCompatActivity {

    Button btn_dashboard, btn_view_ticket;
    Animation app_splash, btt, ttb;
    ImageView icon_success_buy;
    TextView app_title, app_subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy);

        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_ticket);
        icon_success_buy = findViewById(R.id.icon_success_buy);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);

        app_splash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this, R.anim.ttb);

        icon_success_buy.startAnimation(app_splash);
        btn_dashboard.startAnimation(btt);
        btn_view_ticket.startAnimation(btt);
        app_subtitle.startAnimation(ttb);
        app_title.startAnimation(ttb);

        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoviewticket = new Intent(SuccessBuyAct.this,MyProfileAct.class);
                startActivity(gotoviewticket);
                finish();
            }
        });

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotodashboard = new Intent(SuccessBuyAct.this,HomeAct.class);
                startActivity(gotodashboard);
                finish();
            }
        });
    }
}
