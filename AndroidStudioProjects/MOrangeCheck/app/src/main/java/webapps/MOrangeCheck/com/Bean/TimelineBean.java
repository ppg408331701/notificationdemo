package webapps.MOrangeCheck.com.Bean;

/**
 * Created by ppg777 on 2017/3/6.
 */

public class TimelineBean {

    private String mMessage;
    private String mDate;
    private String mName;
    /** 0已同意,1等待审批,3拒绝 */
    private int mStatus;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }
}
