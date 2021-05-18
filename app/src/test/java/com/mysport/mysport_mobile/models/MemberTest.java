package com.mysport.mysport_mobile.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberTest {

    Member member = new Member(1, "David", "Johns", "david.j@mail.com");

    @Test
    public void getFirstname() {
        String expected = "David";
        assertEquals(expected, member.getFirstname());
    }

    @Test
    public void getSurname() {
        String expected = "Johns";
        assertEquals(expected, member.getSurname());
    }

    @Test
    public void getEmail() {
        String expected = "david.j@mail.com";
        assertEquals(expected, member.getEmail());
    }
}