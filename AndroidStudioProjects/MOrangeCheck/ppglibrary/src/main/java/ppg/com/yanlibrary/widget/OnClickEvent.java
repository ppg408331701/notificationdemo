package ppg.com.yanlibrary.widget;

import android.view.View;

public abstract class OnClickEvent implements View.OnClickListener {

    public static long lastTime;

    public abstract void singleClick(View v);

    @Override
    public void onClick(View v) {
        if (onDoubClick()) {
            return;
        }
        singleClick(v);
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time < 800) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}