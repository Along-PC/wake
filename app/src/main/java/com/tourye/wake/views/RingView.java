package com.tourye.wake.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by along
 * 创建时间 2017/9/26 0026.
 */

public class RingView extends View {

    private Paint mPaintRing;//圆环画笔
    private Paint mPaintRingBack;//圆环背景画笔
    private Paint mPaintEdge;//边缘画笔
    private Paint mPaintInner;//内部画笔
    private Paint mPaintPonit;//终点画笔
    private int measuredHeight;
    private int measuredWidth;
    private float radius;
    private float degreeLast;
    private float degreeCurrent;

    private int widthEdge=20;//边缘宽度
    private int widthCircle=20;//圆环宽度


    static {
        Log.e("along","静态代码块");
    }

    {
        Log.e("along","构造代码块");
    }

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Log.e("along","构造器");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("along","测量");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("along","布局");
    }

    public void initPaint(){

        Log.e("along","初始化");
        measuredHeight = getMeasuredHeight()-getPaddingTop()-getPaddingBottom();
        measuredWidth = getMeasuredWidth()-getPaddingLeft()-getPaddingRight();

        radius = measuredHeight > measuredWidth ? measuredWidth /2: measuredHeight /2;

        mPaintRing=new Paint();
        mPaintRing.setAntiAlias(true);
        mPaintRing.setStrokeWidth(widthCircle);
        mPaintRing.setStyle(Paint.Style.STROKE);
        int[] colors=new int[]{Color.parseColor("#138AFF"),Color.parseColor("#01B7FF")};
        SweepGradient sweepGradient=new SweepGradient(radius, radius,colors,null);
        //设置渐变色
        mPaintRing.setShader(sweepGradient);
        //设置画笔圆头
//        mPaintRing.setStrokeCap(Paint.Cap.ROUND);

        mPaintEdge=new Paint();
        mPaintEdge.setAntiAlias(true);
        mPaintEdge.setStrokeWidth(widthEdge);
        mPaintEdge.setColor(Color.parseColor("#1a1092ff"));
        mPaintEdge.setStyle(Paint.Style.STROKE);

        mPaintInner=new Paint();
        mPaintInner.setColor(Color.parseColor("#171092"));
        mPaintInner.setStyle(Paint.Style.FILL);

        mPaintRingBack=new Paint();
        mPaintRingBack.setColor(Color.parseColor("#324465"));
        mPaintRingBack.setStyle(Paint.Style.STROKE);
        mPaintRingBack.setStrokeWidth(widthCircle);

        mPaintPonit=new Paint();
        mPaintPonit.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
//        canvas.translate(getMeasuredWidth()/2,getMeasuredWidth()/2); //将坐标中心平移到要围绕的坐标点x,y
        canvas.rotate(89,getMeasuredWidth()/2,getMeasuredWidth()/2);

        initPaint();

        Log.e("along","绘制");

        RectF rectF = new RectF(widthEdge+widthCircle/2, widthEdge+widthCircle/2,
                measuredWidth-widthEdge-widthCircle/2, measuredHeight-widthEdge-widthCircle/2);
        RectF rect = new RectF(widthEdge/2, widthEdge/2, measuredWidth-widthEdge/2,
                measuredHeight-widthEdge/2);

        canvas.drawArc(rectF,0,360,false,mPaintRingBack);
        canvas.drawArc(rectF,degreeLast,degreeCurrent,false,mPaintRing);
        canvas.drawArc(rect,0,360,false,mPaintEdge);
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,
                getMeasuredWidth()/2-widthEdge-widthCircle,mPaintInner);

        float x = (float) (getMeasuredWidth()/2 + (radius-widthEdge-widthCircle/2) * Math.cos(degreeCurrent   *   3.14   /180));
        float y = (float) (getMeasuredHeight()/2 + (radius-widthEdge-widthCircle/2) * Math.sin(degreeCurrent   *   3.14   /180));

        mPaintPonit.setColor(Color.WHITE);
        canvas.drawCircle(x,y,14,mPaintPonit);
        mPaintPonit.setColor(Color.parseColor("#01B7FF"));
        canvas.drawCircle(x,y,widthCircle/2,mPaintPonit);
        canvas.restore();

    }

    public void startAnim() {
        //添加属性动画
        ValueAnimator valueAnimator= ValueAnimator.ofFloat(0f,360f);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                degreeCurrent=animatedValue.floatValue();
                invalidate();
            }
        });
        valueAnimator.setTarget(degreeCurrent);
        valueAnimator.start();
    }

}
