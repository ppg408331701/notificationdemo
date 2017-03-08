package webapps.MOrangeCheck.com.Tool;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.MaterialDialog;
import com.flyco.dialog.widget.NormalListDialog;


import java.util.ArrayList;

import webapps.MOrangeCheck.com.R;

/**
 * Created by ppg on 2016/10/18.
 */

public abstract class DialogTools {

    private ArrayList<DialogMenuItem> mMenuItems = new ArrayList<>();

    Context mContext;
    MaterialDialog dialog;

    public DialogTools(Context mContext) {
        this.mContext = mContext;
    }

    public abstract void okTask();

    public void cancelTask() {

    }

    public void initMenuItems(ArrayList datas) {
        for (int i = 0; i < datas.size(); i++) {
            mMenuItems.add(new DialogMenuItem((String) datas.get(i), 0));
        }
    }

    /**
     * 两个按钮
     */
    public void MaterialDialogDefault(String content) {
        dialog = new MaterialDialog(mContext);
        dialog.content(content)//
                .btnText("取消", "确定")
                .btnTextColor(ContextCompat.getColor(mContext, R.color.primary_dark), ContextCompat.getColor(mContext, R.color.primary_text))
                .show();
        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {
                        cancelTask();
                        dialog.dismiss();
                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        okTask();
                        dialog.dismiss();
                    }
                });
    }

    /**
     * 三个按钮
     */
    public void MaterialDialogThreeBtns() {
        dialog = new MaterialDialog(mContext);
        dialog.isTitleShow(false)//
                .btnNum(3)
                .content("为保证咖啡豆的新鲜度和咖啡的香味，并配以特有的传统烘焙和手工冲。")//
                .btnText("确定", "取消", "知道了")//
                .show();

        dialog.setOnBtnClickL(
                new OnBtnClickL() {//left btn click listener
                    @Override
                    public void onBtnClick() {

                        dialog.dismiss();
                    }
                },
                new OnBtnClickL() {//right btn click listener
                    @Override
                    public void onBtnClick() {

                        dialog.dismiss();
                    }
                }
                ,
                new OnBtnClickL() {//middle btn click listener
                    @Override
                    public void onBtnClick() {

                        dialog.dismiss();
                    }
                }
        );
    }

    /**
     * 一个按钮
     */
    public void MaterialDialogOneBtn(String content, OnBtnClickL onBtnClickL) {
        dialog = new MaterialDialog(mContext);
        dialog.btnNum(1)
                .content(content)//
                .btnText("确定")//
                .btnTextColor(ContextCompat.getColor(mContext, R.color.primary_text))
                .show();
        dialog.setOnBtnClickL(onBtnClickL);
    }

    public void NormalListDialog(OnOperItemClickL operItemClickL) {
        if (mMenuItems == null || mMenuItems.size() <= 0) {
            return;
        }
        final NormalListDialog dialog = new NormalListDialog(mContext, mMenuItems);
        dialog.title("请选择")//
                .show();
        dialog.setOnOperItemClickL(operItemClickL);
    }


}
