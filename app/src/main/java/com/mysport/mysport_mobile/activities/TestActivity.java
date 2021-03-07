//package com.mysport.mysport_mobile.activities;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.mysport.mysport_mobile.App;
//import com.mysport.mysport_mobile.R;
//import com.mysport.mysport_mobile.adapters.ActivityListAdapter;
//import com.mysport.mysport_mobile.injection.AppComponent;
//import com.mysport.mysport_mobile.presenters.ActivityListPresenter;
//
//public class TestActivity extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_activity);
//
//        AppComponent component = App.getComponent();
//
//        RecyclerView activityList = findViewById(R.id.activityList);
//        activityList.setLayoutManager(new LinearLayoutManager(this));
//        activityList.setAdapter(new ActivityListAdapter(
//                new ActivityListPresenter(
//                        component.getModuleFactory().getDefaultModuleList(),
//                        component.getActivityHelper()
//                )
//        ));
//    }
//}