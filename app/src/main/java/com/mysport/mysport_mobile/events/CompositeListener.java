//package com.mysport.mysport_mobile.events;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CompositeListener implements OnEventListener {
//    private List<OnEventListener> registeredListeners = new ArrayList<OnEventListener>();
//
//    public void registerListener (MediaDrm.OnEventListener listener) {
//        registeredListeners.add(listener);
//    }
//
//    public void onEvent(Event e) {
//        for(MediaDrm.OnEventListener listener:registeredListeners) {
//            listener.onEvent(e);
//        }
//    }
//}
