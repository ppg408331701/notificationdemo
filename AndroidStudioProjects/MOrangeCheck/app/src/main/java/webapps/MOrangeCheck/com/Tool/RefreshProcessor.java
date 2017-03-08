package webapps.MOrangeCheck.com.Tool;


import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;


/**
 * Created by lcodecore on 2016/11/26.
 */

public class RefreshProcessor {
    private final int mTouchSlop;
    private float mTouchX, mTouchY;


    public RefreshProcessor(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean interceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = ev.getX() - mTouchX;
                float dy = ev.getY() - mTouchY;
                if (Math.abs(dx) <= Math.abs(dy)) {//滑动允许最大角度为45度
                    if (Math.abs(dy) > 0 && Math.abs(dy) > mTouchSlop) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }


}
