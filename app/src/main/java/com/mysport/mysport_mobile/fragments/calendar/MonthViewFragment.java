package com.mysport.mysport_mobile.fragments.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.utils.CalendarUtils;
import com.mysport.mysport_mobile.views.MonthView;

import java.util.Calendar;

public class MonthViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_month_view, container, false);
        MonthView firstMonthView = view.findViewById(R.id.firstMonthView);

        firstMonthView.setOnSelectedDayListener(new MonthView.OnSelectedDayListener() {
            @Override
            public void onSelectedDay(Calendar day) {
                Toast.makeText(getContext(), CalendarUtils.toSimpleString(day), Toast.LENGTH_SHORT).show();
            }
        });

        MonthView secondMonthView = view.findViewById(R.id.secondMonthView);

        Calendar calendar = secondMonthView.getMonth().getStart();
        calendar.add(Calendar.MONTH, 1);
        secondMonthView.setMonth(calendar);

        secondMonthView.setOnChevronSelectedListener(new MonthView.OnChevronSelectedListener() {
            @Override
            public void onLeftChevronSelected() {
                Calendar cal = secondMonthView.getMonth().getStart();
                cal.add(Calendar.MONTH, -1);
                secondMonthView.setMonth(cal);
            }

            @Override
            public void onRightChevronSelected() {
                Calendar cal = secondMonthView.getMonth().getStart();
                cal.add(Calendar.MONTH, 1);
                secondMonthView.setMonth(cal);
            }
        });

        return view;
    }
}
