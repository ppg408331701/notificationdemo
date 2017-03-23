package webapps.MOrangeCheck.com.Adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.yokeyword.indexablerv.IndexableAdapter;
import webapps.MOrangeCheck.com.Bean.CompanyMeber;
import webapps.MOrangeCheck.com.R;

/**
 * Created by YoKey on 16/10/8.
 */
public class ContactAdapter extends IndexableAdapter<CompanyMeber> {
    private LayoutInflater mInflater;

    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_meber_title, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_meber, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CompanyMeber entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tv_meber_name.setText(entity.getName());
        vh.tv_meber_position.setText(entity.getPistion());

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv_meber_name, tv_meber_position;

        public ContentVH(View itemView) {
            super(itemView);
            tv_meber_name = (TextView) itemView.findViewById(R.id.tv_meber_name);
            tv_meber_position = (TextView) itemView.findViewById(R.id.tv_meber_position);
        }
    }
}
