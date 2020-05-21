package com.kagami.mireaderlauncher;

import android.content.Context;
import android.graphics.Typeface;

public class FontHolder {

    private volatile static FontHolder instance;

	private Typeface nanum = null;

	public FontHolder(Context context) {
		if (nanum == null)
			nanum = Typeface.createFromAsset(context.getAssets(), "NanumBarunGothic.ttf");
	}

	public static FontHolder getInstance(Context context) {
		if (instance == null) {
			synchronized (FontHolder.class) {
				if (instance == null) {
					instance = new FontHolder(context);
				}
			}
		}
		return instance;
	}

    public Typeface getNanumTypeFace() {
        return nanum;
    }
}
