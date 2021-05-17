package com.mysport.mysport_mobile.fragments.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.mocks.SessionMock;
import com.mysport.mysport_mobile.models.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatFragment extends Fragment {

    private FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBtn;
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
        sendBtn = view.findViewById(R.id.chat_button_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = view.findViewById(R.id.chat_send_message_content);
                if (textField.getText().toString().isEmpty())
                    return;
                FirebaseDatabase.getInstance().getReference().push().setValue(
                        new Message(SessionMock.instance.getMember().getEmail(), textField.getText().toString())
                );
                textField.setText("");
            }
        });
        displayAllMessages();

        return view;
    }

    private void displayAllMessages(){
        ListView messages = findViewById(R.id.chat_messages_list);
        FirebaseListOptions.Builder<Message> builder = new FirebaseListOptions.Builder<>();
        builder
                .setLayout(R.layout.chat_list_item)
                .setQuery(FirebaseDatabase.getInstance().getReference(), Message.class)
                .setLifecycleOwner(this.getActivity());
        adapter = new FirebaseListAdapter<Message>(builder.build()) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
                TextView sender, time, content;
                sender = findViewById(R.id.chat_item_msg_sender);
                time = findViewById(R.id.chat_item_msg_time);
                content = findViewById(R.id.chat_item_msg_content);

                sender.setText(model.getSender());
                content.setText(model.getContent());
                time.setText(android.text.format.DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getPublished()));
            }
        };
        messages.setAdapter(adapter);
    }

    public final <T extends View> T findViewById(int id) {
        return this.view.findViewById(id);
    }
}