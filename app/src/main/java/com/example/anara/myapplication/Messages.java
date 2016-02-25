package com.example.anara.myapplication;

/**
 * Created by anara on 2/18/2016
 * Add timestamp.
 */
public class Messages {
    String id;
    String message;
    String userId;
    boolean deleted;

    public String getMsgId(){ return id; }

    public void setMsgId( String id ){ this.id = id; }

    public String getMsg(){ return message; }

    public void setMsg( String message ){ this.message = message; }

    public String getMsgUId(){ return userId; }

    public void setMsgUId( String userId ){ this.userId = userId; }

    public boolean getMsgDel(){ return deleted; }

    public void setMsgDel( boolean deleted ){ this.deleted = deleted; }

}
