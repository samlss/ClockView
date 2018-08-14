package com.iigo.library;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description The clock helper, to help {@link ClockView} connect with the real time, and, to test the go off effect.
 */
public class ClockHelper extends TimerTask {
    private ClockView mClockView;
    private Timer mTimer;

    public ClockHelper(ClockView clockView){
        this.mClockView = clockView;
    }

    public void start(){
        stop();

        mTimer = new Timer();
        mTimer.schedule(this, 0, 1000);
    }

    public void stop(){
        if (mTimer != null){
            mTimer.cancel();
        }
    }

    /**
     * This method to test the effect of clock goes off.
     * */
    public void goOff(){
        if (mClockView == null){
            return;
        }

        RotateAnimation rotateAnimation = new RotateAnimation(-15, 15, mClockView.getPivotX(), mClockView.getPivotY());
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(15);
        rotateAnimation.setRepeatMode(ValueAnimator.RESTART);
        rotateAnimation.setFillAfter(false);
        mClockView.startAnimation(rotateAnimation);
    }

    @Override
    public void run() {
        if (mClockView != null){
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            mClockView.setTime(hour, minute, second);
        }
    }
}
