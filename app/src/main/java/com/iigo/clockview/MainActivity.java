package com.iigo.clockview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.iigo.library.ClockHelper;
import com.iigo.library.ClockView;

public class MainActivity extends AppCompatActivity {
    private ClockView clockView;
    private ClockHelper clockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        clockView = findViewById(R.id.clockView);
        clockHelper = new ClockHelper(clockView);
        clockHelper.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clockHelper.stop();
    }

    public void onGoOff(View view) {
        clockHelper.goOff();
    }

    public void onStartSettingAttrs(View view) {
        startActivity(new Intent(this, SetAttrsActivity.class));
    }
}
