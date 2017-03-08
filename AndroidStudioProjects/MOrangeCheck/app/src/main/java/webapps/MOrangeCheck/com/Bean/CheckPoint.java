package webapps.MOrangeCheck.com.Bean;

/**
 * Created by ppg777 on 2017/2/22.
 */

public class CheckPoint {

    private String day;
    private String time;
    private  int state;

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
