package com.example.whatsapp.models;

public class user {

    String profpic,username,mail,pass,userid,lastmsg,status;
    public user(String profpic, String username, String mail, String pass, String userid, String lastmsg) {
        this.profpic = profpic;
        this.username = username;
        this.mail = mail;
        this.pass = pass;
        this.userid = userid;
        this.lastmsg = lastmsg;
    }
 public user(){}
    public user( String username, String mail, String pass) {

        this.username = username;
        this.mail = mail;
        this.pass = pass;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfpic() {
        return profpic;
    }

    public void setProfpic(String profpic) {
        this.profpic = profpic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUserid(String key) {
        return userid;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }
}
