package Adapter.OmnipotentAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yy on 2015/5/10.
 */
public abstract class LinBaseadapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int mLayoutItem;

    //descendantFocusability 用于解决item中的控件抢获事件
    //bean的用处在于 如果是单选和多选等时候可以使用来保存选中状态
    public LinBaseadapter(Context context, List<T> datas, int layoutItem) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
        mLayoutItem = layoutItem;
    }

    public List<T> getmDatas() {

        return mDatas;
    }

    public void setmDatas(List<T> Datas) {
        mDatas.clear();
        mDatas = Datas;
        notifyDataSetChanged();
    }
    public void setMoreDatas(List<T> Datas) {

        for (int i = 0; i < Datas.size(); i++) {
            mDatas.add(Datas.get(i));
        }
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, mLayoutItem, position, getCount());
        convert(holder, getItem(position));
        return holder.getConvertView();
    }


    public abstract void convert(ViewHolder holder, T t);

}
