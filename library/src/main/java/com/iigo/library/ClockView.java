package com.iigo.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import static android.view.View.MeasureSpec.AT_MOST;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description A clock view.
 */
public class ClockView extends View{
    private final static int DEFAULT_EDGE_COLOR = Color.parseColor("#83CBB3");
    private final static int DEFAULT_EAR_AND_FOOT_COLOR = Color.parseColor("#F57A22");
    private final static int DEFAULT_HEAD_COLOR = Color.parseColor("#6B2831");
    private final static int DEFAULT_SCALE_AND_HANDS_COLOR = Color.BLACK;
    private final static int DEFAULT_SECOND_HAND_COLOR = Color.RED;

    //The ARC constant
    private static final double ARC_LENGTH = Math.PI * 2;
    private static final double SCALE_ARC_LENGTH_OFFSET = ARC_LENGTH / 12d;

    private static final double CLOCK_HOUR_HAND_ARC_LENGTH = SCALE_ARC_LENGTH_OFFSET;
    private static final double CLOCK_MINUTE_HAND_ARC_LENGTH = ARC_LENGTH / 60d;
    private static final double CLOCK_SECOND_HAND_ARC_LENGTH = CLOCK_MINUTE_HAND_ARC_LENGTH;

    private static final double CLOCK_START_ARC = 90 * Math.PI / 180d;

    private int mEdgeColor = DEFAULT_EDGE_COLOR;
    private int mEarColor  = DEFAULT_EAR_AND_FOOT_COLOR;
    private int mFootColor = DEFAULT_EAR_AND_FOOT_COLOR;
    private int mHeadColor = DEFAULT_HEAD_COLOR;
    private int mScaleColor = DEFAULT_SCALE_AND_HANDS_COLOR;
    private int mCenterPointColor = DEFAULT_EAR_AND_FOOT_COLOR;
    private int mHourHandColor = DEFAULT_SCALE_AND_HANDS_COLOR;
    private int mMinuteHandColor = DEFAULT_SCALE_AND_HANDS_COLOR;
    private int mSecondHandColor = DEFAULT_SECOND_HAND_COLOR;

    private Paint mClockCirclePaint, mEarPaint, mFootPaint, mHeadLinePaint, mHeadCirclePaint,
            mScalePaint, mCenterCirclePaint, mHourHandPaint, mMinHandPaint, mSecondHandPaint;

    private RectF mEarRectF;
    private Path mEarPath, mEarDstPath;
    private Matrix mMatrix;

    private float mCenterX, mCenterY, mClockStrokeWidth, mClockRadius, mHeadCircleRadius,
            mCenterCircleRadius, mFootCalculateRadius, mEarRightAngel, mEarLeftAngel,
            mFootRightAngel, mFootLeftAngel;

    private PointF mFootRightStartPointF, mFootRightEndPointF,
            mFootLeftStartPointF, mFootLeftEndPointF;

    private PointF mEarRightEndPointF, mEarLeftEndPointF;

    private float mStartScaleLength;
    private float mStopScaleLength;

    private float mStopHeadLength;

    private float mStopHourHandLength;
    private float mStopMinHandLength;
    private float mStopSecondHandLength;

    private double mScaleStartX, mScaleStartY, mScaleStopX, mScaleStopY;

    private int mHour, mMinute, mSecond;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttrs(attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth  = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //没有指定宽高,使用了wrap_content,则手动指定宽高为MATCH_PARENT
        // (No width or height is specified, wrap_content is used, and the width and height are manually specified as MATCH_PARENT)
        if (modeWidth == AT_MOST && modeHeight == AT_MOST){
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(layoutParams);
        }
    }

