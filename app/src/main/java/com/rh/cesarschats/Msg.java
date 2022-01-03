package com.rh.cesarschats;

public class Msg {
    private String user_name;
    private String msg;
    public Msg(String user_name, String msg) {
        this.user_name = user_name;
        this.msg = msg;


    }
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
