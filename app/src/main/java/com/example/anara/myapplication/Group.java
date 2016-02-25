package com.example.anara.myapplication;

/**
 * Created by anara on 2/15/2016.
 */
public class Group {
    String name;
    String desc;
    String id;

    public String getGroupName() { return name; }

    public void setGroupName(String name) { this.name = name; }

    public String getDescription() { return desc; }

    public void setDescription( String desc){ this.desc = desc; }

    public String getGroupId() { return id; }

    public void setGroupId( String id ){ this.id = id; }

    public Group(){

    }

    public Group(String id, String name, String desc){this.id = id; this.name = name; this.desc = desc;}
}
