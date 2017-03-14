package webapps.MOrangeCheck.com.Views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LT;

/**
 * Created by ppg on 16/9/2 19:28
 * ppg_77@qq.com
 * <p/>
 */
public class NothinCircularProgressar extends View {


    // view宽度
    private int width;


    //  圆环起始角度
    private final static float mStartAngle = 90f;

    // 圆环结束角度
    private final static float mEndAngle = 360f;

    //外层圆环画笔
    private Paint mMiddleArcPaint;


    //进度圆环画笔
    private Paint mArcProgressPaint;

    //半径
    private int radius;

    //外层矩形
    private RectF mMiddleRect;

    //内层矩形
    private RectF mInnerRect;

    //进度矩形
    private RectF mMiddleProgressRect;

    // 最小数字
    private int mMinNum = 0;

    // 最大数字
    private int mMaxNum = 40;

    // 当前进度
    private float mCurrentAngle = 0f;

    //总进度
    private float mTotalAngle = 360f;


    private Context mContext;
    private int circularSize;
    private int lineSize;
    private int backgroundLineColor;
    private int frontLineColor;
    private int maxWidth;
    private int maxHeight;

    public NothinCircularProgressar(Context context) {
        this(context, null);
        mContext = context;
    }


    public NothinCircularProgressar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        mContext = context;
    }


    public NothinCircularProgressar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }


    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NothinCircularProgressar);
        circularSize = typedArray.getDimensionPixelSize(R.styleable.NothinCircularProgressar_nothig_circularSize, dp2px(100));
        lineSize = typedArray.getDimensionPixelSize(R.styleable.NothinCircularProgressar_nothig_circularlineSize, dp2px(3));
        backgroundLineColor = typedArray.getColor(R.styleable.NothinCircularProgressar_nothig_backgroundLineColor, getResources().getColor(android.R.color.darker_gray));
        frontLineColor = typedArray.getColor(R.styleable.NothinCircularProgressar_nothig_frontLineColor, getResources().getColor(android.R.color.holo_orange_dark));

        //外层圆环画笔
        mMiddleArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddleArcPaint.setStrokeWidth(lineSize);
        mMiddleArcPaint.setColor(backgroundLineColor);
        mMiddleArcPaint.setStyle(Paint.Style.STROKE);
        mMiddleArcPaint.setAlpha(90);


        //外层进度画笔
        mArcProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcProgressPaint.setStrokeWidth(lineSize);
        mArcProgressPaint.setColor(frontLineColor);
        mArcProgressPaint.setStyle(Paint.Style.STROKE);
        mArcProgressPaint.setStrokeCap(Paint.Cap.ROUND);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int computedWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
        int computedHeight = resolveMeasured(heightMeasureSpec, minimumHeight);

        setMeasuredDimension(computedWidth, computedHeight);
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxWidth = w;
        maxHeight = h;
        width = circularSize;
        radius = width / 2;
        LT.ee("circularSize=" + circularSize);
        LT.ee("maxWidth=" + maxWidth);
        LT.ee("maxHeight=" + maxHeight);

        mMiddleRect = new RectF((maxWidth / 2) - radius, (maxHeight / 2) - radius, (maxWidth / 2)
                + radius, (maxHeight / 2) + radius);

        mMiddleProgressRect = new RectF((maxWidth / 2) - radius, (maxHeight / 2) - radius, (maxWidth / 2)
                + radius, (maxHeight / 2) + radius);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        drawMiddleArc(canvas);

        drawRingProgress(canvas);


    }


    /**
     * 绘制外层圆环进度和小圆点
     */
    private void drawRingProgress(Canvas canvas) {

        Path path = new Path();
        path.addArc(mMiddleProgressRect, mStartAngle, mCurrentAngle);
        PathMeasure pathMeasure = new PathMeasure(path, false);

        canvas.drawPath(path, mArcProgressPaint);
        //起始角度不为0时候才进行绘制小圆点
        if (mCurrentAngle == 0) {
            return;
        }
//        canvas.drawBitmap(bitmap, matrix, mBitmapPaint);
//        mBitmapPaint.setColor(Color.WHITE);
//        canvas.drawCircle(pos[0], pos[1], 8, mBitmapPaint);
    }


    /**
     * 绘制外层圆环
     */
    private void drawMiddleArc(Canvas canvas) {
        canvas.drawArc(mMiddleRect, mStartAngle, mEndAngle, false, mMiddleArcPaint);
    }


    public void setSesameValues(int values, int totel) {
        if (values >= 0) {
            mMaxNum = values;

            mTotalAngle = ((float) values / (float) totel) * 360f;
            startAnim();
        }
    }


    public void startAnim() {

        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(mCurrentAngle, mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(2000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mCurrentAngle = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAngleAnim.start();

        // mMinNum = 350;
        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(2000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }


    /**
     * dp2px
     */
    public int dp2px(int values) {

        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }

}
