package com.nghiatt.calculator.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.nghiatt.calculator.R;

/**
 * Created by FRAMGIA\tran.thanh.nghia on 08/09/2015.
 */
public class Button extends android.widget.Button {
    public Button(Context context) {
        this(context, null);
    }

    public Button(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Button(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.TextView);
            String font = a.getString(R.styleable.TextView_font);
            if (font != null) {
                if (!font.contains("."))
                    font += ".ttf"; // Add default font's extension.
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), font);
                if (typeface != null) {
                    setTypeface(typeface);
                }
                //Log.i("Viki TextView", "set font: " + font);
            }
            a.recycle();
        }
    }


}
