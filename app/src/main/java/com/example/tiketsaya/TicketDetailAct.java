package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_buy_ticket;
    TextView location_ticket, title_ticket, festivalInfo, spotInfo, wifiInfo, short_desc;
    ImageView headerDetail;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);


        btn_back = findViewById(R.id.btn_back);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        title_ticket = findViewById(R.id.title_ticket);
        festivalInfo = findViewById(R.id.festivalInfo);
        spotInfo = findViewById(R.id.spotInfo);
        wifiInfo = findViewById(R.id.wifiInfo);
        short_desc = findViewById(R.id.short_desc);
        headerDetail = findViewById(R.id.headerDetail);

        //mengambil nilai dari intent dihalaman Home
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //ambil data dari Firebase based on intent

        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //input nilai dari firebase ke halaman interface
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                spotInfo.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifiInfo.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festivalInfo.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailAct.this).load(dataSnapshot.child("url_thumbnail")
                        .getValue().toString()).into(headerDetail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotocheckout = new Intent(TicketDetailAct.this,TicketCheckoutAct.class);
                gotocheckout.putExtra("jenis_tiket",jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });

    }
}
