package com.mysport.mysport_mobile.models;

public class ForumPost {
    private String id;
    private String author;
    private String title;
    private String content;
    private String createdAt;
    private String timeRaw;
    private String lastModified;
    private String[] comments;

    public ForumPost(){

    }

    public ForumPost(String id, String author, String title, String createdAt, String content, String timeRaw){
        this.id = id;
        this.author = author;
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
        this.timeRaw = timeRaw;
    }

    public ForumPost(String id, String author, String title, String content, String createdAt, String timeRaw, String lastModified, String[] comments) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.timeRaw = timeRaw;
        this.lastModified = lastModified;
        this.comments = comments;
    }

    public ForumPost setComments(String[] comments) {
        this.comments = comments;
        return this;
    }

    public ForumPost setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public ForumPost setAuthor(String author) {
        this.author = author;
        return this;
    }

    public ForumPost setContent(String content) {
        this.content = content;
        return this;
    }

    public ForumPost setLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String[] getComments() {
        return comments;
    }

    public String getTitle() {
        return title;
    }

    public ForumPost setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTimeRaw() {
        return timeRaw;
    }

    public ForumPost setTimeRaw(String timeRaw) {
        this.timeRaw = timeRaw;
        return this;
    }
}
