package com.mysport.mysport_mobile.mocks;

import com.mysport.mysport_mobile.models.Member;

public class SessionMock {
    public static SessionMock instance = new SessionMock();
    private final Member member;

    private SessionMock(){
        member = new Member(1, "Michael", "Tomson", "mike.tomson@gmail.com");
    }

    public Member getMember() {
        return member;
    }
}
