package ppg.com.yanlibrary.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

/**
 * hahaYJ
 */
public class BaseActvity extends AppCompatActivity {

	protected KeyDownProxy keyDownProxy;

    private boolean isDestroy = false;

    public void setKeyDownProxy(KeyDownProxy keyDownProxy) {
		this.keyDownProxy = keyDownProxy;
	}

    public boolean isDestroy() {
        return isDestroy;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy = true;
    }
}
