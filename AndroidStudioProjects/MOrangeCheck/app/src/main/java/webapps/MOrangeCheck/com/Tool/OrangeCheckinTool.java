package webapps.MOrangeCheck.com.Tool;

import android.content.Context;
import android.net.wifi.ScanResult;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ppg.com.yanlibrary.utils.ToastUtil;

/**
 * Created by ppg777 on 2017/1/4.
 */

public class OrangeCheckinTool {

    public static ArrayList<String> getWIFIList(ArrayList<ScanResult> list ) {
        ArrayList<String> bssidList= new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (ScanResult scanResult : list) {
                bssidList.add(scanResult.BSSID);
            }
        }
        return bssidList;
    }

    /**
     * @param context
     * @param bean
     * @return 返回1代表未到上班时间, 返回2代表超出上班时间, 返回0代表上班时间
     */
//    public static int comparaTime(Context context, RuleBean bean) {
//        int Checkin_timeNum = bean.getCheckin_time().size();
//        String Checkin_time1_1 = "8";
//        String Checkin_time1_2 = "0";//上班时间
//        String Checkin_time2_1 = "18";
//        String Checkin_time2_2 = "0";//下班时间
//        //当签次数到只有两次时
//        if (Checkin_timeNum == 2) {
//            try {
//                String Checkin_time1 = bean.getCheckin_time().get(0);
//                Checkin_time1_1 = Checkin_time1.split(":")[0];
//                Checkin_time1_2 = Checkin_time1.split(":")[1];
//                String Checkin_time2 = bean.getCheckin_time().get(1);
//                Checkin_time2_1 = Checkin_time2.split(":")[0];
//                Checkin_time2_2 = Checkin_time2.split(":")[1];
//            } catch (Exception e) {
//                ToastUtil.toast2_bottom(context, "时间转换错误");
//            }
//        }
//
//        long currenTime;
//        long startTime;
//        long endTime;
//        final long LeavelTimes;
//
//        //当前时间
//        currenTime = System.currentTimeMillis();
//        //设置8:30分为上班开始时间
//        Calendar startcalendar = Calendar.getInstance();
//        startcalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(Checkin_time1_1));
//        startcalendar.set(Calendar.MINUTE, Integer.valueOf(Checkin_time1_2));
//        startcalendar.set(Calendar.SECOND, 0);
//        startTime = startcalendar.getTimeInMillis();
//
//        //设置下班时间为上班时间的9小时30分后
//        Calendar endcalendar = Calendar.getInstance();
//        endcalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(Checkin_time2_1));
//        endcalendar.set(Calendar.MINUTE, Integer.valueOf(Checkin_time2_2));
//        endcalendar.set(Calendar.SECOND, 0);
//        endTime = endcalendar.getTimeInMillis();
//        //下班时间-当前时间,得到剩余的时间
//        LeavelTimes = endTime - currenTime;
//
//        if (currenTime < startTime) {
//            return 1;
//        }
//        if (LeavelTimes < 0) {
//            return 2;
//        }
//
//
//        return 0;
//    }


//    public static boolean CheckWifi(List<DevicesBean> devicesBeanList, ArrayList<String> ls) {
//        if (devicesBeanList.size() >= 0) {
//            for (DevicesBean devicesBean : devicesBeanList) {
//                if (ls.contains(devicesBean.getAp_bssid())) {
//                    return true;
//                }
//            }
//            return false;
//        } else {
//            return false;
//        }
//
//    }

}
