package com.ppjun.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ppjun.greendaotest.db.DaoSession;
import com.ppjun.greendaotest.db.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaoSession daoSession = GreenDaoUtils.getInstance().getSession(getApplicationContext());
        //获取user表对象
        UserDao userDao = daoSession.getUserDao();

        //add
        User user1 = new User(null, "ag1", "123456");
        User user2 = new User(null, "ag2", "123456");
        User user3 = new User(null, "ag3", "123456");
        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);


        //update
        User user4 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(3)).build().unique();
        user4.setUserName("ck");
        userDao.update(user4);

        //delete
        List<User> userList2 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(2)).build().list();
        for (User user5 : userList2)
            userDao.delete(user5);



        //query
        List<User> userList = userDao.queryBuilder().build().list();
        for (User user : userList)
            Log.i(TAG, user.toString());
    }


}
