package com.systemmodule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class ScreenModules {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public HashMap<String, Object> screen(Context context, DisplayMetrics dm) {
        float safeTop = safeTop(context);
        float safeBottom = safeBottom(context);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("safeTop", safeTop / dm.density);
        hashMap.put("safeBottom", safeBottom / dm.density);
        hashMap.put("width", dm.widthPixels * 1.0f / dm.density);
        hashMap.put("height", dm.heightPixels * 1.0f / dm.density);
        return hashMap;
    }

    private float safeTop(Context context) {
        float defaultHeight = 56;
        try {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0)
                defaultHeight = resources.getDimensionPixelSize(resourceId);
        } catch (Exception ignored) {

        }
        return defaultHeight;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private float safeBottom(Context context) {
        int val = Settings.Global.getInt(context.getContentResolver(), deviceInfo(), 0);
        int result = 0;
        if (val != 0)
            return result;
        if (isNavigationBarShown(context))
            return result;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height","dimen", "android");
        if (resourceId > 0)
            result = context.getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    private String deviceInfo() {
        String brand = Build.BRAND.toUpperCase();
        if (brand.equals("HUAWEI"))
            return "navigationbar_is_min";
        if (brand.equals("XIAOMI"))
            return "force_fsg_nav_bar";
        if (brand.equals("VIVO") || brand.equals("OPPO"))
            return "navigation_gesture_on";
        return "navigationbar_is_min";
    }

    private boolean isNavigationBarShown(Context context) {
        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return !(id > 0 && context.getResources().getBoolean(id));
    }
}
