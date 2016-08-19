package com.ppjun.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //新建数据库时初始化一个devOpenHelper
       // DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "user.db", null);
       // DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());

        //升级数据库时初始化一个devOpenHelper
       DBHelper dbHelper = new DBHelper(getApplicationContext());
       DaoMaster daoMaster= new DaoMaster(dbHelper.getWritableDb());

       //初始化一个daoSession
       DaoSession daoSession = daoMaster.newSession();
        //获取user表对象
        UserDao userDao = daoSession.getUserDao();

        //add
       /* User user1 = new User(null, "ag1", "123456");
        User user2 = new User(null,"ag2", "123456");
        User user3 = new User(null,"ag3", "123456");*/
       User user4 = new User(null,4, "ag4", "123456");
       /* userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);*/
       // userDao.insert(user4);
        //query
        List<User> userList = userDao.queryBuilder().build().list();
        for (User user : userList)
            Log.i(TAG, user.getUserName()+","+user.getAge());

        //update
        User user2 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(0)).build().unique();
        user2.setUserName("ck");
        userDao.update(user2);

        //delete
        List<User> userList2 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(0)).build().list();
        for (User user1 : userList2)
            userDao.delete(user1);
    }


}
