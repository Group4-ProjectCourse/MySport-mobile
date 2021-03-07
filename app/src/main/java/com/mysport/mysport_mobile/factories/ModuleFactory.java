//package com.mysport.mysport_mobile.factories;
//
//import android.content.Context;
//
//import com.mysport.mysport_mobile.App;
//import com.mysport.mysport_mobile.R;
//import com.mysport.mysport_mobile.activities.calendar.DayViewActivity;
//import com.mysport.mysport_mobile.activities.calendar.MonthViewActivity;
//import com.mysport.mysport_mobile.models.Module;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class ModuleFactory {
//    public Module getModule(@Module.ActivityName String name) {
//        Context context = App.getComponent().getAppContext();
//        if (name == null || name.isEmpty()) {
//            return null;
//        } else if (Module.DAY_VIEW_ACTIVITY.equals(name)) {
//            return new Module(name, context.getString(R.string.day_view_activity_description), DayViewActivity.class);
//        } else if (Module.MONTH_VIEW_ACTIVITY.equals(name)) {
//            return new Module(name, context.getString(R.string.month_view_activity_description), MonthViewActivity.class);
//        }
//
//        return null;
//    }
//
//    public List<Module> getDefaultModuleList() {
//        return Arrays.asList(
//                getModule(Module.DAY_VIEW_ACTIVITY),
//                getModule(Module.MONTH_VIEW_ACTIVITY)
//        );
//    }
//}
