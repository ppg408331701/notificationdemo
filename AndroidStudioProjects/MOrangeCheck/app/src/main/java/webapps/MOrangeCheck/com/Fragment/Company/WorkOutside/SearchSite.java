package webapps.MOrangeCheck.com.Fragment.Company.WorkOutside;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import webapps.MOrangeCheck.com.Application.AppApplication;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Tool.LeftPaddingDividerItemDecoration;
import webapps.MOrangeCheck.com.databinding.FragmentSearchSiteBinding;

/**
 * 目前InputtipsListener这个回调是返回全南宁市的地名,类似搜索不全,
 * 而OnPoiSearchListener这个是搜索附近5000米的地名,需要的按需删除其中一个,或者两个都留
 * 追忆
 * Created by ppg777 on 2017/3/22.
 */

public class SearchSite extends LoadingFragment implements TextWatcher, Inputtips.InputtipsListener, PoiSearch.OnPoiSearchListener {

    FragmentSearchSiteBinding binding;
    private BaseQuickAdapter adapter;
    private int currentPage;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private Double mLatitude;
    private Double mLongitude;
    private LatLonPoint lp;
    private PoiResult poiResult;
    private ArrayList<PoiItem> poiItems;

    public SearchSite() {
        super(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity.SettingTopHide(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_site, container, false);
        binding = DataBindingUtil.bind(root);
        binding.edSearch.addTextChangedListener(this);
        binding.searchlist.setLayoutManager(new LinearLayoutManager(mActivity));
        binding.searchlist.addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
                LeftPaddingDividerItemDecoration.VERTICAL, 0));
        binding.searchlist.setItemAnimator(new DefaultItemAnimator());
        binding.flBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.edSearch.getText().toString())) {
                    doSearchQuery(binding.edSearch.getText().toString());
                } else {
                    ToastUtil.toast(mActivity, "搜索内容不能为空");
                }
            }
        });
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
        //初始化当前定位的数据
        mLatitude = Double.valueOf(AppApplication.getSpUtils().getString("Latitude"));
        mLongitude = Double.valueOf(AppApplication.getSpUtils().getString("Longitude"));
        lp = new LatLonPoint(mLatitude, mLongitude);
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
    protected void doSearchQuery(String key) {
        if (TextUtils.isEmpty(AppApplication.getSpUtils().getString("LocationCity"))) {
            ToastUtil.toast(mActivity, "定位失败");
            return;
        }
        currentPage = 0;
        query = new PoiSearch.Query(key, "", AppApplication.getSpUtils().getString("LocationCity"));// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(mActivity, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 50000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(AppApplication.getSpUtils().getString("LocationCity"))) {
            ToastUtil.toast(mActivity, "定位失败");
            return;
        }
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, AppApplication.getSpUtils().getString("LocationCity"));
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(mActivity, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 输入提示结果的回调
     *
     * @param tipList
     * @param rCode
     */
    @Override
    public void onGetInputtips(final List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;
            for (int i = 0; i < tipList.size(); i++) {
                map = new HashMap<String, String>();
                map.put("name", tipList.get(i).getName());
                map.put("address", tipList.get(i).getDistrict());
                listString.add(map);
            }
            initTipList(listString);
        } else {
            if (adapter != null) {
                adapter.setEmptyView(getView());
            }

        }
    }

    private void initTipList(List<HashMap<String, String>> listString) {
        if (adapter == null) {
            adapter = new BaseQuickAdapter<HashMap<String, String>, BaseViewHolder>(R.layout.item_poi, listString) {
                @Override
                protected void convert(BaseViewHolder helper, HashMap<String, String> item) {
                    helper.setText(R.id.tv_poi_title, item.get("name"))
                            .setText(R.id.tv_poi_content, item.get("address"));
                }

            };
            binding.searchlist.setAdapter(adapter);
        } else {
            adapter.setNewData(listString);
        }
    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
                        HashMap<String, String> map;
                        for (int i = 0; i < poiItems.size(); i++) {
                            map = new HashMap<String, String>();
                            map.put("name", poiItems.get(i).getTitle());
                            map.put("address", poiItems.get(i).getSnippet());
                            listString.add(map);
                        }
                        initTipList(listString);

                    } else {
                        ToastUtil.toast(mActivity, "没有搜索到数据");
                        if (adapter != null) {
                            adapter.setEmptyView(R.layout.empty_page, binding.searchlist);
                        }
                    }
                }
            } else {
                ToastUtil.toast(mActivity, "没有搜索到数据");
            }
        } else {
            ToastUtil.toast(mActivity, "错误:" + rcode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
