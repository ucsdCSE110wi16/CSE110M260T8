package com.example.anara.myapplication;

/**
 * Created by anara on 2/18/2016
 * Add timestamp.
 */
public class Messages {
    Integer id;
    String message;
    Integer userId;
    boolean deleted;

    public Integer getMsgId(){ return id; }

    public void setMsgId( Integer id ){ this.id = id; }

    public String getMsg(){ return message; }

    public void setMsg( String message ){ this.message = message; }

    public Integer getMsgUId(){ return userId; }

    public void setMsgUId( Integer userId ){ this.userId = userId; }

    public boolean getMsgDel(){ return deleted; }

    public void setMsgDel( boolean deleted ){ this.deleted = deleted; }

}
