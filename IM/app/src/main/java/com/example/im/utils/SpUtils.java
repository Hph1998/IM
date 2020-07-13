package com.example.im.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.im.IMApplication;

public class SpUtils {

    public static final String IS_NEW_INVITE = "is_new_invite";
    private static SpUtils instace = new SpUtils();
    private static SharedPreferences mSp;


    public SpUtils() {

    }

    public static SpUtils getInstance(){

        if(mSp == null){
            mSp = IMApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }

        return instace;

    }

    // 保存

    public void save(String key, Object value){

        if(value instanceof String) {

            mSp.edit().putString(key, (String) value).commit();

        }else if(value instanceof Boolean) {

            mSp.edit().putBoolean(key, (Boolean) value).commit();

        }else if(value instanceof  Integer) {

            mSp.edit().putInt(key, (Integer) value).commit();

        }

    }

    // 读取

    // 读取String类型数据

    public String getString(String key, String defValue){

        return mSp.getString(key, defValue);

    }

    // 读取boolean类型数据

    public boolean getBoolean(String key, boolean defValue){

        return mSp.getBoolean(key, defValue);

    }

    // 读取int类型数据

    public int getInt(String key, int defValue){

        return mSp.getInt(key, defValue);

    }

}
