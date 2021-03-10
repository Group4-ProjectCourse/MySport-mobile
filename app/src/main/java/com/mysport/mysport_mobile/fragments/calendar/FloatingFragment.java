package com.mysport.mysport_mobile.fragments.calendar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.mysport.mysport_mobile.events.DoubleClickListener;

public class FloatingFragment extends Fragment {
    private Dialog dialog;
    private Animation rotateOpen, rotateClose, fromBottom, toBottom;
    private FloatingActionButton addButton, edit, addMember;
    private Boolean clicked = false;

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

    private void createSportEvent(){

    }

    public void showAddForm(View v){
        dialog.setContentView(R.layout.add_form);
        TextView textClose = dialog.findViewById(R.id.popup_txtClose_button);
        Button follow = dialog.findViewById(R.id.popup_follow_button);

        textClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void loadAnimations(){
        rotateOpen = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getContext(), R.anim.dayview_to_bottom_anim);
    }
}
