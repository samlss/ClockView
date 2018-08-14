package com.iigo.clockview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iigo.library.ClockHelper;
import com.iigo.library.ClockView;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description To set attrs of {@link com.iigo.library.ClockView}
 */
public class SetAttrsActivity extends AppCompatActivity {
    private ClockView clockView;
    private ClockHelper clockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_attrs);
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
}
