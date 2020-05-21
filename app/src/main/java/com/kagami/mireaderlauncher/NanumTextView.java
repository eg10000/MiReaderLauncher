package com.kagami.mireaderlauncher;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class NanumTextView extends androidx.appcompat.widget.AppCompatTextView {

    public NanumTextView(Context context) {
        super(context);
        init();
    }

    public NanumTextView(Context context, int style) {
        super(context);
        init();
    }

    public NanumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NanumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        setTypeface(FontHolder.getInstance(getContext()).getNanumTypeFace());
    }
}
