package webapps.MOrangeCheck.com.Tool;

import android.content.Intent;

import webapps.MOrangeCheck.com.Activity.DetailActivity;


/**
 * Created by ppg777 on 2016/12/12.
 */

public  class   App_DefaultTool {

    /**
     * 判断跳转
     */
    public static Intent juggPage(String app_deault) {
        Intent intent = new Intent();
        switch (app_deault){
            case "1":
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_MYINFOFRAGMENT);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "我的");
                break;
            case "3":
//                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_CHECKWORKFRAGMENT);
//                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "考勤");
                break;
            default:
//                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_CHECKWORKFRAGMENT);
//                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "考勤");
                break;
        }
        return intent;
    }
}
