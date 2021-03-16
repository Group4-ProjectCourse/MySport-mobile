package com.mysport.mysport_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.presenters.ActivityListPresenter;
import com.mysport.mysport_mobile.viewholders.ActivityListViewHolder;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListViewHolder> {

    private final ActivityListPresenter presenter;

    public ActivityListAdapter(@NonNull ActivityListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityListViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_activity_list, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull final ActivityListViewHolder holder, int position) {
        presenter.populate(holder, position);
        holder.itemView.setOnClickListener(
                v -> presenter.clicked(holder.getAdapterPosition()))
        ;
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }
}
