package com.mysport.mysport_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.ForumPost;

import java.util.List;

public class ForumPostAdapter extends RecyclerView.Adapter<ForumPostAdapter.ForumPostViewHolder> {

    private List<ForumPost> postList;
    private Context context;
    private OnRecyclerItemClick onRecyclerItemClick;

    public ForumPostAdapter() { }

    public ForumPostAdapter(List<ForumPost> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public ForumPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ForumPostViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.forum_post_items, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ForumPostViewHolder holder, int position) {
        holder.authorText.setText(postList.get(position).getAuthor());
        holder.titleText.setText(postList.get(position).getTitle());
        holder.createdTime.setText(postList.get(position).getCreatedAt());
        holder.contentText.setText(postList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    public class ForumPostViewHolder extends RecyclerView.ViewHolder {

        private TextView authorText, titleText, createdTime, contentText;

        public ForumPostViewHolder(@NonNull View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.forum_author);
            createdTime = itemView.findViewById(R.id.forum_created_time);
            titleText = itemView.findViewById(R.id.forum_title);
            contentText = itemView.findViewById(R.id.forum_content_short);

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

    public void setPostList(List<ForumPost> postList) {
        this.postList = postList;
    }

    public interface OnRecyclerItemClick {
        void onClick(int position);
    }
}
