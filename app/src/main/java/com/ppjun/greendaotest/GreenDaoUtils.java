package com.ppjun.greendaotest;

import android.content.Context;

import com.ppjun.greendaotest.db.DaoMaster;
import com.ppjun.greendaotest.db.DaoSession;

/**
 * @Package :com.ppjun.greendaotest
 * @Description :
 * @Author :Rc3
 * @Created at :2016/8/19 10:33.
 */
public class GreenDaoUtils {
    public static final String DB_NAME = "ppjun.db";

    public static GreenDaoUtils mInstance = new GreenDaoUtils();

    public static GreenDaoUtils getInstance() {
        return mInstance;
    }

    //第一次发布app不需要升级数据库正常使用DevOpenHelper获取daosession
    public DaoSession getSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        return daoMaster.newSession();
    }

    //再次app需要升级数据库时 获取daosession
    public DaoSession getUpdateSession(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        DaoMaster daoMaster = new DaoMaster(dbHelper.getWritableDb());
        return daoMaster.newSession();
    }
}
