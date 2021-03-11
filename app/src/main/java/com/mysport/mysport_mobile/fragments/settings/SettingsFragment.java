package com.mysport.mysport_mobile.fragments.settings;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.language.LanguageManager;
import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment {

    private LanguageManager languageManager;
    private final String[] languages = {"English", "Svenska", "Norsk"};
    private final String[] colours = {"Purple", "Blue", "Black", "Dark Green"};
    private Spinner spinnerLanguage;
    private Spinner spinnerColour;
    private ArrayAdapter<?> adapterLanguage, adapterColour;
    private Button buttonSave;
    String colourSelected, languageSelected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        buttonSave = view.findViewById(R.id.buttonSave);
        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
        spinnerColour = view.findViewById(R.id.spinnerColour);
        adapterLanguage = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterLanguage);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                languageSelected = selectedItem;
                Toast.makeText(adapterView.getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterColour = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, colours);
        adapterColour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColour.setAdapter(adapterColour);
        spinnerColour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                colourSelected = selectedItem;
                Toast.makeText(adapterView.getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        buttonSave.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colourSelected.equals("Purple")) {


                } else if (colourSelected.equals("Blue")) {

                } else if (colourSelected.equals("Black")) {

                } else if (colourSelected.equals("Dark Green")) {

                }

                if (languageSelected.equals("English")) {

                } else if (languageSelected.equals("Svenska")) {

                } else if (languageSelected.equals("Norsk")) {

                }
                Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_LONG).show();
            }
        }));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
//        spinnerColour = view.findViewById(R.id.spinnerColour);
//        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerLanguage.setAdapter(adapter);
//        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String selectedItem = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(adapterView.getContext(), "Selected: " + selectedItem, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    private void setNewLocale(AppCompatActivity mContext, @LanguageManager.languageDefiner String language) {
        languageManager.setNewLocale(mContext, language);
        Intent intent = mContext.getIntent();
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
    // En
}