package com.example.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PersonalArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_area);
    }

    Intent gi = new Intent();
    String nameP = gi.getStringExtra("nnn");

    public void ex(View view) {
        Intent ssi = new Intent(PersonalArea.this,Expenses.class);
        ssi.putExtra("nn", nameP);
        startActivity(ssi);
    }
}
