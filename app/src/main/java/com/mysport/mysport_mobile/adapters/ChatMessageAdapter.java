package com.mysport.mysport_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.ChatMessage;
import com.mysport.mysport_mobile.models.ForumPost;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder> {

    private List<ChatMessage> messageList;
    private Context context;
    private OnRecyclerItemClick onRecyclerItemClick;

    public ChatMessageAdapter() { }

    public ChatMessageAdapter(List<ChatMessage> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatMessageViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.chat_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        holder.senderText.setText(messageList.get(position).getSender());
        holder.timeText.setText(messageList.get(position).getTimeClean());
        holder.contentText.setText(messageList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    public class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        private final TextView senderText;
        private final TextView timeText;
        private final TextView contentText;

        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            senderText = itemView.findViewById(R.id.chat_item_msg_sender);
            timeText = itemView.findViewById(R.id.chat_item_msg_time);
            contentText = itemView.findViewById(R.id.chat_item_msg_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecyclerItemClick != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION)
                            onRecyclerItemClick.onClick(pos);
                    }
                }
            });
        }
    }

    public void setPostList(List<ChatMessage> postList) {
        this.messageList = postList;
    }

    public interface OnRecyclerItemClick {
        void onClick(int position);
    }
}
