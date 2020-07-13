package com.example.im;

import android.app.Application;
import android.content.Context;

import com.example.im.model.Model;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;

public class IMApplication extends Application {

    private static Context mContext;
    @Override
    public void onCreate(){
        super.onCreate();

        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);
        EaseUI.getInstance().init(this,options);

        Model.getInstance().init(this);
        mContext = this;
    }

    public static Context getGlobalApplication(){
        return mContext;
    }
}
