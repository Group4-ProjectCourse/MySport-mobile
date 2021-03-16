package com.mysport.mysport_mobile.models;

public class Session {
    private final Member member;

    public Session(Member member) {
        this.member = member;
    }

    public Member getUser() {
        return member;
    }
}
