package com.ppjun.greendaotest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ppjun.greendaotest.db.DaoMaster;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Package :com.ppjun.greendaotest
 * @Description :升级数据库，表user新增列age
 * @Author :Rc3
 * @Created at :2016/8/18 20:38.
 */
public class DBHelper extends DaoMaster.OpenHelper {

  public DBHelper(Context context) {
        super(context, DB_NAME,null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        NoteDao.createTable(db,true);
        db.execSQL("ALTER TABLE NOTE ADD COLUMN age");
    }

}
