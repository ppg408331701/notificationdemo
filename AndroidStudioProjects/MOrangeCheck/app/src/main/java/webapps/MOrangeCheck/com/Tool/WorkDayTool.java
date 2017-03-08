package webapps.MOrangeCheck.com.Tool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ppg on 2016/10/19.
 */

public class WorkDayTool {

    public static List<Date> getDates(int year, int month) {
        List<Date> dates = new ArrayList<Date>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);

        while (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) < month) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                dates.add((Date) cal.getTime().clone());
            }
            cal.add(Calendar.DATE, 1);
        }
        return dates;

    }

    public static int getWorkDaysNum(int year, int month) {
        int num = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);

        while (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) < month) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
                num++;
            }
            cal.add(Calendar.DATE, 1);
        }
        return num;

    }

    /**
     * 获得到目前为止不工作的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getNoWorkDaysNum(int year, int month, int nowday) {
        int num = 0;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        while (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) < month &&
                cal.get(Calendar.DAY_OF_MONTH) < nowday) {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
                num++;
            }
            cal.add(Calendar.DATE, 1);
        }
        return num;

    }

}
