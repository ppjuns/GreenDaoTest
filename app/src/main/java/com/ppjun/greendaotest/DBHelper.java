package com.ppjun.greendaotest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Package :com.ppjun.greendaotest
 * @Description :
 * @Author :Rc3
 * @Created at :2016/8/18 20:38.
 */
public class DBHelper extends DaoMaster.OpenHelper {
    public static final String DBNAME = "user.db";
    private static final SortedMap<Integer, Migration> ALL_MIGRATIONS = new TreeMap<>();

    {

         ALL_MIGRATIONS.put(1,new V1Migration());
    }

    public DBHelper(Context context) {
        super(context, DBNAME, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        executeMigrations(db, ALL_MIGRATIONS.keySet());
    }

    private void executeMigrations(SQLiteDatabase db, Set<Integer> integers) {
        for (Integer version : integers) {
            ALL_MIGRATIONS.get(version).migrate(db);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        SortedMap<Integer, Migration> migrations = ALL_MIGRATIONS.subMap(oldVersion, newVersion);
        executeMigrations(db, migrations.keySet());

    }


    public interface Migration {
        void migrate(SQLiteDatabase db);


    }
//新增列age，如果有多个操作写多个V1Migration
    public class V1Migration implements Migration {

        @Override
        public void migrate(SQLiteDatabase db) {
            db.execSQL("ALTER TABLE USER ADD COLUMN age");
        }
    }

}
