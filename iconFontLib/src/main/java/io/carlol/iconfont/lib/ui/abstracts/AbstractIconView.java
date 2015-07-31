package io.carlol.iconfont.lib.ui.abstracts;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckedTextView;


/**
 * User: Mirko Haeberlin
 * Date: 25.04.13 | Time: 11:44
 * 
 * @link https://bitbucket.org/informatic0re/awesome-font-iconview
 */
public abstract class AbstractIconView extends CheckedTextView {


    /**
     * Returns the Typeface from the given context with the given name typeface
     * @param context Context to get the assets from
     * @param typeface name of the ttf file
     * @return Typeface from the given context with the given name
     */
    public abstract Typeface getTypeface(Context context);

    public AbstractIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(this.getTypeface(context));

        // TODO: maybe too heavy load all array each time
        String[] icons = getResources().getStringArray(this.getStringArrayResId());
        setText(icons[getIconIndex(context, attrs)]);
    }

    public void setIcon(int icon) {
        // TODO: maybe too heavy load all array each time
        String[] icons = getResources().getStringArray(this.getStringArrayResId());
        setText(icons[icon - this.getFirstIconResId()]);
    }

    private int getIconIndex(Context context, AttributeSet attrs){
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, this.getStyleableResIds(), 0, 0);
        try{
            return a.getInt(this.getIconAttributeResId(), 0);
        } finally {
            a.recycle();
        }
    }
    
    protected abstract int getFirstIconResId();
    
    protected abstract int getStringArrayResId();
    
    protected abstract int[] getStyleableResIds();
    
    protected abstract int getIconAttributeResId();
    
    protected abstract String getFontResName();
    
}
