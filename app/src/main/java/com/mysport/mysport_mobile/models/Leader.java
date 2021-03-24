package com.mysport.mysport_mobile.models;

import com.mysport.mysport_mobile.R;

public class Leader extends Member {
    private final String cardNum;
    private final String[] leadingSports;

    protected Leader(String userId, String firstname, String surname, String email, String cardNum, String[] leadingSports) {
        super(userId, firstname, surname, email);
        this.cardNum = cardNum;
        this.leadingSports = leadingSports;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String[] getLeadingSport() {
        return leadingSports;
    }
}
