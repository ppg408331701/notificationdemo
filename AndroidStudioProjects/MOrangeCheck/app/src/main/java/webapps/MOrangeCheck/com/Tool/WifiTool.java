package webapps.MOrangeCheck.com.Tool;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ppg777 on 2016/12/13.
 */

public class WifiTool {

    private WifiManager wifiManager;//存放周围wifi热点对象的列表
    ArrayList<ScanResult> list;
    private Context context;

    public WifiTool(Context context){
        this.context=context;
    }

    public ArrayList<ScanResult> getWifi() {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);        //获得系统wifi服务
        }
        ArrayList<ScanResult> list;
        list = (ArrayList<ScanResult>) wifiManager.getScanResults();
       // sortByLevel2(list);
        return list;
    }



    //将搜索到的wifi根据信号强度从强到弱进行排序
    public void sortByLevel2(ArrayList<ScanResult> list) {
        Collections.sort(list, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult lhs, ScanResult rhs) {
                if (lhs.level < rhs.level) {
                    return 1;
                } else if (lhs.level > rhs.level) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

}
