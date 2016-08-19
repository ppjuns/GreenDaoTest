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
   @Property(nameInDb = "username")
    private String userName;
    @Property(nameInDb = "password")
    private String passWord;


    @Generated(hash = 936445358)
    public User(Long id, String userName, String passWord) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
