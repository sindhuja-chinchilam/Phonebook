package com.example.xinthe.phonebook;

/**
 * Created by xinthe on 8/10/2016.
 */
/**
 * Created by xinthe on 8/9/2016.
 */
public class HolderPhoneBook {
    public int id;
    public String fname,lname,phoneNumber,nickname;
    public HolderPhoneBook(int id,String fname,String lname,String phoneNumber,String nickname) {
        this.id = id;
        this.fname = fname;
        this.lname=lname;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
    }
    public HolderPhoneBook(String fname,String lname,String phoneNumber,String nickname) {
        this.fname = fname;
        this.lname=lname;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;

    }
    public String getFname(){
        return fname;
    }
    public String getLname() { return lname;}
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getNickname(){
        return nickname;
    }
    public int getId(){
        return id;
    }
}
