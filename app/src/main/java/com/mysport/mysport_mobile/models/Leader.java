package com.mysport.mysport_mobile.models;

import android.net.Uri;
import com.mysport.mysport_mobile.R;

public class Leader extends Member {
    private final String cardNum;
    private final String[] leadingSports;

    protected Leader(String firstname, String surname, String email, String cardNum, String[] leadingSports, Uri photo) {
        super(firstname, surname, email, photo);
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
