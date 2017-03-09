package Adapter;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yy on 2015/4/12.
 */
public abstract class AdapterBase<T> extends BaseAdapter {
    private List<T> list;
    private T[] array;

    public AdapterBase(List<T> list) {

        this.list = list;
    }

    public AdapterBase(T[] array) {

        this.array = array;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : array.length;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getList() {

        return list;
    }

    public T[] getArray() {

        return array;
    }
}
