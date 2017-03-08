package webapps.MOrangeCheck.com.Tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import webapps.MOrangeCheck.com.Activity.DetailActivity;


/**
 * Created by ppg on 2016/9/27.
 * <p>
 * Intent intent = new Intent(getActivity(), DetailActivity.class);
 * intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "账号和绑定设置");
 * intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_ACCOUNT);
 * startActivity(intent);
 */

public class IntentTool {

    public static void startByActivity(Activity activity, Intent intent) {
        if (activity == null)
            return;
        intent.setClass(activity, DetailActivity.class);
        activity.startActivity(intent);
    }

    public static void startByFragment(Context context,Fragment fragment, Intent intent) {
        if (context == null)
            return;
        intent.setClass(context, DetailActivity.class);
        fragment.startActivity(intent);
    }


}
