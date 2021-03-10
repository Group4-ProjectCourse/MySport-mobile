package com.mysport.mysport_mobile.events;

import android.view.View;

public abstract class DoubleClickListener implements View.OnClickListener {

    private long lastClickTime = 0;
    private final long DOUBLE_CLICK_TIME_DELTA;

    public DoubleClickListener(long within){
        this.DOUBLE_CLICK_TIME_DELTA = within;
    }

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v);
        }
        else
            onSingleClick(v);

        lastClickTime = clickTime;
    }

    public abstract void onDoubleClick(View v);

    public abstract void onSingleClick(View v);
}
