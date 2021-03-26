package com.mysport.mysport_mobile.fragments;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.R;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Random;

public class ProfileFragment extends Fragment {
    private TextView textViewFullName;
    private TextInputEditText textViewFullNameBelow;
    private TextInputEditText textViewPhone;
    private TextInputEditText textViewAddress;
    private TextInputEditText textViewEmail;
    private CircleImageView circleImageView;

    private String[] address = {"Olastorpsvagen 15D", "Olastorpsvagen 23 Lgh 1108", "Österlånggatan 21C"};
    private String[] phoneNumbers = {"0728871234", "0738912311", "0729912345"};
    private Random random;


    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewFullName = view.findViewById(R.id.full_name);
        textViewFullNameBelow = view.findViewById(R.id.textEditName);
        textViewEmail = view.findViewById(R.id.textEditEmail);
        textViewPhone = view.findViewById(R.id.textEditPhone);
        textViewAddress = view.findViewById(R.id.textEditAddress);
        circleImageView = view.findViewById(R.id.profileImage);
        random = new Random();


        textViewFullName.setText(App.getSession().getUser().getFirstname() + " " + App.getSession().getUser().getSurname());
        textViewFullNameBelow.setText(App.getSession().getUser().getFirstname() + " " + App.getSession().getUser().getSurname());
        textViewAddress.setText(address[random.nextInt(2)]);
        textViewPhone.setText(phoneNumbers[random.nextInt(2)]);

        return view;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}