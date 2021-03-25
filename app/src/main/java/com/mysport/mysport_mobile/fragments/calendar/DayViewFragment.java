package com.mysport.mysport_mobile.fragments.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.enums.TransactionAction;
import com.mysport.mysport_mobile.events.DoubleClickEventListener;
import com.mysport.mysport_mobile.models.CalendarRange;
import com.mysport.mysport_mobile.models.Member;
import com.mysport.mysport_mobile.models.MongoActivity;
import com.mysport.mysport_mobile.models.SportEvent;
import com.mysport.mysport_mobile.utils.CalendarUtils;
import com.mysport.mysport_mobile.utils.Networking;
import com.mysport.mysport_mobile.views.DayView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DayViewFragment extends Fragment {

    private FloatingFragment floatingFragment;
    private DayView dayView;
    private final Calendar today;

    public DayViewFragment(Calendar day){
        this.today = day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_view, container, false);
        dayView = view.findViewById(R.id.dayView);
        floatingFragment = new FloatingFragment();

        MainActivity parent = (MainActivity) getActivity();
        parent.handleFragment(TransactionAction.ADD, R.id.main_place_for_floating_buttons, floatingFragment);
        parent.getViewOption().setVisibility(View.VISIBLE);
        parent.getViewOption().setClickable(true);

//        dayView.addEventClickedListener(new EventClickedListener() {
//                @Override
//                public void onEventClicked(SportEvent sportEvent) {
//                    Toast.makeText(getContext(), sportEvent.getSportName(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        );

        dayView.addEventClickedListener(new DoubleClickEventListener(300){
                boolean flag = true;
                final boolean isLeader = App.getSession().getUser().isLeader();
                final MaterialAlertDialogBuilder[] builders = createBuilders(new ContextThemeWrapper(getContext(), R.style.Theme_MaterialComponents_Light_DarkActionBar), isLeader);
                @Override
                public void onDoubleClick(SportEvent sportEvent) {
                    Toast.makeText(getContext(), sportEvent.getSportName(), Toast.LENGTH_SHORT).show();
                    JSONObject json = new JSONObject();
                    //const { date, title, fullname } = req.body;
                    try {
                        json.put("title", sportEvent.getSportName())
                                .put("fullname", App.getSession().getUser().toString())
                                .put("date", today.getTime());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    builders[0]
                        .setTitle((flag ? "Join" : "Quit") + " " + sportEvent.getSportName() + "?")
                        .setMessage(String.format((flag ? "Please press confirm button to join %s" : "Are you sure you want to quit %s?"), sportEvent.getSportName()))
                        .setPositiveButton(flag ? "JOIN" : "QUIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(flag) {
                                sportEvent.getParticipants().add(App.getSession().getUser().toString());
                                dialog.dismiss();
                                Toast.makeText(getContext(), String.format(getString(R.string.joined_sport_dialog), sportEvent.getSportName()), Toast.LENGTH_SHORT).show();
                                String[] names = sportEvent.getNames();
                                builders[1]
                                        //.setMessage(String.format("Please mark the people that appeared on %s session.", sportEvent.getSportName()))
                                        .setMultiChoiceItems(names, new boolean[names.length], (dialog1, which1, isChecked) -> {

                                        });
                                //record in DB
                                String url = App.baseURL + "sports/add-participant";
                                Networking.volleyPost(getContext(), url, json);
                            }
                            else {
                                if(sportEvent.getParticipants().remove(App.getSession().getUser().toString())){
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), String.format(getString(R.string.unjoined_sport_dialog), sportEvent.getSportName()), Toast.LENGTH_SHORT).show();
                                    String[] names = sportEvent.getNames();
                                    builders[1]
                                            .setMultiChoiceItems(names, new boolean[names.length], (dialog1, which1, isChecked) -> {

                                            });
                                    //record in DB
                                    String url = App.baseURL + "sports/remove-participant";
                                    Networking.volleyPost(getContext(), url, json);

                                    flag = !flag;
                                }
                            }
                            //no change detection please
                        }
                    });
                    if(isLeader){
                        //if session is for leader mark attendance
                        builders[0].setNeutralButton("Attendance", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(sportEvent.getParticipants() == null){
                                    Toast.makeText(getContext(), R.string.error_no_participants, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                dialog.dismiss();
                                builders[1].show();
                            }
                        });
                    }
                    if(sportEvent.getParticipants() != null){
                        String[] names = sportEvent.getNames();
                        builders[1]
                                //.setMessage(String.format("Please mark the people that appeared on %s session.", sportEvent.getSportName()))
                                .setMultiChoiceItems(names, new boolean[names.length], (dialog, which, isChecked) -> {
                                    
                                });
                    }

                    builders[0].show();
                }
        });

        Calendar startCalendar = CalendarUtils.createCalendar();
        startCalendar.add(Calendar.HOUR_OF_DAY, 12);
        startCalendar.add(Calendar.MINUTE, 23);

        Calendar endCalendar = CalendarUtils.createCalendar();
        endCalendar.add(Calendar.HOUR_OF_DAY,14);
        endCalendar.add(Calendar.MINUTE, 24);

