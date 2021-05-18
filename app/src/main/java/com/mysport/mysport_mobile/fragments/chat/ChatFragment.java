package com.mysport.mysport_mobile.fragments.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.adapters.ChatMessageAdapter;
import com.mysport.mysport_mobile.adapters.ForumPostAdapter;
import com.mysport.mysport_mobile.models.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements ChatMessageAdapter.OnRecyclerItemClick {

    public RequestQueue queue;
    private RecyclerView recyclerView;
    private ChatMessageAdapter messageAdapter;
    private ArrayList<ChatMessage> messageList;
    private FloatingActionButton sendBtn;
    private TextInputEditText userInput;
    private View view;

    public ChatFragment() {

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
        queue = Volley.newRequestQueue(getContext());
        sendBtn = findViewById(R.id.chat_button_send);
        recyclerView = findViewById(R.id.chat_messages_recyclerview);
        messageList = new ArrayList<>(15);
        //forumPostAdapter = new ForumPostAdapter(forumPostList, this);
        messageAdapter = new ChatMessageAdapter();
        messageAdapter.setOnRecyclerItemClick(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userInput = findViewById(R.id.chat_send_message_content);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userInput.getText().toString().isEmpty())
                    return;
                sendMessage();
                userInput.setText("");
                loadMessages();
            }
        });
        loadMessages();
        //displayAllMessages();

        return view;
    }

    //Firebase does not like me, I can use nodejs no problem
//    private void displayAllMessages(){
//        ListView messages = findViewById(R.id.chat_messages_list);
//        FirebaseListOptions.Builder<Message> builder = new FirebaseListOptions.Builder<>();
//        builder
//                .setLayout(R.layout.chat_list_item)
//                .setQuery(FirebaseDatabase.getInstance().getReference(), Message.class)
//                .setLifecycleOwner(this.getActivity());
//        adapter = new FirebaseListAdapter<Message>(builder.build()) {
//            @Override
//            protected void populateView(@NonNull View v, @NonNull Message model, int position) {
//                TextView sender, time, content;
//                sender = findViewById(R.id.chat_item_msg_sender);
//                time = findViewById(R.id.chat_item_msg_time);
//                content = findViewById(R.id.chat_item_msg_content);
//
//                sender.setText(model.getSender());
//                content.setText(model.getContent());
//                time.setText(android.text.format.DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getPublished()));
//            }
//        };
//        messages.setAdapter(adapter);
//    }

    public final <T extends View> T findViewById(int id) {
        return this.view.findViewById(id);
    }

    public void loadMessages(){
        //make url
        String url = App.baseURL + "messaging";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), response -> {
            for(int i = 0; i < response.length(); i++) {
                try {
                    String id = response.getJSONObject(i).getString("_id");
                    String sender = response.getJSONObject(i).getString("sender");
                    String content = response.getJSONObject(i).getString("content");
                    String time = response.getJSONObject(i).getString("time");

                    ChatMessage chatMessage = new ChatMessage(id, sender, content, Long.parseLong(time));
                    messageList.add(chatMessage);
                } catch(JSONException e) {
                    Log.e("Error", "Caught JSON exception: " + e);
                }
            }
            messageAdapter.setPostList(messageList);
            recyclerView.setAdapter(messageAdapter);
        }, error -> Log.e("Error", "Volley error getting messages: " + error));

        queue.add(request);
    }

    public void sendMessage(){
        String content = userInput.getText().toString();
        String sender = App.getSession().getUser().getEmail();
        long timeStamp = System.currentTimeMillis();
        //make url
        String url = App.baseURL + "messaging";

        //create Volley queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //create JSON object
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("sender", sender);
            jsonBody.put("content", content);
            jsonBody.put("time", Long.toString(timeStamp));
        } catch(JSONException e) {
            Log.e("Error", "Error creating JSON object: " + e);
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody, response -> {
            try {
                String responseStatus = response.getString("status");
                Log.d("responseStatus", responseStatus);
                if(true || responseStatus.equals("200") || responseStatus.equals("OK")){

                    Log.d("Debug", "Message successfully submitted.");

                    Toast success = Toast.makeText(getContext(), R.string.create_post_success, Toast.LENGTH_SHORT);
                    success.setGravity(Gravity.BOTTOM, 0, 50);
                    success.show();
                } else {
                    Log.e("Error", "Error submitting message.");
                }
            } catch (JSONException e) {
                Log.e("Error", "Error grabbing status from response: " + e);
            }
        }, error -> Log.e("Error", "Volley error submitting message: " + error));
        //submit request by adding to queue
        queue.add(request);
    }

    @Override
    public void onClick(int position) {
        Toast toast = Toast.makeText(getContext(), position + " clicked!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.show();
    }
}