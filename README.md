# GreenDao
GreenDao3.1.0使用案例包含（增删查改，升级数据库），3.+版本比2.+更加便捷生成DaoMaster和DaoSession
[本文项目地址](https://github.com/gdmec07120731/GreenDaoTest)
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

如果遇到在生产包下找不到DaoMaster等文件，配置完上述代码要先Run一下。才开始下面代码。
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

1、修改build.gradle下面的schemaVersion 2

2、在user类，新增age对象

```java
public class User {
    @Id
    private Long id;
    @Property(nameInDb = "age")
    private int age;
  //generate getter and setter & toString
}
```
    



3、你要新建一个类MyDBHelper继承DaoMaster.OpenHelper,在类的构造函数传入Context，super(context,DB_NAME,null);还要重写onUpgrade方法（注意这里的参数一是Database），然后创建表(传入true，这里使用IF NOT EXISTS)不用担心表不存在，还有执行增加age列sql语句 db.exeSQL("ALTER TABLE USER ADD COLUMN age");

```java
public class MyDBHelper extends DaoMaster.OpenHelper {
    public MyDBHelper(Context context) {
        super(context, DB_NAME,null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        NoteDao.createTable(db,true);
        db.execSQL("ALTER TABLE NOTE ADD COLUMN age");
    }


}
```


这时候的DaoMaster.DevOpenHelper改为自定义DBHelper，这样子升级数据库就不会丢失原来的数据了

```java
DBHelper dbHelper = new DBHelper(context);
DaoMaster daoMaster = new DaoMaster(dbHelper.getWritableDb());
```


[本文项目地址](https://github.com/gdmec07120731/GreenDaoTest)
