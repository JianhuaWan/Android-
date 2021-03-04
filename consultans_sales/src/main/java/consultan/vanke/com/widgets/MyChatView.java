package consultan.vanke.com.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import consultan.vanke.com.R;
import consultan.vanke.com.bean.DataChatBean;
import consultan.vanke.com.utils.DensityUtil;


import java.util.List;


public class MyChatView extends View {
    private ValueAnimator animator;
    private float curtFraction = 1f;
    private Bitmap bitmap;
    private int width;
    private int height;


    public List<DataChatBean> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<DataChatBean> mDatas) {
        this.mDatas = mDatas;
    }

    private List<DataChatBean> mDatas;
    //圆的直径
    private float mRadius;

    //圆的粗细
    private int mStrokeWidth = DensityUtil.dip2px(getContext(), 11);
    //文字大小
    private int textSize = DensityUtil.dip2px(getContext(), 18);
    ;
    //-------------画笔相关-------------
    //圆环的画笔
    private Paint cyclePaint;
    //文字的画笔
    private Paint textPaint;
    //底部圆环颜色
    private Paint mBottomRingPaint;
    //标注的画笔
    private Paint labelPaint;

    //所占百分比
    private String text = "0%";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curtFraction = animation.getAnimatedFraction();
                Log.e("MyChatView", curtFraction + "");
                invalidate();
            }
        });
    }

    private int mHeight;
    private int mWidth;
    private int mBottomCircleColor;
    private boolean isDrawRect;

    public MyChatView(Context context) {
        this(context, null);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bottom_lines);
    }

    public MyChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bottom_lines);
    }

    public MyChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        mBottomCircleColor = typedArray.getColor(R.styleable.RoundView_bottom_ring_color, Color.parseColor("#ccf1e8"));
        isDrawRect = typedArray.getBoolean(R.styleable.RoundView_isDrawRect, false);
        init1();
    }

    private void init1() {
        initPaint();
        initAnimator();
    }

    public void startA() {
        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Rect rect = new Rect(DensityUtil.dip2px(getContext(), 30), DensityUtil.dip2px(getContext(), 30), mWidth - DensityUtil.dip2px(getContext(), 30), mHeight - DensityUtil.dip2px(getContext(), 30));
        super.onDraw(canvas);
        //画背景
        canvas.drawBitmap(bitmap, null, rect, null);
        //画底圆环
//        drawBottomRing(canvas);
        //画圆环
        drawCycle(canvas);

        //画文字和标注
        if (isDrawRect)
            drawTextAndLabel(canvas);
    }

    private void drawBottomRing(Canvas canvas) {
        float startPercent = 0;
        float sweepPercent = 360;
        canvas.drawArc(new RectF(DensityUtil.dip2px(getContext(), 20f), DensityUtil.dip2px(getContext(), 20f), mWidth - DensityUtil.dip2px(getContext(), 20f), mWidth - DensityUtil.dip2px(getContext(), 20f)), startPercent, sweepPercent, false, mBottomRingPaint);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        cyclePaint = new Paint();
        cyclePaint.setAntiAlias(true);
        cyclePaint.setStyle(Paint.Style.STROKE);
        cyclePaint.setStrokeWidth(mStrokeWidth);
        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#00ba8e"));
        textPaint.setStyle(Paint.Style.STROKE);

        textPaint.setStrokeWidth(2);
        textPaint.setTextSize(DensityUtil.dip2px(getContext(), 18));
        textPaint.setTextSize(textSize);
        //标注画笔
        labelPaint = new Paint();
        labelPaint.setAntiAlias(true);
        labelPaint.setStyle(Paint.Style.FILL);
        labelPaint.setStrokeWidth(2);
        //底部圆环的的颜色
        mBottomRingPaint = new Paint();
        mBottomRingPaint.setAntiAlias(true);// 抗锯齿效果
        mBottomRingPaint.setStyle(Paint.Style.STROKE);
        mBottomRingPaint.setColor(mBottomCircleColor);// 背景
        mBottomRingPaint.setStrokeWidth(mStrokeWidth);


    }

    /**
     * 画文字和标注
     *
     * @param canvas
     */
    private void drawTextAndLabel(Canvas canvas) {
        Rect rect = new Rect(DensityUtil.dip2px(getContext(), 27), DensityUtil.dip2px(getContext(), 31), DensityUtil.dip2px(getContext(), 37), DensityUtil.dip2px(getContext(), 41));//画一个矩形
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.parseColor("#00ba8e"));
        rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, rectPaint);

        Rect rect1 = new Rect(DensityUtil.dip2px(getContext(), 42), DensityUtil.dip2px(getContext(), 31), DensityUtil.dip2px(getContext(), 52), DensityUtil.dip2px(getContext(), 41));//画一个矩形
        Paint rectPaint1 = new Paint();
        rectPaint1.setColor(Color.parseColor("#6397ee"));
        rectPaint1.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect1, rectPaint1);

        Rect rect2 = new Rect(DensityUtil.dip2px(getContext(), 57), DensityUtil.dip2px(getContext(), 31), DensityUtil.dip2px(getContext(), 67), DensityUtil.dip2px(getContext(), 41));//画一个矩形
        Paint rectPaint2 = new Paint();
        rectPaint2.setColor(Color.parseColor("#ccf1e8"));
        rectPaint2.setStyle(Paint.Style.FILL);

        canvas.drawRect(rect2, rectPaint2);
    }


    private void resetParams() {
        width = getWidth();
        height = getHeight();
    }

    /**
     * 画圆环
     *
     * @param canvas
     */
    private void drawCycle(Canvas canvas) {
        float startPercent = -90;
        float sweepPercent = 0;
        RectF rectF = null;
        int postion = 0;
        if (null == mDatas || mDatas.size() == 0) {
            return;
        }
        for (DataChatBean bean : mDatas) {
            float percent = bean.getValue();
            List<String> colors = bean.getColors();
            mStrokeWidth = DensityUtil.dip2px(getContext(), 18);
            cyclePaint.setStrokeWidth(mStrokeWidth);
            rectF = new RectF(DensityUtil.dip2px(getContext(), 18f), DensityUtil.dip2px(getContext(), 18f), mWidth - DensityUtil.dip2px(getContext(), 18f), mWidth - DensityUtil.dip2px(getContext(), 18f));
            LinearGradient linearGradient = new LinearGradient(
                    mWidth / 2, 0,
                    mWidth / 2, mHeight,
                    Color.parseColor(colors.get(0)), Color.parseColor(colors.get(1)),
                    Shader.TileMode.MIRROR
            );

            cyclePaint.setShader(linearGradient);
            startPercent = sweepPercent + startPercent;
            //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
            sweepPercent = percent * curtFraction * 360;
            canvas.drawArc(rectF, startPercent, sweepPercent, false, cyclePaint);
            postion++;
        }

    }


}