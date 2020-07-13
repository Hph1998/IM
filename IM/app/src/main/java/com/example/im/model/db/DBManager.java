package com.example.im.model.db;

// 数据库的管理类

import android.content.Context;

import com.example.im.model.dao.ContactTableDao;
import com.example.im.model.dao.InviteTableDao;

public class DBManager {

    private final DBHelper dbHelper;

    private final ContactTableDao contactTableDao;

    private final InviteTableDao inviteTableDao;

    public DBManager(Context context, String name) {

        dbHelper = new DBHelper(context, name);

        // 创建联系人操作类

        contactTableDao = new ContactTableDao(dbHelper);

        // 创建邀请信息操作类

        inviteTableDao = new InviteTableDao(dbHelper);

    }

    public InviteTableDao getInviteTableDao(){

        return inviteTableDao;

    }

    public ContactTableDao getContactTableDao(){

        return contactTableDao;

    }

    public void close() {

        dbHelper.close();

    }

}
