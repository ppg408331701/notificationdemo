package webapps.MOrangeCheck.com.Fragment.Company.Meber;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import ppg.com.yanlibrary.fragment.LoadingFragment;
import ppg.com.yanlibrary.utils.ToastUtil;
import webapps.MOrangeCheck.com.Bean.CompanyMeber;
import webapps.MOrangeCheck.com.R;
import webapps.MOrangeCheck.com.Adpter.ContactAdapter;
import webapps.MOrangeCheck.com.databinding.FragmentMeberBinding;

/**
 * 成员
 * Created by ppg777 on 2017/3/14.
 */

public class Meber extends LoadingFragment {

    FragmentMeberBinding binding;
    private ContactAdapter mAdapter;


    public Meber() {
        super(true);
    }

    @Override
    public View onLoadingCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_meber, container, false);
        binding = DataBindingUtil.bind(root);
        initcontact();
        return root;
    }

    @Override
    protected void onCreateViewRequestData() {
        super.onCreateViewRequestData();
    }

    private void initcontact() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        binding.indexableLayout.setLayoutManager(layoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.listdivider_gray));
//        binding.indexableLayout.getRecyclerView().addItemDecoration(new LeftPaddingDividerItemDecoration(mActivity,
//                LeftPaddingDividerItemDecoration.VERTICAL, 0));
        binding.indexableLayout.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        binding.indexableLayout.getRecyclerView().setBackgroundResource(R.color.white0);
        mAdapter = new ContactAdapter(mActivity);
        binding.indexableLayout.setAdapter(mAdapter);
        // set Material Design OverlayView
        binding.indexableLayout.setOverlayStyle_MaterialDesign(ContextCompat.getColor(mActivity, R.color.yellow2));
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        binding.indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        // set Listener
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CompanyMeber>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CompanyMeber entity) {
                if (originalPosition >= 0) {
                    ToastUtil.toast(mActivity, entity.getName() + ",当前位置:" + currentPosition + ",原始位置:" + originalPosition);
                } else {
                    ToastUtil.toast(mActivity, entity.getName() + ",当前位置:" + currentPosition + ",原始位置:" + originalPosition);

                }
            }
        });

        mAdapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.toast(mActivity, "选中:" + indexTitle + "  当前位置:" + currentPosition);
            }
        });


        mAdapter.setDatas(initDatas(), new IndexableAdapter.IndexCallback<CompanyMeber>() {
            @Override
            public void onFinished(List<EntityWrapper<CompanyMeber>> datas) {
//                layoutManager.scrollToPositionWithOffset(1, 0);

            }
        });
    }

    private List<CompanyMeber> initDatas() {
        List<CompanyMeber> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.contact_array));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.mobile_array));
        for (int i = 0; i < contactStrings.size(); i++) {
            CompanyMeber contactEntity = new CompanyMeber();
            contactEntity.setName(contactStrings.get(i));
            contactEntity.setPistion(mobileStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }


    /**
     * 自定义的Banner Header
     */
//    class BannerHeaderAdapter extends IndexableHeaderAdapter {
//        private static final int TYPE = 2;
//
//        public BannerHeaderAdapter(String index, String indexTitle, List datas) {
//            super(index, indexTitle, datas);
//        }
//
//        @Override
//        public int getItemViewType() {
//            return TYPE;
//        }
//
//        @Override
//        public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
//            View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_meber_head_search, parent, false);
//            VH holder = new VH(view);
//            LT.ee(holder.ed_meber_search.getHint().toString());
//            return holder;
//        }
//
//        @Override
//        public void onBindContentViewHolder(RecyclerView.ViewHolder holder, Object entity) {
//            // 数据源为null时, 该方法不用实现
//        }
//
//        private class VH extends RecyclerView.ViewHolder {
//            EditText ed_meber_search;
//
//            public VH(View itemView) {
//                super(itemView);
//                ed_meber_search = (EditText) itemView.findViewById(R.id.ed_meber_search);
//            }
//        }
//    }
}
