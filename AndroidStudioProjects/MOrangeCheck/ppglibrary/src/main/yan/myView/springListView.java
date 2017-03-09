package myView;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

public class springListView extends ListView{
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 150;
    private static final float SCROLL_RATIO = 2f;// 阻尼系数
    private Context mContext;
    private int mMaxYOverscrollDistance;

    public springListView(Context context){
        super(context);
        mContext = context;
        initBounceListView();
    }

    public springListView(Context context, AttributeSet attrs){
        super(context, attrs);
        mContext = context;
        initBounceListView();
    }

    public springListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView(){
        //get the density of the screen and do some maths with it on the max overscroll distance
        //variable so that you get similar behaviors no matter what the screen size

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent){
        //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;

        int newDeltaY = deltaY;
        int delta = (int) (deltaY * SCROLL_RATIO);
        if (delta != 0) newDeltaY = delta;
        return super.overScrollBy(deltaX, delta, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

}