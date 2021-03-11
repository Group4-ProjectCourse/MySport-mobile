package com.mysport.mysport_mobile.fragments.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.enums.TransactionAction;
import com.mysport.mysport_mobile.models.CalendarRange;
import com.mysport.mysport_mobile.models.MongoManager;
import com.mysport.mysport_mobile.models.SportEvent;
import com.mysport.mysport_mobile.utils.CalendarUtils;
import com.mysport.mysport_mobile.views.DayView;

import java.util.Calendar;

public class DayViewFragment extends Fragment {

    private FloatingFragment floatingFragment;
    private DayView dayView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        dayView = view.findViewById(R.id.dayView);
        floatingFragment = new FloatingFragment();

        MainActivity parent = (MainActivity) getActivity();
        parent.handleFragment(TransactionAction.ADD, R.id.main_place_for_floating_buttons, floatingFragment);
        parent.getViewOption().setVisibility(View.VISIBLE);
        parent.getViewOption().setClickable(true);

        dayView.addEventClickedListener(new DayView.EventClickedListener() {
                @Override
                public void onEventClicked(SportEvent sportEvent) {
                    Toast.makeText(getContext(), sportEvent.getSportName(), Toast.LENGTH_SHORT).show();
                }
            }
        );

//        Calendar startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.DATE, -1);
//        startCalendar.add(Calendar.HOUR_OF_DAY, 23);
//
//        Calendar endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 2);
//
//        dayView.addEvent(
//                new SportEvent(
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam lobortis scelerisque ante, a porttitor eros interdum ut. Aliquam eu viverra ipsum. Vestibulum vel risus massa. Suspendisse ligula turpis, congue eu ipsum vestibulum, porta tincidunt nibh. Curabitur tincidunt dictum molestie. Quisque consectetur libero ac ornare pulvinar. In dapibus mi quis tristique molestie. Donec fermentum pretium enim ac sagittis. Duis ut venenatis sapien. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed faucibus ornare diam, sed laoreet lacus commodo eget. In ultricies sodales odio eget imperdiet. Nunc lobortis turpis et est euismod, ac dapibus massa lobortis. Mauris porta odio vitae risus molestie fermentum. Praesent a arcu urna.",
//                        "Test Event Description",
//                        new CalendarRange(startCalendar, endCalendar)
//                ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 23);
//        startCalendar.add(Calendar.MINUTE, 55);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.DATE, 1);
//        endCalendar.add(Calendar.HOUR_OF_DAY, 23);
//        endCalendar.add(Calendar.MINUTE, 56);
//
//        dayView.addEvent(new SportEvent(
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam lobortis scelerisque ante, a porttitor eros interdum ut. Aliquam eu viverra ipsum. Vestibulum vel risus massa. Suspendisse ligula turpis, congue eu ipsum vestibulum, porta tincidunt nibh. Curabitur tincidunt dictum molestie. Quisque consectetur libero ac ornare pulvinar. In dapibus mi quis tristique molestie. Donec fermentum pretium enim ac sagittis. Duis ut venenatis sapien. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed faucibus ornare diam, sed laoreet lacus commodo eget. In ultricies sodales odio eget imperdiet. Nunc lobortis turpis et est euismod, ac dapibus massa lobortis. Mauris porta odio vitae risus molestie fermentum. Praesent a arcu urna.",
//                "Test Event Description",
//                new CalendarRange(startCalendar, endCalendar)
//        ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 5);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 10);
//
//        dayView.addEvent(new SportEvent(
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam lobortis scelerisque ante, a porttitor eros interdum ut. Aliquam eu viverra ipsum. Vestibulum vel risus massa. Suspendisse ligula turpis, congue eu ipsum vestibulum, porta tincidunt nibh. Curabitur tincidunt dictum molestie. Quisque consectetur libero ac ornare pulvinar. In dapibus mi quis tristique molestie. Donec fermentum pretium enim ac sagittis. Duis ut venenatis sapien. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed faucibus ornare diam, sed laoreet lacus commodo eget. In ultricies sodales odio eget imperdiet. Nunc lobortis turpis et est euismod, ac dapibus massa lobortis. Mauris porta odio vitae risus molestie fermentum. Praesent a arcu urna.",
//                "Test Event Description",
//                new CalendarRange(startCalendar, endCalendar)
//        ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.DATE, -1);
//        startCalendar.add(Calendar.HOUR_OF_DAY, 14);
//        startCalendar.add(Calendar.MINUTE, 20);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.DATE, -1);
//        endCalendar.add(Calendar.HOUR_OF_DAY, 18);
//        endCalendar.add(Calendar.MINUTE, 45);
//
//        dayView.addEvent(new SportEvent(
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam lobortis scelerisque ante, a porttitor eros interdum ut.",
//                "Test Event Description 2",
//                new CalendarRange(startCalendar, endCalendar)
//        ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 20);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 22);
//
//        dayView.addEvent(new SportEvent(
//                "Test Event Name 3",
//                "Test Event Description 3",
//                new CalendarRange(startCalendar, endCalendar))
//        );
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 19);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 19);
//        endCalendar.add(Calendar.MINUTE, 55);
//
//        dayView.addEvent(new SportEvent(
//                "Test Event Name 4",
//                "Test Event Description 4",
//                new CalendarRange(startCalendar, endCalendar)
//        ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 18);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 19);
//        endCalendar.add(Calendar.MINUTE, 35);
//
//        dayView.addEvent(new SportEvent(
//                "Test Event Name 5",
//                "Test Event Description 5",
//                new CalendarRange(startCalendar, endCalendar)
//        ));
//
//        startCalendar = CalendarUtils.createCalendar();
//        startCalendar.add(Calendar.HOUR_OF_DAY, 15);
//
//        endCalendar = CalendarUtils.createCalendar();
//        endCalendar.add(Calendar.HOUR_OF_DAY, 21);
//
//        dayView.addEvent(new SportEvent(
//                "Test Event Name 6",
//                "Test Event Description 6",
//                new CalendarRange(startCalendar, endCalendar)
//        ));

        return view;
    }

    private void drawSportEvent(MongoManager.MongoActivity sport){
        Calendar startCalendar = CalendarUtils.createCalendar();
        Calendar endCalendar = CalendarUtils.createCalendar();

        startCalendar.add(Calendar.HOUR_OF_DAY, 15);
        startCalendar.add(Calendar.MINUTE, 35);
        endCalendar.add(Calendar.HOUR_OF_DAY, 21);
        endCalendar.add(Calendar.MINUTE, 35);

        dayView.addEvent(new SportEvent(
                sport.getName(),
                sport.getTimeSpan() + "\n\n" + getString(R.string.participants_example),
                new CalendarRange(startCalendar, endCalendar)
        ));
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity parent = (MainActivity) getActivity();
        parent.handleFragment(TransactionAction.REMOVE, R.id.main_place_for_floating_buttons, floatingFragment);//not null, source: "believe me bro"
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity parent = (MainActivity) getActivity();
        parent.getViewOption().setVisibility(View.INVISIBLE);//not null, source: "believe me bro"
        parent.getViewOption().setClickable(false);
    }

    public void receiveItem(MongoManager.MongoActivity sport) {
        drawSportEvent(sport);
    }
}
