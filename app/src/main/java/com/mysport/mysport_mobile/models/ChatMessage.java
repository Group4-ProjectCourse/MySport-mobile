package com.mysport.mysport_mobile.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {
    private String id, sender, content;
    private long createdAt;

    public ChatMessage(String id, String sender, String content, long createdAt){
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long published) {
        this.createdAt = published;
    }

    public String getTimeClean(){

        return "created at: " +
                new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.UK)
                        .format(new Date(createdAt));
    }
}
