package com.kagami.mireaderlauncher;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class NanumRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {
    public NanumRadioButton(Context context) {
        super(context);
        init();
    }

    public NanumRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NanumRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public NanumRadioButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    protected void init() {
        setTypeface(FontHolder.getInstance(getContext()).getNanumTypeFace());
    }
}
