package ppg.com.yanlibrary.widget.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.List;

import ppg.com.yanlibrary.utils.SupportUtil_SDK21;

/**
 * Created by john on 2015/1/31.
 */
public class BaseMenuBarLayout extends LinearLayout {

    private Action mAction;
    private int itemRID = -1;
    private int itemCount = 0;
    private List<View> itemViews = new ArrayList<>();
    private boolean lazyInit = false;

    public BaseMenuBarLayout(Context context) {
        super(context);
    }

    public BaseMenuBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void lazyInitMenuViews() {
        super.onFinishInflate();
        setOrientation(HORIZONTAL);
//        if(itemRID != -1) {
            int size = itemCount;
            for(int i=0;i<size;++i) {
                LayoutParams llp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
                llp.weight = 1;
                View item = inflaterItemLayout(itemRID, i);
                itemViews.add(item);
                onInitItemData(item, i);
                SupportUtil_SDK21.SupporTouchFeedbackOn21(getContext(), item,
                        true, new InteriorOnClickListener(i));
//                item.setOnClickListener(new InteriorOnClickListener(i));
                addView(item, llp);
            }
//        }
        lazyInit = true;
    }

    public void setMenuItemLayoutID(int rid) {
        itemRID = rid;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        if(!lazyInit)
            lazyInitMenuViews();
    }

    public int getItemCount() {
        return itemCount;
    }

    protected void onInitItemData(View item, int index) {}

    protected View inflaterItemLayout(int itemRID, int pos) {
        return LayoutInflater.from(getContext()).inflate(itemRID, null);
    }

    public static interface onInitItemDataListener {
        public void onInitItemData(View v, int index);
    }

    public void setAction(Action mAction) {
        this.mAction = mAction;
    }

    public static interface Action {
        public boolean onMenuClick(View v, int index);
    }

    protected void holdAtIndex(List<View> itemViews, int index) {}

    class InteriorOnClickListener implements OnClickListener {

        private int index;

        public InteriorOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            boolean onClick=false;
            if(mAction != null)
            {
                onClick=  mAction.onMenuClick(v, index);
            }
            if(!onClick)
            {
                holdAtIndex(itemViews, index);
            }

//            ToastUtil.toast2_bottom(v.getContext(), "" + index);
        }
    }
}
