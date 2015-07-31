package io.carlol.iconfont.lib.ui;

import io.carlol.iconfont.R;
import io.carlol.iconfont.lib.ui.abstracts.AbstractIconView;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * 
 * @author c.luchessa
 *
 */
public class IconViewStroke extends AbstractIconView {

	private static Typeface mFont;
	
	public IconViewStroke(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public Typeface getTypeface(Context context) {
        if (mFont == null) {
            mFont = Typeface.createFromAsset(context.getAssets(), this.getFontResName());
        }
        return mFont;
    }

	@Override
	protected int getFirstIconResId() {
		return R.id.pe_7s_cloud_upload;
	}

	@Override
	protected int getStringArrayResId() {
		return R.array.stroke_icons;
	}

	@Override
	protected int[] getStyleableResIds() {
		return R.styleable.IconViewStroke;
	}

	@Override
	protected int getIconAttributeResId() {
		return R.styleable.IconViewStroke_stroke_icon;
	}

	@Override
	protected String getFontResName() {
		return "Pe-icon-7-stroke.ttf";
	}

}
