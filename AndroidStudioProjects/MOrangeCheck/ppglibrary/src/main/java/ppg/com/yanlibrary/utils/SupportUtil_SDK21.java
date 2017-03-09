package ppg.com.yanlibrary.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import ppg.com.yanlibrary.R;


public class SupportUtil_SDK21 {
	
	public static void touchFeedback(Context context, View view) {
		touchFeedback(context, view, R.drawable.listview_item_style_theme, false);
	}
	
	public static void touchFeedbackBorderless(Context context, View view) {
		touchFeedback(context, view, R.drawable.listview_item_style_theme, true);
	}
	
	/**
	 * 触摸反馈
	 */
	public static void touchFeedback(Context context, View view, int defaultRID, boolean isBorderless) {
		if(Build.VERSION.SDK_INT >= 21) {
			int[] attrs = new int[1];
			if(isBorderless)
				attrs[0] = android.R.attr.selectableItemBackgroundBorderless;
			else
				attrs[0] = android.R.attr.selectableItemBackground;
			TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs);
			Drawable drawable = typedArray.getDrawable(0);
			view.setBackgroundDrawable(drawable);
			typedArray.recycle();
		}else {
			view.setBackgroundResource(defaultRID);
		}
	}
	
	public static void touchFeedback(Context context, View[] views) {
		touchFeedback(context, views, R.drawable.listview_item_style_theme, false);
	}
	
	public static void touchFeedbackBorderless(Context context, View[] views) {
		touchFeedback(context, views, R.drawable.listview_item_style_theme, true);
	}
	
	public static void touchFeedback(Context context, View[] views, int defaultRID, boolean isBorderless) {
		if(Build.VERSION.SDK_INT >= 21) {
			int[] attrs = new int[1];
			for(int i=0;i<views.length;++i) {
				if(isBorderless)
					attrs[0] = android.R.attr.selectableItemBackgroundBorderless;
				else
					attrs[0] = android.R.attr.selectableItemBackground;
				TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs);
				Drawable drawable = typedArray.getDrawable(0);
				views[i].setBackgroundDrawable(drawable);
				typedArray.recycle();
			}
		}else {
			for(int i=0;i<views.length;++i) {
				views[i].setBackgroundResource(defaultRID);
			}
		}

	}

    public static void SupporTouchFeedbackOn21(Context context, View sdk21_view, boolean sdk21_isBorderless,
                                           OnClickListener clickListener) {
        if(Build.VERSION.SDK_INT >= 21) {
            if(sdk21_view.getTag() == null) {
                int[] attrs = new int[1];
                if(sdk21_isBorderless)
                    attrs[0] = android.R.attr.selectableItemBackgroundBorderless;
                else
                    attrs[0] = android.R.attr.selectableItemBackground;
                TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs);
                Drawable drawable = typedArray.getDrawable(0);
                sdk21_view.setOnClickListener(clickListener);
                sdk21_view.setBackgroundDrawable(drawable);
                typedArray.recycle();
                sdk21_view.setTag("materialView");
            }else {
                sdk21_view.setOnClickListener(clickListener);
            }
        }else {
            sdk21_view.setOnClickListener(clickListener);
        }
    }
	

	
	public static void SupporTouchFeedback(Context context, View sdk21_view, boolean sdk21_isBorderless, 
			View sdk14_root, int sdk14_colorID, OnClickListener clickListener) {
		if(Build.VERSION.SDK_INT >= 21) {
			if(sdk21_view.getTag() == null) {
				int[] attrs = new int[1];
				if(sdk21_isBorderless)
					attrs[0] = android.R.attr.selectableItemBackgroundBorderless;
				else
					attrs[0] = android.R.attr.selectableItemBackground;
				TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs);
				Drawable drawable = typedArray.getDrawable(0);
				sdk21_view.setOnClickListener(clickListener);
				sdk21_view.setBackgroundDrawable(drawable);
				typedArray.recycle();
				sdk21_view.setTag("materialView");
			}else {
				sdk21_view.setOnClickListener(clickListener);
			}
		}else {
			sdk14_root.setBackgroundResource(R.drawable.listview_item_style_theme);
			sdk14_root.setOnClickListener(clickListener);
		}
	}

    public static void SupporTouchFeedback(Context context, View view, boolean sdk21_isBorderless,
                                           int RID, OnClickListener clickListener) {
        if(Build.VERSION.SDK_INT >= 21) {
            int[] attrs = new int[1];
            if(sdk21_isBorderless)
                attrs[0] = android.R.attr.selectableItemBackgroundBorderless;
            else
                attrs[0] = android.R.attr.selectableItemBackground;
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs);
            Drawable drawable = typedArray.getDrawable(0);
            view.setOnClickListener(clickListener);
            view.setBackgroundDrawable(drawable);
            typedArray.recycle();
        }else {
            view.setBackgroundResource(RID);
            view.setOnClickListener(clickListener);
        }
    }
	

}
