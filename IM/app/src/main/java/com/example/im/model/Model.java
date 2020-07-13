package com.example.im.model;

import android.content.Context;

import com.example.im.model.bean.UserInfo;
import com.example.im.model.dao.UserAccountDao;
import com.example.im.model.db.DBManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//数据模型层全局类
public class Model {

    private Context mContext;
    private ExecutorService executors = Executors.newCachedThreadPool();
    private UserAccountDao userAccountDao;
    private DBManager dbManager;

    private static Model model = new Model();

    private Model(){

    }

    public static Model getInstance(){

        return model;
    }

    public void init(Context context){
        mContext = context;

        userAccountDao = new UserAccountDao(mContext);

        EventListener eventListener = new EventListener(mContext);

    }

    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

    public void loginSuccess(UserInfo account){

        if(account == null){
            return;
        }
        if(dbManager!=null){
            dbManager.close();
        }
        dbManager = new DBManager(mContext, account.getName());
    }

    public DBManager getDbManager(){
        return dbManager;
    }

    public UserAccountDao getUserAccountDao(){
        return userAccountDao;
    }

}