    private void parseAttrs(AttributeSet attrs) {
        if (attrs == null){
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClockView);
        mEarColor  = typedArray.getColor(R.styleable.ClockView_ear_color, DEFAULT_EAR_AND_FOOT_COLOR);
        mFootColor = typedArray.getColor(R.styleable.ClockView_foot_color, DEFAULT_EAR_AND_FOOT_COLOR);
        mEdgeColor = typedArray.getColor(R.styleable.ClockView_ear_color, DEFAULT_EDGE_COLOR);
        mHeadColor = typedArray.getColor(R.styleable.ClockView_head_color, DEFAULT_HEAD_COLOR);
        mScaleColor       = typedArray.getColor(R.styleable.ClockView_scale_color, DEFAULT_SCALE_AND_HANDS_COLOR);
        mCenterPointColor = typedArray.getColor(R.styleable.ClockView_center_point_color, DEFAULT_EAR_AND_FOOT_COLOR);
        mHourHandColor    = typedArray.getColor(R.styleable.ClockView_hour_hand_color, DEFAULT_SCALE_AND_HANDS_COLOR);
        mMinuteHandColor  = typedArray.getColor(R.styleable.ClockView_minute_hand_color, DEFAULT_SCALE_AND_HANDS_COLOR);
        mSecondHandColor  = typedArray.getColor(R.styleable.ClockView_second_hand_color, DEFAULT_SECOND_HAND_COLOR);
        typedArray.recycle();
    }

    private void init(){
        mClockCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mClockCirclePaint.setStyle(Paint.Style.STROKE);
        mClockCirclePaint.setColor(mEdgeColor);

        mEarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEarPaint.setStyle(Paint.Style.FILL);
        mEarPaint.setColor(mEarColor);

        mFootPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFootPaint.setStyle(Paint.Style.STROKE);
        mFootPaint.setStrokeCap(Paint.Cap.ROUND);
        mFootPaint.setStrokeJoin(Paint.Join.ROUND);
        mFootPaint.setColor(mFootColor);

        mScalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScalePaint.setStyle(Paint.Style.STROKE);
        mScalePaint.setStrokeCap(Paint.Cap.ROUND);
        mScalePaint.setStrokeJoin(Paint.Join.ROUND);
        mScalePaint.setColor(mScaleColor);

        mHeadLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeadLinePaint.setStyle(Paint.Style.STROKE);
        mHeadLinePaint.setColor(mHeadColor);

        mHeadCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHeadCirclePaint.setStyle(Paint.Style.FILL);
        mHeadCirclePaint.setColor(mHeadColor);

        mCenterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterCirclePaint.setStyle(Paint.Style.FILL);
        mCenterCirclePaint.setColor(mCenterPointColor);

        mHourHandPaint = new Paint();
        mHourHandPaint.setStrokeCap(Paint.Cap.ROUND);
        mHourHandPaint.setStrokeJoin(Paint.Join.ROUND);
        mHourHandPaint.setAntiAlias(true);
        mHourHandPaint.setColor(mHourHandColor);

        mMinHandPaint = new Paint();
        mMinHandPaint.setStrokeCap(Paint.Cap.ROUND);
        mMinHandPaint.setStrokeJoin(Paint.Join.ROUND);
        mMinHandPaint.setAntiAlias(true);
        mMinHandPaint.setColor(mMinuteHandColor);

        mSecondHandPaint = new Paint();
        mSecondHandPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecondHandPaint.setStrokeJoin(Paint.Join.ROUND);
        mSecondHandPaint.setAntiAlias(true);
        mSecondHandPaint.setColor(mSecondHandColor);

        mEarRectF = new RectF();
        mEarPath = new Path();
        mEarDstPath = new Path();
        mMatrix = new Matrix();

        mFootRightStartPointF = new PointF();
        mFootRightEndPointF   = new PointF();
        mFootLeftStartPointF  = new PointF();
        mFootLeftEndPointF    = new PointF();
        mEarRightEndPointF    = new PointF();
        mEarLeftEndPointF    = new PointF();

        mEarRightAngel = (float) (300 * Math.PI / 180);
        mEarLeftAngel = (float) (240 * Math.PI / 180);

        mFootRightAngel = (float) (60 * Math.PI / 180);
        mFootLeftAngel = (float) (120 * Math.PI / 180);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;

        int minSize = Math.min(w, h);

        mClockRadius = minSize / 3f;
        mClockStrokeWidth = mClockRadius / 7f;

        mClockCirclePaint.setStrokeWidth(mClockStrokeWidth);

        mEarRectF.set(mCenterX - mClockRadius / 3, mCenterY - mClockRadius / 3,
                mCenterX + mClockRadius / 3, mCenterY + mClockRadius / 3);

        mEarPath.reset();
        mEarPath.addArc(mEarRectF,180, 180);

        mFootCalculateRadius = mClockRadius + (minSize - mClockRadius) / 4f;

        mFootPaint.setStrokeWidth(mClockStrokeWidth * 2 / 3f);
        mScalePaint.setStrokeWidth(mClockStrokeWidth / 3f);

        mHourHandPaint.setStrokeWidth(mClockStrokeWidth / 2f);
        mMinHandPaint.setStrokeWidth(mHourHandPaint.getStrokeWidth());
        mSecondHandPaint.setStrokeWidth(mHourHandPaint.getStrokeWidth() / 2f);

        mHeadLinePaint.setStrokeWidth(mFootPaint.getStrokeWidth());
        mHeadCircleRadius   = mClockStrokeWidth / 2f;
        mCenterCircleRadius = mHeadCircleRadius;

        mStopHeadLength = mCenterY - mClockRadius - mClockStrokeWidth * 1.5f;

        mStartScaleLength = mClockRadius * 5.2f / 7f;
        mStopScaleLength  = mClockRadius * 5.6f / 7f;

        mStopHourHandLength = mClockRadius * 3.2f / 7f;
        mStopMinHandLength  = mClockRadius * 4.2f / 7f;
        mStopSecondHandLength = mClockRadius * 4.2f / 7f;

        initCoordinates();
    }

