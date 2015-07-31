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
public class IconViewTypicon extends AbstractIconView {

	private static Typeface mFont;
	
	public IconViewTypicon(Context context, AttributeSet attrs) {
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
		return R.id.typcn_adjust_brightness;
	}

	@Override
	protected int getStringArrayResId() {
		return R.array.typeicon_icons;
	}

	@Override
	protected int[] getStyleableResIds() {
		return R.styleable.IconViewTypicon;
	}

	@Override
	protected int getIconAttributeResId() {
		return R.styleable.IconViewTypicon_typicon_icon;
	}

	@Override
	protected String getFontResName() {
		return "typicons.ttf";
	}

}
