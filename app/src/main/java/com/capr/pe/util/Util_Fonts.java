package com.capr.pe.util;

import android.content.Context;
import android.graphics.Typeface;

public class Util_Fonts {

    public static Typeface setPNALight(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_light.otf");
    }

    public static Typeface setPNASemiBold(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_semi_bold.otf");
    }

    public static Typeface setPNACursivaLight(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(),"fonts/pna_cursiva_light.otf");
    }
}
