package com.mysport.mysport_mobile.models;

public class Session<T extends Member> {
    private final T member;

    public Session(T member) {
        this.member = member;
    }

    public T getUser() {
        return member;
    }
}
