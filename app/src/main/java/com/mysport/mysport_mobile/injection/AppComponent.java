package com.mysport.mysport_mobile.injection;


import android.content.Context;

import com.mysport.mysport_mobile.factories.ModuleFactory;
import com.mysport.mysport_mobile.helpers.ActivityHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getAppContext();

    ActivityHelper getActivityHelper();

    ModuleFactory getModuleFactory();
}
