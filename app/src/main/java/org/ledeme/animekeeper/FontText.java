package org.ledeme.animekeeper;

/**
 * Created by alexl on 11/05/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * TextView subclass which allows the user to define a truetype font file to use as the view's typeface.
 */
@SuppressLint("AppCompatCustomView")
public class FontText extends TextView {
    public FontText(Context context) {
        this(context, null);
    }

    public FontText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontText);

        if (ta != null) {
            String fontAsset = ta.getString(R.styleable.FontText_typefaceAsset);

            if (!TextUtils.isEmpty(fontAsset)) {
                Typeface tf = FontManager.getInstance().getFont(fontAsset);
                int style = Typeface.NORMAL;
                float size = getTextSize();

                if (getTypeface() != null)
                    style = getTypeface().getStyle();

                if (tf != null)
                    setTypeface(tf, style);
                else
                    Log.d("FontText", String.format("Could not create a font from asset: %s", fontAsset));
            }
        }
    }
}