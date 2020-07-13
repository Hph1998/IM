package com.example.im.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.im.R;
import com.example.im.model.Model;
import com.example.im.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;

public class SplashActivity extends Activity {

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){

            if(isFinishing()){
                return;
            }

            toMainOrLogin();

        }
    };

    private void toMainOrLogin(){

        Model.getInstance().getGlobalThreadPool().execute(() -> {
            //判断当前账号是否已经登陆过
            if(EMClient.getInstance().isLoggedInBefore()){

                UserInfo account = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());

                if(account == null){
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {

                    Model.getInstance().loginSuccess(account);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }else {//没登陆过
                //跳转到登陆页面
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            //结束当前页面
            finish();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.sendMessageDelayed(Message.obtain(),2000);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}
