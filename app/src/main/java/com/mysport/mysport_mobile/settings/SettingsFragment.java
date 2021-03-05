package com.mysport.mysport_mobile.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import com.mysport.mysport_mobile.R;

public class SettingsFragment extends Fragment {

    private final String[] languages = { "English", "Swedish", "Norwegian" };
    private Spinner spinnerLanguage;
    private Spinner spinnerColour;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_table, container, false);

        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
        spinnerColour = view.findViewById(R.id.spinnerColour);
        ArrayAdapter adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item,  languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(adapterView.getContext(), "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
}