//        dayView.addEvent(
//                new SportEvent(
//                        "Volleyball",
//                        new ArrayList<>(Arrays.asList(
//                                new Member(1, "Bob", "Marley", "deniel@mysport-community.com"),
//                                new Member(1, "John", "Cena", "deniel@mysport-community.com"),
//                                new Member(1, "Tom", "Soyeur", "deniel@mysport-community.com"),
//                                new Member(1, "Chuck", "Norris", "deniel@mysport-community.com"),
//                                new Member(1, "Arnold", "Stalone", "deniel@mysport-community.com"),
//                                new Member(1, "Silvestro", "Rembo", "deniel@mysport-community.com")
//                        )),
//                        new CalendarRange(startCalendar, endCalendar)
//                ));

        return view;
    }

    private MaterialAlertDialogBuilder[] createBuilders(Context context, boolean isLeader) {
        MaterialAlertDialogBuilder join = new MaterialAlertDialogBuilder(context)
                .setIcon(R.drawable.ic_sports)
                .setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.popup_join_bc, null));
        join.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        MaterialAlertDialogBuilder attendance = isLeader ? new MaterialAlertDialogBuilder(context)
                .setTitle("Mark Attendance")
                .setIcon(R.drawable.ic_edit)
                .setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.popup_join_bc, null))
                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("MARK SELECTED", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save to MongoDB the attendance
                        Toast.makeText(getContext(), "Attendance has been saved in database", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        join.show();
                    }
                }) : null;

        return new MaterialAlertDialogBuilder[] { join, attendance };
    }

    private void drawSportEvent(MongoActivity sport){
        Calendar startCalendar = CalendarUtils.createCalendar();
        startCalendar.add(Calendar.HOUR_OF_DAY, sport.getStartHour());
        startCalendar.add(Calendar.MINUTE, sport.getStartMinutes());

        Calendar endCalendar = CalendarUtils.createCalendar();
        endCalendar.add(Calendar.HOUR_OF_DAY, sport.getEndHour());
        endCalendar.add(Calendar.MINUTE, sport.getEndMinutes());

        dayView.addEvent(new SportEvent(
                sport.getTitle(),
                sport.getParticipants(),
                new CalendarRange(startCalendar, endCalendar)
        ));

//        dayView.addEvent(new SportEvent(
//                sport.getTitle(),
//                new ArrayList<>(Arrays.asList(
//                        new Member("123", "Bob", "Marley", "deniel@mysport-community.com"),
//                        new Member("132", "John", "Cena", "deniel@mysport-community.com"),
//                        new Member("213", "Tom", "Soyeur", "deniel@mysport-community.com"),
//                        new Member("231", "Chuck", "Norris", "deniel@mysport-community.com"),
//                        new Member("312", "Arnold", "Stalone", "deniel@mysport-community.com"),
//                        new Member("321", "Silvestro", "Rembo", "deniel@mysport-community.com")
//                )),
//                new CalendarRange(startCalendar, endCalendar)
//        ));
        //sport.getTimeSpan() + "\n\n" + getString(R.string.participants_example)
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

    public void receiveItem(MongoActivity sport) {
        drawSportEvent(sport);
    }

    public Calendar getToday() {
        return today;
    }
}
//
//    Calendar startCalendar = CalendarUtils.createCalendar();
////        startCalendar.add(Calendar.DATE, -1);
////        startCalendar.add(Calendar.HOUR_OF_DAY, 23);
//
//    Calendar endCalendar = CalendarUtils.createCalendar();
////        endCalendar.add(Calendar.HOUR_OF_DAY, 2);
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
//                startCalendar.add(Calendar.HOUR_OF_DAY, 20);
//
//                endCalendar = CalendarUtils.createCalendar();
//                endCalendar.add(Calendar.HOUR_OF_DAY, 22);
//
//                dayView.addEvent(new SportEvent(
//                "Test Event Name 3",
//                "Test Event Description 3",
//                new CalendarRange(startCalendar, endCalendar)
//                )
//                );
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
//                startCalendar = CalendarUtils.createCalendar();
//                startCalendar.add(Calendar.HOUR_OF_DAY, 18);
//
//                endCalendar = CalendarUtils.createCalendar();
//                endCalendar.add(Calendar.HOUR_OF_DAY, 19);
//                endCalendar.add(Calendar.MINUTE, 35);
//
//                dayView.addEvent(new SportEvent(
//                "Test Event Name 5",
//                "Test Event Description 5",
//                new CalendarRange(startCalendar, endCalendar)
//                ));
//
//                startCalendar = CalendarUtils.createCalendar();
//                startCalendar.add(Calendar.HOUR_OF_DAY, 17);
//
//                endCalendar = CalendarUtils.createCalendar();
//                endCalendar.add(Calendar.HOUR_OF_DAY, 20);
//                endCalendar.add(Calendar.MINUTE, 35);
//
//                dayView.addEvent(new SportEvent(
//                "Test Event Name 555",
//                "Test Event Description 555",
//                new CalendarRange(startCalendar, endCalendar)
//                ));
//
//                startCalendar = CalendarUtils.createCalendar();
//                startCalendar.add(Calendar.HOUR_OF_DAY, 16);
//
//                endCalendar = CalendarUtils.createCalendar();
//                endCalendar.add(Calendar.HOUR_OF_DAY, 19);
//                endCalendar.add(Calendar.MINUTE, 35);
//
//                dayView.addEvent(new SportEvent(
//                "Test Event Name 512",
//                "Test Event Description 512",
//                new CalendarRange(startCalendar, endCalendar)
//                ));
//
//                startCalendar = CalendarUtils.createCalendar();
//                startCalendar.add(Calendar.HOUR_OF_DAY, 15);
//
//                endCalendar = CalendarUtils.createCalendar();
//                endCalendar.add(Calendar.HOUR_OF_DAY, 21);
//
//                dayView.addEvent(new SportEvent(
//                "Test Event Name 6",
//                "Test Event Description 6",
//                new CalendarRange(startCalendar, endCalendar)
//                ));