    private void initCoordinates(){
        float earRightEndX = (float) ((mClockRadius + mClockStrokeWidth) * Math.cos(mEarRightAngel) + mCenterX);
        float earRightEndY = (float) ((mClockRadius + mClockStrokeWidth) * Math.sin(mEarRightAngel) + mCenterY);
        mEarRightEndPointF.set(earRightEndX, earRightEndY);

        float earLeftEndX = (float) ((mClockRadius + mClockStrokeWidth) * Math.cos(mEarLeftAngel) + mCenterX);
        float earLeftEndY = (float) ((mClockRadius + mClockStrokeWidth) * Math.sin(mEarLeftAngel) + mCenterY);
        mEarLeftEndPointF.set(earLeftEndX, earLeftEndY);

        float footRightStartX = (float) ((mClockRadius + mClockStrokeWidth / 2) * Math.cos(mFootRightAngel) + mCenterX);
        float footRightStartY = (float) ((mClockRadius + mClockStrokeWidth / 2) * Math.sin(mFootRightAngel) + mCenterY);
        mFootRightStartPointF.set(footRightStartX, footRightStartY);

        float footRightEndX = (float) (mFootCalculateRadius * Math.cos(mFootRightAngel) + mCenterX);
        float footRightEndY = (float) (mFootCalculateRadius * Math.sin(mFootRightAngel) + mCenterY);
        mFootRightEndPointF.set(footRightEndX, footRightEndY);

        float footLeftStartX = (float) ((mClockRadius + mClockStrokeWidth / 2) * Math.cos(mFootLeftAngel) + mCenterX);
        float footLeftStartY = (float) ((mClockRadius + mClockStrokeWidth / 2) * Math.sin(mFootLeftAngel) + mCenterY);
        mFootLeftStartPointF.set(footLeftStartX, footLeftStartY);

        float footLeftEndX = (float) (mFootCalculateRadius * Math.cos(mFootLeftAngel) + mCenterX);
        float footLeftEndY = (float) (mFootCalculateRadius * Math.sin(mFootLeftAngel) + mCenterY);
        mFootLeftEndPointF.set(footLeftEndX, footLeftEndY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawHead(canvas);
        drawFoots(canvas);
        drawClock(canvas);
        drawEars(canvas);
    }

    private void drawClock(Canvas canvas){
        canvas.drawCircle(mCenterX, mCenterY, mClockRadius, mClockCirclePaint);

        //Drawing scale
        for (float a = 0; a <= ARC_LENGTH; a += SCALE_ARC_LENGTH_OFFSET) {
            mScaleStartX = mStartScaleLength * Math.cos(a) + mCenterX;
            mScaleStartY = mStartScaleLength * Math.sin(a) + mCenterY;

            mScaleStopX = mStopScaleLength * Math.cos(a) + mCenterX;
            mScaleStopY = mStopScaleLength * Math.sin(a) + mCenterY;

            canvas.drawLine((float) mScaleStartX, (float) mScaleStartY, (float) mScaleStopX, (float) mScaleStopY, mScalePaint);
        }

        //Drawing the hour and minute hand
        drawHour(canvas);
        drawMinute(canvas);
        drawSecond(canvas);
        canvas.drawCircle(mCenterX, mCenterY, mCenterCircleRadius, mCenterCirclePaint);
    }

    private void drawHour(Canvas canvas){
        float angle = (float) ((mHour % 12 + mMinute / 60f) * CLOCK_HOUR_HAND_ARC_LENGTH - CLOCK_START_ARC);

        float hourEndX = (float) (mStopHourHandLength * Math.cos(angle) + mCenterX);
        float hourEndY = (float) (mStopHourHandLength * Math.sin(angle) + mCenterY);

        canvas.drawLine(mCenterX, mCenterY, hourEndX, hourEndY, mHourHandPaint);
    }

    private void drawMinute(Canvas canvas){
        float angle = (float) (mMinute * CLOCK_MINUTE_HAND_ARC_LENGTH - CLOCK_START_ARC);

        float minEndX = (float) (mStopMinHandLength * Math.cos(angle) + mCenterX);
        float minEndY = (float) (mStopMinHandLength * Math.sin(angle) + mCenterY);

        canvas.drawLine(mCenterX, mCenterY, minEndX, minEndY, mMinHandPaint);
    }

    private void drawSecond(Canvas canvas){
        float angle = (float) (mSecond * CLOCK_SECOND_HAND_ARC_LENGTH - CLOCK_START_ARC);

        float secondEndX = (float) (mStopSecondHandLength * Math.cos(angle) + mCenterX);
        float secondEndY = (float) (mStopSecondHandLength * Math.sin(angle) + mCenterY);

        canvas.drawLine(mCenterX, mCenterY,secondEndX, secondEndY, mSecondHandPaint);
    }

    private void drawHead(Canvas canvas){
        canvas.drawLine(mCenterX, mCenterY - mClockRadius, mCenterX,
                mStopHeadLength, mHeadLinePaint);

        canvas.drawCircle(mCenterX, mStopHeadLength, mHeadCircleRadius, mHeadCirclePaint);
    }

    private void drawEars(Canvas canvas){
        mMatrix.reset();
        mEarDstPath.reset();

        mMatrix.preRotate(30, mCenterX, mCenterY);
        mMatrix.postTranslate(mEarRightEndPointF.x - mCenterX,
                mEarRightEndPointF.y - mCenterY);
        mEarPath.transform(mMatrix, mEarDstPath);
        canvas.drawPath(mEarDstPath, mEarPaint);

        mMatrix.reset();
        mEarDstPath.reset();

        mMatrix.preRotate(-30, mCenterX, mCenterY);
        mMatrix.postTranslate(mEarLeftEndPointF.x - mCenterX,
                mEarLeftEndPointF.y - mCenterY);
        mEarPath.transform(mMatrix, mEarDstPath);

        canvas.drawPath(mEarDstPath, mEarPaint);
    }

    private void drawFoots(Canvas canvas){
        canvas.drawLine(mFootRightStartPointF.x, mFootRightStartPointF.y, mFootRightEndPointF.x, mFootRightEndPointF.y, mFootPaint);
        canvas.drawLine(mFootLeftStartPointF.x, mFootLeftStartPointF.y, mFootLeftEndPointF.x, mFootLeftEndPointF.y, mFootPaint);
    }

    /**
     * To check if the input time is correct.
     * */
    private void checkTime(){
        if (mHour < 0){
            mHour = 0;
        }

        if (mHour > 24){
            mHour = 24;
        }

        if (mMinute < 0){
            mMinute = 0;
        }

        if (mMinute > 60){
            mMinute = 60;
        }

        if (mSecond < 0){
            mSecond = 0;
        }

        if (mSecond > 60){
            mSecond = 60;
        }
    }

    /**
     * Bind the clock to real time.
     *
     * @param hour The hour, it should be [0-24].
     * @param minute The minute, it should be [0-60].
     * @param second The second, it should be [0-60].
     * */
    public void setTime(int hour, int minute, int second){
        this.mHour = hour;
        this.mMinute = minute;
        this.mSecond = second;

        checkTime();

        postInvalidate();
    }
}
