package com.mysport.mysport_mobile.fragments.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.events.DoubleClickListener;
import com.mysport.mysport_mobile.events.OnFragmentSendDataListener;
import com.mysport.mysport_mobile.models.MongoManager;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FloatingFragment extends Fragment {

    private OnFragmentSendDataListener<MongoManager.MongoActivity> fragmentSendDataListener;

    private Dialog dialog;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private FloatingActionButton addButton, edit, addMember;
    private Boolean clicked = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener<MongoManager.MongoActivity>) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.class_cast_exception_fragment_data_send_event));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.floating_buttons, container, false);

        dialog = new Dialog(getContext());
        addButton = view.findViewById(R.id.day_view_add_button);
        edit = view.findViewById(R.id.day_view_edit_button);
        addMember = view.findViewById(R.id.day_view_add_person_button);

        loadAnimations();

        addButton.setOnClickListener(new DoubleClickListener(300) {
            @Override
            public void onDoubleClick(View v) {
                showAddForm(v);
            }
        });
        addButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OnAddButtonClicked();
                return true;
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

        return view;
    }

    private void OnAddButtonClicked() {
        setVisibility();
        startAnimation();
        setClickable();
        clicked = !clicked;
    }

    private void startAnimation() {
        addButton.startAnimation(clicked ? rotateClose : rotateOpen);
        edit.startAnimation(clicked ? toBottom : fromBottom);
        addMember.startAnimation(clicked ? toBottom : fromBottom);
    }

    private void setVisibility() {
        edit.setVisibility(clicked ? View.INVISIBLE : View.VISIBLE);
        addMember.setVisibility(clicked ? View.INVISIBLE : View.VISIBLE);
    }

    private void setClickable(){
        edit.setClickable(!clicked);
        addMember.setClickable(!clicked);
    }

    public void showAddForm(View v){
        dialog.setContentView(R.layout.form_add_sport);

        TextView textClose = dialog.findViewById(R.id.popup_txtClose_button);
        EditText sportName, date, timeStart, timeEnd, location;
        Button submit = dialog.findViewById(R.id.form_add_button_create);

        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        date = dialog.findViewById(R.id.form_add_date);
        timeStart = dialog.findViewById(R.id.form_add_time_start);
        timeEnd = dialog.findViewById(R.id.form_add_time_end);
        sportName = dialog.findViewById(R.id.form_add_sportname);
        location = dialog.findViewById(R.id.form_add_location);

        timeEnd.setInputType(InputType.TYPE_NULL);
        date.setInputType(InputType.TYPE_NULL);
        timeStart.setInputType(InputType.TYPE_NULL);

//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDateDialog(date);
//            }
//        });

        date.setText(new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH)
                .format(new Date(
                        ((MainActivity) getActivity()).getDayViewFragment().getToday().getTimeInMillis() //not null dont worry
                )));

        timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(timeStart);
            }
        });

        timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(timeEnd);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MongoManager.MongoActivity sport = new MongoManager.MongoActivity(
                        sportName.getText().toString(),
                        location.getText().toString(),
                        Arrays.stream(timeStart.getText().toString().split(":")).mapToInt(Integer::parseInt).toArray(),
                        Arrays.stream(timeEnd.getText().toString().split(":")).mapToInt(Integer::parseInt).toArray()
                );

                fragmentSendDataListener.onSendData(sport);

//                new Thread(() -> {
//                    MongoManager database = App.getMongo();
//                    database.addActivity(LocalDate.now(), sport);
//                }).start();
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showDateTimeDialog(final EditText dateTime) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm", Locale.ENGLISH);

                        dateTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimeDialog(final EditText time) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                time.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true)
                .show();
    }

    private void showDateDialog(final EditText date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void loadAnimations(){
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_to_bottom_anim);
    }
}
