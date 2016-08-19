# GreenDaoTest
GreenDao3.1.0使用案例包含（增删查改，升级数据库），3.+版本比2.+更加便捷生成DaoMaster和DaoSession

##首先让你的android studio配置Greendao数据库
在build.gradle目录下
```java
dependencies {
    classpath 'com.android.tools.build:gradle:2.1.0'
    classpath 'org.greenrobot:greendao-gradle-plugin:3.1.0'

}
```

在app/build.gradle目录下设置

```java
apply plugin: 'org.greenrobot.greendao'
android {
   greendao{
        schemaVersion 1                    //数据库 版本号
        targetGenDir 'src/main/java'    //生成DaoMaster类文件夹
        daoPackage   'com.ppjun.greendaotest.db'  //生成DaoMaster类包名
    }

}
dependencies {
    compile 'org.greenrobot:greendao:3.1.0'
}
```



新建一个User类

```java
@Entity
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "username")
    private String userName;
    @Property(nameInDb = "password")
    private String passWord;
  
  
  //generate set和get方法 toString方法
  ...
}
```

1. @Entity 代表数据库里面的USER表
2. @Id 主键
3. @Property 表里面的内容
4. @Unique 唯一的
5. @Transient 不会被数据库持久化写进数据库 
6. @NotNull 不为空

到此为止，sync gradle来执行greendao配置，下面开始讲解怎么使用


##GreenDao使用

```java
public static final String DB_NAME = "ppjun.db";//数据库名称
DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);//实例化一个DevOpenhelper,相当于sqlit的SQliteOpenHelper
DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());//实例化DaoMaster
DaoSession daoSession = daoMaster.newSession();//实例化DaoSession
UserDao userDao =daoSession.getUserDao(); //获取UserDao实例来对表user进行操作


//add，这里的null 代表自增长的id，你还可以为user表插入unique的userid
        User user1 = new User(null, "ag1", "123456");
        User user2 = new User(null, "ag2", "123456");
        User user3 = new User(null, "ag3", "123456");
        userDao.insert(user1);
        userDao.insert(user2);
        userDao.insert(user3);

 //update，这里更新id是3的user的名字，id从1开始的，在where来添加匹配条件
        User user4 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(3)).build().unique();
        user4.setUserName("kk");
        userDao.update(user4);

//delete，这里删除id是2的user
        List<User> userList2 = userDao.queryBuilder().where(UserDao.Properties.Id.eq(2)).build().list();
        for (User user5 : userList2)
            userDao.delete(user5);

  //query，重新user表全部user
        List<User> userList = userDao.queryBuilder().build().list();
        for (User user : userList)
            Log.i(TAG, user.toString());

```
上面完成数据库基本操作。

##下面来说GreenDao的升级数据库，在user表插入age

修改schemaVersion 2

user类

```java
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "age")
    private int age;
  ...
}
```
    

重写DBHelper类继承DaoMaster.OpenHelper 详细请看[GreenDao(2) ---- 数据库升级 onUpgrade](http://www.jianshu.com/p/e599a3b3aba5)

```java
public class DBHelper extends DaoMaster.OpenHelper {

    private static final SortedMap<Integer, Migration> ALL_MIGRATIONS = new TreeMap<>();

    {

        ALL_MIGRATIONS.put(1, new V1Migration());
    }

    public DBHelper(Context context) {
        super(context, GreenDaoUtils.DB_NAME, null);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        SortedMap<Integer, Migration> migrations = ALL_MIGRATIONS.subMap(oldVersion, newVersion);
        executeMigrations(db, migrations.keySet());

    }


    public interface Migration {
        void migrate(SQLiteDatabase db);


    }

    //新增列age，如果有多个操作写多个V1Migration，如V2Migration，并且调用ALL_MIGRATIONS.put
    public class V1Migration implements Migration {

        @Override
        public void migrate(SQLiteDatabase db) {
            db.execSQL("ALTER TABLE USER ADD COLUMN age");
        }
    }

}
```





这时候的DaoMaster.DevOpenHelper改为自定义DBHelper，这样子升级数据库就不会丢失原来的数据了

```java
DBHelper dbHelper = new DBHelper(context);
DaoMaster daoMaster = new DaoMaster(dbHelper.getWritableDb());
```

