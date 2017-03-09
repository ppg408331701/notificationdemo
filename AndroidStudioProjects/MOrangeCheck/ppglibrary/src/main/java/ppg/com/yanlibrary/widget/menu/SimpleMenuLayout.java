package ppg.com.yanlibrary.widget.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ppg.com.yanlibrary.R;

/**
 * 简单的底部导航条
 * Created by jie.yang on 2015/2/2.
 */
public class SimpleMenuLayout extends BaseMenuBarLayout {
    private List<MenuDataItem> itemDataSets;
    public boolean isShow = false;

    public SimpleMenuLayout(Context context) {
        super(context);
        setMenuItemLayoutID(R.layout.simple_menu_item);
    }

    public SimpleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMenuItemLayoutID(R.layout.simple_menu_item);
    }


    public void setItemDataSets(List<MenuDataItem> itemDataSets) {
        this.itemDataSets = itemDataSets;
        setItemCount(itemDataSets.size());
    }

    public void setRedPoint(boolean flag) {
        if (flag) {
            isShow = true;
        }
    }

    public List<MenuDataItem> getItemDataSets() {
        return itemDataSets;
    }

//    private boolean isShowRedPoint() {
//
//        return isShow;
//    }


    /**
     * 这是初始化的
     *
     * @param item
     * @param index
     */
    @Override
    protected void onInitItemData(View item, int index) {
        super.onInitItemData(item, index);
        if (itemDataSets != null && item instanceof FrameLayout) {
            MenuDataItem dataItem = itemDataSets.get(index);
            //dataItem.isShow=isShowRedPoint();
            FrameLayout root = (FrameLayout) item;
            // 设置标题
            TextView titleTxt = (TextView) root.findViewById(R.id.widget_layout_menu_text);
//            titleTxt.setBackgroundResource(R.drawable.rounded_notice);
            //隐藏图标
            titleTxt.setText(dataItem.titleName);
            // 设置图标2
            ImageView logo = (ImageView) root.findViewById(R.id.widget_layout_menu_image);
//            logo.setVisibility(GONE);
            // 设置消息提示的小红点
            ImageView redpoint = (ImageView) root.findViewById(R.id.widget_layout_menu_red_point);

            if (isShow == true && index == 2) {
                redpoint.setVisibility(View.VISIBLE);
            }


            if (dataItem.isDefaultHold) {
                logo.setImageResource(dataItem.holdImagerRID);
                titleTxt.setTextColor(dataItem.holdTitleColor);
                // redpoint.setVisibility();
            } else {
                logo.setImageResource(dataItem.unHoldImagerRID);
                titleTxt.setTextColor(dataItem.unHoldTitleColor);
            }
            dataItem.logo = logo;
            dataItem.titleTxt = titleTxt;
            dataItem.redpoint = redpoint;
        }
    }


    @Override
    protected void holdAtIndex(List<View> itemViews, int index) {
        super.holdAtIndex(itemViews, index);
        if (itemDataSets != null) {
            int size = getItemCount();
            for (int i = 0; i < size; ++i) {
                MenuDataItem dataItem = itemDataSets.get(i);
                dataItem.logo.setImageResource(dataItem.unHoldImagerRID);
                dataItem.titleTxt.setTextColor(dataItem.unHoldTitleColor);
                if (index == 2) {
                    dataItem.redpoint.setVisibility(View.INVISIBLE);
                }
            }
            MenuDataItem indexItem = itemDataSets.get(index);
            indexItem.logo.setImageResource(indexItem.holdImagerRID);
            indexItem.titleTxt.setTextColor(indexItem.holdTitleColor);
        }
    }

    public static class MenuDataItem {
        public String titleName;
        public int holdTitleColor = 0xFF444444;
        public int unHoldTitleColor = 0xFF838383;
        public int holdImagerRID;
        public int unHoldImagerRID;
        public boolean isDefaultHold = false;
        private ImageView logo;
        private TextView titleTxt;
        private ImageView redpoint;

        public TextView getTitleTxt() {
            return titleTxt;
        }

        public ImageView getLogos() {
            return logo;
        }

    }
}
