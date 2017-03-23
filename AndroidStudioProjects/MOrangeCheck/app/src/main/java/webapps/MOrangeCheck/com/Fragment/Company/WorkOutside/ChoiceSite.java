package webapps.MOrangeCheck.com.Fragment.Company.WorkOutside;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import rx.functions.Action1;
import utils.SnackbarUtils;
import webapps.MOrangeCheck.com.Activity.DetailActivity;
import webapps.MOrangeCheck.com.Application.AppApplication;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.IntentTool;
import webapps.MOrangeCheck.com.Tool.LbsMapLocation;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentChoicesiteBinding;

/**
 * Created by ppg777 on 2017/3/21.
 */

public class ChoiceSite extends LoadingFragment implements View.OnClickListener,
        AMap.OnMapClickListener, AMap.InfoWindowAdapter, AMap.OnMarkerClickListener,
        PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener {

    FragmentChoicesiteBinding binding;
    private AMap mAMap;

    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private LatLonPoint lp = new LatLonPoint(22.81057, 108.366678);
    private Marker locationMarker; // 选择的点
    private Marker detailMarker;
    private Marker mlastMarker;
    private PoiSearch poiSearch;
    private PoiOverlay poiOverlay;// poi图层
    private List<PoiItem> poiItems = new ArrayList<>(0);// poi数据
    private Double mLatitude;
    private Double mLongitude;
    private String CityCode;
    private BaseQuickAdapter<PoiItem, BaseViewHolder> adapter;
    //    private int markers = R.mipmap.poi_marker_pressed;


    public ChoiceSite() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_choicesite, container, false);
        binding = DataBindingUtil.bind(root);
        binding.flSearch.setOnClickListener(this);
        binding.mapView.onCreate(savedInstanceState);
        binding.bottomSheet.setItemAnimator(new DefaultItemAnimator());
        binding.bottomSheet.addItemDecoration(new LeftPaddingDividerItemDecoration(
                mActivity, LeftPaddingDividerItemDecoration.VERTICAL, 0));
        binding.bottomSheet.setLayoutManager(new LinearLayoutManager(mActivity));
        initPoiList(poiItems);
        if (TextUtils.isEmpty(AppApplication.getSpUtils().getString("LocationCity"))) {
            getPosition();
        } else {
            init();
        }
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fl_search:
                // 创建一个包含过渡动画信息的 ActivityOptions 对象
                intent = new Intent();
                intent.putExtra(DetailActivity.INTENT_FRAGMENT_INDEX_KEY, DetailActivity.FRAGMENT_SEARCHSITE);
                intent.putExtra(DetailActivity.INTENT_TITLE_KEY, "");
                IntentTool.startByFragment(mActivity, ChoiceSite.this, intent);
                break;
        }
    }

    private void getPosition() {
        if (Build.VERSION.SDK_INT >= 23) {
            new RxPermissions(mActivity)
                    .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                StartLocation();
                            } else {
                                SnackbarUtils.showNoDisMiss(getRoot(), "获取权限失败,无法定位.", ContextCompat.getColor(mActivity
                                        , R.color.orange_primary_dark), "返回", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mActivity.finish();
                                    }
                                });
                            }
                        }
                    });
        } else {
            StartLocation();
        }
    }

    /**
     * 开始定位
     */
    private void StartLocation() {
        LbsMapLocation lbsMapLocation = new LbsMapLocation(mActivity);
        lbsMapLocation.initLocation(new LbsMapLocation.onLocationEnd() {
            @Override
            public void getMyPositionOK(double Latitude, double Longitude, String adress, AMapLocation amapLocation) {
                AppApplication.getSpUtils().putString("LocationCity", amapLocation.getCityCode());
                AppApplication.getSpUtils().putString("Latitude", String.valueOf(Latitude));
                AppApplication.getSpUtils().putString("Longitude", String.valueOf(Longitude));
                init();
            }

            @Override
            public void getMyPositionFailed(String Result) {
                SnackbarUtils.showIndefiniteSnackbar(getRoot(), Result, 5000, ContextCompat.getColor(mActivity
                        , R.color.orange_primary_dark), ContextCompat.getColor(mActivity, R.color.orange_primary_text));
            }
        });
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        //初始化当前定位的数据
        mLatitude = Double.valueOf(AppApplication.getSpUtils().getString("Latitude"));
        mLongitude = Double.valueOf(AppApplication.getSpUtils().getString("Longitude"));
        CityCode = AppApplication.getSpUtils().getString("LocationCity");
        if (mAMap == null) {
            mAMap = binding.mapView.getMap();
            mAMap.setOnMapClickListener(this);
            mAMap.setOnMarkerClickListener(this);
            mAMap.setInfoWindowAdapter(this);
            lp = new LatLonPoint(mLatitude, mLongitude);
        }
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp.getLatitude(), lp.getLongitude()), 18f));
        mAMap.setOnCameraChangeListener(this);
        doSearchQuery();
    }


    /**
     * 开始进行poi搜索
     * /keyWord表示搜索字符串，
     * //第二个参数表示POI搜索类型，二者选填其一，
     * //POI搜索类型共分为以下20种：汽车服务|汽车销售|
     * //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
     * //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
     * //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
     * //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     */
    protected void doSearchQuery() {
        mAMap.setOnMapClickListener(null);// 进行poi搜索时清除掉地图点击事件
        currentPage = 0;
        query = new PoiSearch.Query("", "购物服务|商务住宅|地名地址信息|交通设施服务|公共设施", CityCode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(mActivity, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }


    private void initPoiList(List<PoiItem> poiItems) {
        if (adapter == null) {
            adapter = new BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_poi, poiItems) {
                @Override
                protected void convert(BaseViewHolder helper, PoiItem item) {
                    helper.setText(R.id.tv_poi_title, item.getTitle())
                            .setText(R.id.tv_poi_content, item.getSnippet());
                }
            };
            binding.bottomSheet.setAdapter(adapter);
            if (poiItems.size() == 0) {
                adapter.setEmptyView(R.layout.empty_page, binding.bottomSheet);
            }
        } else {
            adapter.setNewData(poiItems);
        }
    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        initPoiList(poiItems);
                        //清除POI信息显示
//                        whetherToShowDetailInfo(false);
                        //并还原点击marker样式
                        if (mlastMarker != null) {
                            resetlastmarker();
                        }
                        //清理之前搜索结果的marker
                        if (poiOverlay != null) {
                            poiOverlay.removeFromMap();
                        }
                        mAMap.clear();
                        poiOverlay = new PoiOverlay(mAMap, poiItems);
                        poiOverlay.addToMap();
                        // poiOverlay.zoomToSpan();

                        mAMap.addMarker(new MarkerOptions()
                                .anchor(0.9f, 0.9f)
                                .icon(BitmapDescriptorFactory
                                        .fromBitmap(BitmapFactory.decodeResource(
                                                getResources(), R.mipmap.navi_map_gps_locked)))
                                .position(new LatLng(lp.getLatitude(), lp.getLongitude())));

//                        mAMap.addCircle(new CircleOptions()
//                                .center(new LatLng(lp.getLatitude(),
//                                        lp.getLongitude())).radius(5000)
//                                .strokeColor(Color.BLUE)
//                                .fillColor(Color.argb(50, 1, 1, 1))
//                                .strokeWidth(2));

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.toast(mActivity, "没有搜索到数据");
                    }
                }
            } else {
                ToastUtil.toast(mActivity, "没有搜索到数据");
            }
        } else {
            ToastUtil.toast(mActivity, "错误:" + rcode);
        }
    }


    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.toast(mActivity, infomation);

    }

    // 将之前被点击的marker置为原来的状态
    private void resetlastmarker() {
        int index = poiOverlay.getPoiIndex(mlastMarker);
        if (index < 10) {
            mlastMarker.setIcon(BitmapDescriptorFactory
                    .fromBitmap(BitmapFactory.decodeResource(
                            getResources(), R.mipmap.poi_marker_pressed)));
        } else {
            mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.poi_marker_pressed)));
        }
        mlastMarker = null;

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        lp.setLatitude(cameraPosition.target.latitude);
        lp.setLongitude(cameraPosition.target.longitude);
        mAMap.clear();
        mAMap.addMarker(new MarkerOptions().position(cameraPosition.target));
        doSearchQuery();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
    }


    /**
     * 自定义PoiOverlay
     *
     */

