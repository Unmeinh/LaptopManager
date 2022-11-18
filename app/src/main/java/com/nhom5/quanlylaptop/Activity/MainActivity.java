package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom5.quanlylaptop.R;
import com.nhom5.quanlylaptop.Support.ChangeType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.showText);
        TextView textView1 = findViewById(R.id.showMoney);
        ChangeType changeType = new ChangeType();

        textView.setText(String.valueOf(changeType.stringMoneyToInt("13.990.000₫")));
        textView1.setText(changeType.intMoneyToString(13990000));

        startActivity(new Intent(MainActivity.this, PickRole_Activity.class));
    }
}