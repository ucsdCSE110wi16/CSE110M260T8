package com.example.anara.myapplication;

/**
 * Created by anara on 2/18/2016.
 */
public class GroupMembers {
    String groupId;
    String userId;
    String id;

    public String getGroupMem() { return groupId; }

    public String getGMemId() { return id;}

    public String setGMemId(String id) { this.id = id; }

    public void setGroupMem(String groupId) { this.groupId = groupId; }

    public String getUserMem() { return userId; }

    public void setUserMem(String userId) { this.userId = userId; }

    public GroupMembers(){

    }

}
