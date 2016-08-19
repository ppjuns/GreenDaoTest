package com.ppjun.greendaotest;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @Package :com.ppjun.greendaotest
 * @Description :
 * @Author :Rc3
 * @Created at :2016/8/18 15:27.
 */

@Entity
public class User {


    @Id
    private Long id;
    @Property(nameInDb = "age")
    private int age;
    @Property(nameInDb = "username")
    private String userName;
    @Property(nameInDb = "password")
    private String passWord;

    //唯一的@Unique
//不为空@NotNull
//不会持续话到数据库@Transient
    @Generated(hash = 1049676854)
    public User(Long id, int age, String userName, String passWord) {
        this.id = id;
        this.age = age;
        this.userName = userName;
        this.passWord = passWord;
    }

    @Generated(hash = 586692638)
    public User() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
