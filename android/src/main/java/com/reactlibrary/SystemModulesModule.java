package com.reactlibrary;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SystemModulesModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext ctx;
    private SharedPreferences share;
    private final String kLanguageSetKey = "kLanguage";

    public SystemModulesModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.ctx = reactContext;
        this.share = reactContext.getSharedPreferences("shareManager", Context.MODE_PRIVATE);
    }

    @Override
    public String getName() {
        return "SystemModules";
    }

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Map<String, Object> getConstants() {
        Activity activity = getCurrentActivity();
        HashMap<String, Object> hashMap = null;
        if (null == activity) {
            DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
            hashMap = new ScreenModules().screen(ctx, dm);
        } else {
            WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getRealMetrics(dm);
            hashMap = new ScreenModules().screen(ctx, dm);
        }
        String language = share.getString(kLanguageSetKey, null);
        if (null == language)
            language = Locale.getDefault().getLanguage();
        hashMap.put(kLanguageSetKey, language);
        hashMap.put("os", "android");
        return hashMap;
    }

    /**
     * 保存kv
     * @param key key
     * @param value value
     */
    @ReactMethod
    public void setValueWithKey(String key, String value) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 移除指定的key
     * @param keys
     */
    @ReactMethod
    public void removeWithKey(ReadableArray keys) {
        if (keys.size() <= 0)
            return;
        SharedPreferences.Editor editor = share.edit();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.getString(i);
            editor.remove(key);
        }
        editor.apply();
    }

    /**
     * 根据key获取value
     * @param keys keys
     * @param callback callback
     */
    @ReactMethod
    public void getValueWithKey(ReadableArray keys, Callback callback) {
        WritableMap result = Arguments.createMap();
        if (keys.size() > 0) {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.getString(i);
                if (null != key) {
                    String value = share.getString(key, "");
                    result.putString(key, value);
                }
            }
        }
        callback.invoke(result);
    }

    /**
     * 清空
     */
    @ReactMethod
    public void clear() {
        share.edit().clear().apply();
    }

    /**
     * 设置语言
     * @param language
     */
    @ReactMethod
    public void setLanguage(String language) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString(kLanguageSetKey, language);
        editor.apply();
    }
}
