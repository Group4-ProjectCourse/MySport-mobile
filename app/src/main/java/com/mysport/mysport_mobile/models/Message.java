package com.mysport.mysport_mobile.models;

public class Message {
    private String sender, content;
    private long published;

    Message(){

    }

    public Message(String sender, String content){
        this.sender = sender;
        this.content = content;
        this.published = System.currentTimeMillis();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPublished() {
        return published;
    }

    public void setPublished(long published) {
        this.published = published;
    }
}
