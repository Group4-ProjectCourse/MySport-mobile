package com.mysport.mysport_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.SportsViewHolder> {

    private final List<SportsModel> sportsModelList;
    private final Context context;
    private OnRecyclerItemClick onRecyclerItemClick;

    public SportsAdapter(List<SportsModel> sportsModelList, Context context) {
        this.sportsModelList = sportsModelList;
        this.context = context;
    }

    interface OnRecyclerItemClick {
        void onClick(int pos);
    }

    public void setOnRecyclerItemClick(OnRecyclerItemClick onRecyclerItemClick){
        this.onRecyclerItemClick = onRecyclerItemClick;
    }

    @NonNull
    @Override
    public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportsViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(
                    R.layout.item_sport,
                    parent,
                    false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
        holder.sportNameText.setText(sportsModelList.get(position).getSportName());
        holder.participantsText.setText(sportsModelList.get(position).getParticipants());
        holder.timeText.setText(sportsModelList.get(position).getTime());

        switch (position){
            case 1:
                holder.view.setBackgroundColor(context.getColor(R.color.color2));
                break;
            case 2:
                holder.view.setBackgroundColor(context.getColor(R.color.color3));
                break;
            case 3:
                holder.view.setBackgroundColor(context.getColor(R.color.color4));
                break;
            case 4:
                holder.view.setBackgroundColor(context.getColor(R.color.color5));
                break;
            default:
                holder.view.setBackgroundColor(context.getColor(R.color.color1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sportsModelList.size();
    }

    public class SportsViewHolder extends RecyclerView.ViewHolder {
        private TextView sportNameText, timeText, participantsText;
        private View view;

        public SportsViewHolder(@NonNull View itemView) {
            super(itemView);
            sportNameText = itemView.findViewById(R.id.sport_name1);
            timeText = itemView.findViewById(R.id.time1);
            participantsText = itemView.findViewById(R.id.participants1);
            view = itemView.findViewById(R.id.view1);

            itemView.setOnClickListener(v -> {
                if(onRecyclerItemClick != null) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        onRecyclerItemClick.onClick(pos);
                    }
                }
            });
        }
    }
}
