package com.example.projectaplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDesecration:
                Intent new1 = new Intent(this, Desecration.class);
                startActivity(new1);
                break;
            case R.id.btnBalaboba:
                Balaboba.Str = "";
                Intent new2 = new Intent(this, Balaboba.class);
                startActivity(new2);
                break;
            case R.id.btnCompliment:
                Intent newIntent = new Intent(this, Compliment.class);
                startActivity(newIntent);
                break;
            case R.id.btnYouAreToday:
                Intent newYouAreToday = new Intent(this, YouAreToday.class);
                startActivity(newYouAreToday);
                break;
        }
    }
}