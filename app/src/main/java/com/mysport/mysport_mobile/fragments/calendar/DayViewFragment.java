package com.mysport.mysport_mobile.fragments.calendar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.SportEvent;
import com.mysport.mysport_mobile.views.DayView;

public class DayViewFragment extends Fragment {
    private Dialog dialog;
    private Animation rotateOpen, rotateClose, fromBotton, toBottom;
    private FloatingActionButton addActivity, edit, addMember;
    private Boolean clicked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        DayView dayView = view.findViewById(R.id.dayView);
        addActivity = view.findViewById(R.id.day_view_add_button);
        edit = view.findViewById(R.id.day_view_edit_button);
        addMember = view.findViewById(R.id.day_view_add_person_button);
        dialog = new Dialog(getContext());
        loadAnimations();

        dayView.addEventClickedListener(new DayView.EventClickedListener() {
                @Override
                public void onEventClicked(SportEvent sportEvent) {
                    Toast.makeText(getContext(), sportEvent.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        );

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAddButtonClicked();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit button clicked", Toast.LENGTH_SHORT).show();
            }
        });

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add Member button clicked", Toast.LENGTH_SHORT).show();
            }
        });

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

    private void OnAddButtonClicked() {
        setVisibility();
        setAnimation();
        setClickable();
    }

    private void setAnimation() {

    }

    private void setVisibility() {
        edit.setVisibility((clicked = !clicked) ? View.INVISIBLE : View.VISIBLE);
        addMember.setVisibility(clicked ? View.INVISIBLE : View.VISIBLE);
    }

    private void setClickable(){
        edit.setClickable(!clicked);
        addMember.setClickable(!clicked);
    }

    private void createSportEvent(){
        addActivity.startAnimation(clicked ? rotateClose : rotateOpen);
        edit.startAnimation(clicked ? toBottom : fromBotton);
        addActivity.startAnimation(clicked ? toBottom : fromBotton);
    }

    public void showAddForm(View v){
        TextView textClose;
        Button follow;
        dialog.setContentView(R.layout.add_form);
        textClose = dialog.findViewById(R.id.popup_txtClose_button);
        follow = dialog.findViewById(R.id.popup_follow_button);
        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void loadAnimations(){
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_close_anim);
        fromBotton = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_to_bottom_anim);
    }
}