//    private class myPoiOverlay {
//        private AMap mamap;
//        private List<PoiItem> mPois;
//        private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();
//        public myPoiOverlay(AMap amap ,List<PoiItem> pois) {
//            mamap = amap;
//            mPois = pois;
//        }
//
//        /**
//         * 添加Marker到地图中。
//         * @since V2.1.0
//         */
//        public void addToMap() {
//            for (int i = 0; i < mPois.size(); i++) {
//                Marker marker = mamap.addMarker(getMarkerOptions(i));
//                PoiItem item = mPois.get(i);
//                marker.setObject(item);
//                mPoiMarks.add(marker);
//            }
//        }
//
//        /**
//         * 去掉PoiOverlay上所有的Marker。
//         *
//         * @since V2.1.0
//         */
//        public void removeFromMap() {
//            for (Marker mark : mPoiMarks) {
//                mark.remove();
//            }
//        }
//
//        /**
//         * 移动镜头到当前的视角。
//         * @since V2.1.0
//         */
//        public void zoomToSpan() {
//            if (mPois != null && mPois.size() > 0) {
//                if (mamap == null)
//                    return;
//                LatLngBounds bounds = getLatLngBounds();
//                mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//            }
//        }
//
//        private LatLngBounds getLatLngBounds() {
//            LatLngBounds.Builder b = LatLngBounds.builder();
//            for (int i = 0; i < mPois.size(); i++) {
//                b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
//                        mPois.get(i).getLatLonPoint().getLongitude()));
//            }
//            return b.build();
//        }
//
//        private MarkerOptions getMarkerOptions(int index) {
//            return new MarkerOptions()
//                    .position(
//                            new LatLng(mPois.get(index).getLatLonPoint()
//                                    .getLatitude(), mPois.get(index)
//                                    .getLatLonPoint().getLongitude()))
//                    .title(getTitle(index)).snippet(getSnippet(index))
//                    .icon(getBitmapDescriptor(index));
//        }
//
//        protected String getTitle(int index) {
//            return mPois.get(index).getTitle();
//        }
//
//        protected String getSnippet(int index) {
//            return mPois.get(index).getSnippet();
//        }
//
//        /**
//         * 从marker中得到poi在list的位置。
//         *
//         * @param marker 一个标记的对象。
//         * @return 返回该marker对应的poi在list的位置。
//         * @since V2.1.0
//         */
//        public int getPoiIndex(Marker marker) {
//            for (int i = 0; i < mPoiMarks.size(); i++) {
//                if (mPoiMarks.get(i).equals(marker)) {
//                    return i;
//                }
//            }
//            return -1;
//        }
//
//        /**
//         * 返回第index的poi的信息。
//         * @param index 第几个poi。
//         * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
//         * @since V2.1.0
//         */
//        public PoiItem getPoiItem(int index) {
//            if (index < 0 || index >= mPois.size()) {
//                return null;
//            }
//            return mPois.get(index);
//        }
//
//        protected BitmapDescriptor getBitmapDescriptor(int arg0) {
//            if (arg0 < 10) {
//                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
//                        BitmapFactory.decodeResource(getResources(), markers));
//                return icon;
//            }else {
//                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
//                        BitmapFactory.decodeResource(getResources(), R.mipmap.poi_marker_pressed));
//                return icon;
//            }
//        }
//    }
}
