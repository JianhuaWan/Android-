package consultan.vanke.com.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import consultan.vanke.com.utils.DensityUtil;

public class CanvasView extends View {
    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private String info;
    private int[] sweepAngle = {6, 14, 45, 15, 5, 10};
    private String num;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setLayerType(View.LAYER_TYPE_SOFTWARE, new Paint());
        drawSaveRestore(canvas);

    }

    @SuppressLint("ResourceAsColor")
    private void drawSaveRestore(Canvas canvas) {
        if (this.info == null) {
            return;
        }
        //如果 注释掉 save restore 两个圆在屏幕中间重合
        canvas.save();
        Paint paint = new Paint();
        paint.setStrokeWidth(DensityUtil.dip2px(8));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //坐标原点移到屏幕中心
        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        //以屏幕中心为坐标原点在（60,50）为圆心处绘制红色圆
        //恢复画布
        //恢复画布后，坐标原点（0,0）默认在屏幕左上角，
        //即以屏幕左上角为坐标原点在（60,50）为圆心处绘制黑色圆
        paint.setColor(Color.parseColor("#f5f5f5"));
        canvas.drawCircle(0, 0, DensityUtil.dip2px(125), paint);
        canvas.drawCircle(0, 0, DensityUtil.dip2px(115), paint);
        canvas.drawCircle(0, 0, DensityUtil.dip2px(105), paint);
        canvas.drawCircle(0, 0, DensityUtil.dip2px(95), paint);
        canvas.drawCircle(0, 0, DensityUtil.dip2px(85), paint);
        canvas.drawCircle(0, 0, DensityUtil.dip2px(75), paint);
        canvas.restore();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.RED);
        RectF rectF1 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(125), getMeasuredHeight() / 2 - DensityUtil.dip2px(125), getMeasuredWidth() / 2
                + DensityUtil.dip2px(125), getMeasuredHeight() / 2 + DensityUtil.dip2px(125));
        canvas.drawArc(rectF1, -90, (float) (sweepAngle[0] * 3.6), false, paint);
        paint.setColor(Color.GREEN);
        RectF rectF2 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(115), getMeasuredHeight() / 2 - DensityUtil.dip2px(115), getMeasuredWidth() / 2
                + DensityUtil.dip2px(115), getMeasuredHeight() / 2 + DensityUtil.dip2px(115));
        canvas.drawArc(rectF2, -90, (float) (sweepAngle[1] * 3.6), false, paint);
        paint.setColor(Color.BLUE);
        RectF rectF3 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(105), getMeasuredHeight() / 2 - DensityUtil.dip2px(105), getMeasuredWidth() / 2
                + DensityUtil.dip2px(105), getMeasuredHeight() / 2 + DensityUtil.dip2px(105));
        canvas.drawArc(rectF3, -90, (float) (sweepAngle[2] * 3.6), false, paint);
        paint.setColor(Color.BLACK);
        RectF rectF4 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(95), getMeasuredHeight() / 2 - DensityUtil.dip2px(95), getMeasuredWidth() / 2
                + DensityUtil.dip2px(95), getMeasuredHeight() / 2 + DensityUtil.dip2px(95));
        canvas.drawArc(rectF4, -90, (float) (sweepAngle[3] * 3.6), false, paint);
        paint.setColor(Color.CYAN);
        RectF rectF5 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(85), getMeasuredHeight() / 2 - DensityUtil.dip2px(85), getMeasuredWidth() / 2
                + DensityUtil.dip2px(85), getMeasuredHeight() / 2 + DensityUtil.dip2px(85));
        canvas.drawArc(rectF5, -90, (float) (sweepAngle[4] * 3.6), false, paint);
        paint.setColor(Color.YELLOW);
        RectF rectF6 = new RectF(getMeasuredWidth() / 2 - DensityUtil.dip2px(75), getMeasuredHeight() / 2 - DensityUtil.dip2px(75), getMeasuredWidth() / 2
                + DensityUtil.dip2px(75), getMeasuredHeight() / 2 + DensityUtil.dip2px(75));
        canvas.drawArc(rectF6, -90, (float) (sweepAngle[5] * 3.6), false, paint);
        Paint paint1 = new Paint();
        paint1.setColor(Color.GRAY);
        paint1.setStrokeWidth(DensityUtil.dip2px(3));
        paint1.setTextSize(DensityUtil.dip2px(15));
        canvas.drawText(this.info, getMeasuredWidth() / 2 - DensityUtil.dip2px(30), getMeasuredHeight() / 2 - DensityUtil.dip2px(10), paint1);
        paint1.setTextSize(DensityUtil.dip2px(20));
        paint1.setColor(Color.BLACK);
        paint1.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(this.num, getMeasuredWidth() / 2 - DensityUtil.dip2px(6), getMeasuredHeight() / 2 + DensityUtil.dip2px(10), paint1);
    }


    public void setDatas(String info, String num) {
        this.info = info;
        this.num = num;
    }

}
