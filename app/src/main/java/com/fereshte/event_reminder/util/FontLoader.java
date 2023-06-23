package com.fereshte.event_reminder.util;

import android.graphics.Typeface;

public class FontLoader {

    public FontLoader() {}

    private static Typeface iranSansLight = null;
    private static Typeface iranSansRegular = null;
    private static Typeface iranSansMedium = null;

    public static Typeface getIranSansMediumInstance(){
        if(iranSansMedium == null){
            //iranSansMedium = Typeface.createFromAsset(context.getAssets(), "iransans_fa_medium.ttf")
        }
        return iranSansMedium;
    }

}
