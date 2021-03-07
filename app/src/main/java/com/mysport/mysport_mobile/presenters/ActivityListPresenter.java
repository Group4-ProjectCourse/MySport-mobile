package com.mysport.mysport_mobile.presenters;

import androidx.annotation.NonNull;

import com.mysport.mysport_mobile.viewholders.ActivityListViewHolder;
import com.mysport.mysport_mobile.helpers.ActivityHelper;
import com.mysport.mysport_mobile.interfaces.ActivityListViewInterface;
import com.mysport.mysport_mobile.models.Module;

import java.util.List;

public class ActivityListPresenter {
    private List<Module> moduleList;
    private ActivityHelper activityHelper;

    public ActivityListPresenter(@NonNull List<Module> moduleList, @NonNull ActivityHelper activityHelper) {
        this.moduleList = moduleList;
        this.activityHelper = activityHelper;
    }

    public void populate(ActivityListViewHolder viewInterface, int position) {
        Module module = moduleList.get(position);
        viewInterface.setName(module.getName());
        viewInterface.setDescription(module.getDescription());
    }

    public void clicked(int position) {
        activityHelper.startActivity(moduleList.get(position).getModuleClass());
    }

    public int getItemCount() {
        return moduleList.size();
    }
}
