package Adapter.OmnipotentAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by yy on 2015/5/10.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mcontext;
    private int mCount;

    private ViewHolder(Context context, ViewGroup parent, int layoutid, int position, int count) {
        this.mPosition = position;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutid, parent, false);
        this.mViews = new SparseArray<View>();
        mcontext = context;
        mCount = count;
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutid, int position, int count) {

        if (convertView == null) {
            return new ViewHolder(context, parent, layoutid, position, count);

        } else {

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }


    }

    /*
    * 通过viewid得到控件
    *
    * */
    public <T extends View> T getView(int viewid) {
        View view = mViews.get(viewid);
        if (view == null) {
            view = mConvertView.findViewById(viewid);
            mViews.put(viewid, view);
        }
        return (T) view;
    }


    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public View getConvertView() {

        return mConvertView;
    }

    public int getPosition() {

        return mPosition;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;

    }

    public ViewHolder setImageResId(int viewId, int resid) {

        ImageView view = getView(viewId);
        view.setImageResource(resid);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageURI(int viewId, String url) {
        ImageView view = getView(viewId);
//        ImageLoader.createImageLoader(mcontext).displayImage(url, view, false, true, new ImageLoader.onloadListener() {
//            @Override
//            public boolean onloaded(ImageView imageView, Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//                return true;
//            }
//        });
//        Picasso.with(mcontext)
//                .load(url)
//                .fit()
//                .into(view);
        return this;
    }
}
