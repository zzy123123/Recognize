package com.rubbish.model;

import java.io.Serializable;

public class Question implements Serializable{
    private int id;
    private int ntype;
    private int userId;
    private String content;
    private String media;
    private long createTime;
    private String submitor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getNtype() {
        return ntype;
    }

    public void setNtype(int ntype) {
        this.ntype = ntype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubmitor() {
        return submitor;
    }

    public void setSubmitor(String submitor) {
        this.submitor = submitor;
    }
}
