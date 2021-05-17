package com.mysport.mysport_mobile.fragments.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatFragment extends Fragment {

    private FirebaseListAdapter<Message> adapter;
    private View view;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_chat, container, false);
        displayAllMessages();

        return view;
    }

    private void displayAllMessages(){
        ListView messages = findViewById(R.id.chat_messages_list);
        adapter = new FirebaseListAdapter<Message>(this.getActivity(), Message.class, R.layout.chat_list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView sender, time, content;
                sender = findViewById(R.id.chat_item_msg_sender);
                time = findViewById(R.id.chat_item_msg_time);
                content = findViewById(R.id.chat_item_msg_content);

                sender.setText(model.getSender());
                content.setText(model.getContent());
                time.setText(new SimpleDateFormat());
            }
        };
    }

    public final <T extends View> T findViewById(int id) {
        return this.view.findViewById(id);
    }
}