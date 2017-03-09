package ppg.com.yanlibrary;

/**
 * @author jie.yang
 */
public class GlobalVar {

    public static final int LIST_AMIN_LOADMORE_STOP = -0xff;
    public static final int INFOLIST_PAGE_SIZE = 20;
    public static final int RECOMMEND_PAGE_SIZE = 10;
    public static final String PAY_PRAM_PAY_ALL = "支付宝全款";
    public static final String PAY_PRAM_PAY_PART = "支付宝订金";
    public static final String PAY_PRAM_NO_PAY = "货到付款";
    public static final String PAY_PRAM_AGAIN_BINDING = "重新绑定";
    // 存储用户名的key
    public static final String UPDATA_SPECIAL = "special";
    // 存储用户名的key
    public static final String USERNAME_KEY = "username_key";
    public static int screenHeight;
    public static int screenWidth;
    public static String version;
    public static String device;
    public static String IMEI;
    // 存储用户名的key
    public static boolean isCheckUpdate = false;
    // 项目安装文件夹
    public static String APP_FLODER = "dingdong";
    public static final String APP_INSTALL_FLODER = APP_FLODER + "/install";
    public static final String APP_LOG_FLODER = APP_FLODER + "/logs";
    public static final String APP_IMAGE_FLODER = APP_FLODER + "/images";
    /**
     * gps相关
     */
    public static String POS_LAT = "";
    public static String POS_LNG = "";
    public static String AREA_NAME = "";
    public static String CITY_NAME = "南宁市";
    public static String PROVINCE = "广西";
    public static String CITY_ID = "";
    public static int LOCATION_SCAN_SPAN = 5000;
    public static String COOR_TYPE = "bd09ll";

    public static void setFloderName(String name) {
        APP_FLODER = name;
    }

}
