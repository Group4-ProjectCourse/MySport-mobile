package com.mysport.mysport_mobile.events;

import com.mysport.mysport_mobile.models.SportEvent;

public abstract class DoubleClickEventListener implements EventClickedListener {

    private long lastClickTime = 0;
    private final long DOUBLE_CLICK_TIME_DELTA;

    public DoubleClickEventListener(long within){
        this.DOUBLE_CLICK_TIME_DELTA = within;
    }

    @Override
    public void onEventClicked(SportEvent sportEvent) {
        long clickTime = System.currentTimeMillis();
        long delta = clickTime - lastClickTime;
        if (delta < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(sportEvent);
        }
        lastClickTime = clickTime;
    }

    public abstract void onDoubleClick(SportEvent sportEvent);
}
