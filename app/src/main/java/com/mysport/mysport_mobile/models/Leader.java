package com.mysport.mysport_mobile.models;

public class Leader extends Member {
    private String cardNum;
    private String leadingSport;

    protected Leader(String userId, String firstname, String surname, String email, String cardNum, String leadingSport) {
        super(userId, firstname, surname, email);
        this.cardNum = cardNum;
        this.leadingSport = leadingSport;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getLeadingSport() {
        return leadingSport;
    }
}
