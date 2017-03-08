package webapps.MOrangeCheck.com.Factory;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class RecyclerViewFactory {


    public static RecyclerView createNoRefreshVerticalXRecyclerView(Context context){
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

    public static RecyclerView createNoItemDecorationVerticalXRecyclerView(Context context){
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }


    public static RecyclerView createNoRefreshHorizontalXRecyclerView(Context context){
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        return recyclerView;
    }

}
