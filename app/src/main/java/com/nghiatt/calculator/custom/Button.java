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
                    R.styleable.Button);
            String font = a.getString(R.styleable.Button_font);
            if (font != null) {
                if (!font.contains("."))
                    font += ".ttf"; // Add default font's extension.
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), font);
                    if (typeface != null) {
                        setTypeface(typeface);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            a.recycle();
        }
    }


}
