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
public class IconViewFontelico extends AbstractIconView {

	private static Typeface mFont;
	
	public IconViewFontelico(Context context, AttributeSet attrs) {
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
		return R.id.icon_emo_happy;
	}

	@Override
	protected int getStringArrayResId() {
		return R.array.fontelico_icons;
	}

	@Override
	protected int[] getStyleableResIds() {
		return R.styleable.IconViewFontelico;
	}

	@Override
	protected int getIconAttributeResId() {
		return R.styleable.IconViewFontelico_fontelico_icon;
	}

	@Override
	protected String getFontResName() {
		return "fontelico.ttf";
	}

}
