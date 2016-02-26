package com.example.anara.myapplication;

import java.util.Date;

/*
 * Created by anara on 2/18/2016
 * Add timestamp.
 */
public class Messages {
    private String id;
    private String message;
    private String userId;
    private Date messageDate;
    private boolean deleted;

    public Date getMessageDate(){ return messageDate; }

    public void setMessageDate(Date date){ this.messageDate = date; }

    public String getMsgId(){ return id; }

    public void setMsgId( String ids ){ id = ids; }

    public String getMsg(){ return message; }

    public void setMsg( String messages ){ message = messages; }

    //public String getMsgUId(){ return userId; }

    //public void setMsgUId( String userId ){ this.userId = userId; }

    //public boolean getMsgDel(){ return deleted; }

    //public void setMsgDel( boolean deleted ){ this.deleted = deleted; }

    public Messages(){

    }
}
