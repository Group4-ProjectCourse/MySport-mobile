package com.mysport.mysport_mobile.events;

import android.view.View;

import com.mysport.mysport_mobile.models.SportEvent;

public interface EventClickedListener {
    void onEventClicked(SportEvent sportEvent);
}