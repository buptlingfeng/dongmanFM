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
public class IconViewElusive extends AbstractIconView {

	private static Typeface mFont;
	
	public IconViewElusive(Context context, AttributeSet attrs) {
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
		return R.id.el_icon_zoom_out;
	}

	@Override
	protected int getStringArrayResId() {
		return R.array.elusive_icons;
	}

	@Override
	protected int[] getStyleableResIds() {
		return R.styleable.IconViewElusive;
	}

	@Override
	protected int getIconAttributeResId() {
		return R.styleable.IconViewElusive_elusive_icon;
	}

	@Override
	protected String getFontResName() {
		return "Elusive-Icons.ttf";
	}

}
