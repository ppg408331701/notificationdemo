package webapps.MOrangeCheck.com.Tool;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 高德定位
 * Created by ppg on 2016/3/29.
 */
public class LbsMapLocation implements AMapLocationListener {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private Context context;

    public LbsMapLocation(Context context) {
        this.context = context;
    }


    /**
     * 开始定位,在接口回调结果
     *
     * @param locationEnd
     */
    public void initLocation(onLocationEnd locationEnd) {
        if (context == null) {
            Log.e("amapLocation", "LbsMapLocation的context == null");
            return;
        }
        this.locationEnd = locationEnd;
        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        intLocationOption();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    public void intLocationOption() {

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    private onLocationEnd locationEnd;

    public interface onLocationEnd {
        void getMyPositionOK(double Latitude, double Longitude, String adress, AMapLocation amapLocation);

        void getMyPositionFailed(String Result);
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码
//                amapLocation.getAoiName();//获取当前定位点的AOI信息
                Log.e("amapLocation", "getLatitude=" + amapLocation.getLatitude());
                Log.e("amapLocation", "getLongitude=" + amapLocation.getLongitude());
                Log.e("amapLocation", "getAddress=" + amapLocation.getAddress());
                Log.e("amapLocation", "getProvince=" + amapLocation.getProvince());
                Log.e("amapLocation", "getCity=" + amapLocation.getCity());
                Log.e("amapLocation", "getDistrict=" + amapLocation.getDistrict());
                Log.e("amapLocation", "getStreet=" + amapLocation.getStreet());
                Log.e("amapLocation", "getStreetNum=" + amapLocation.getStreetNum());
                stopLocation();
                if (locationEnd != null) {
                    locationEnd.getMyPositionOK(amapLocation.getLatitude(), amapLocation.getLongitude(), amapLocation.getAddress(), amapLocation);
                }
            } else {
                if (locationEnd != null) {
                    locationEnd.getMyPositionFailed("定位失败" + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("amapLocation", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
