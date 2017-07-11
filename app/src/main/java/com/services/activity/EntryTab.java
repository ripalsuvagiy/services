package com.services.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.services.R;
import com.services.adapter.PagerAdapter;
import com.services.fragment.FireExtinguishersDetails;
import com.services.fragment.PersonalDetail;

public class EntryTab extends AppCompatActivity {
    public static AppCompatActivity activityTabs;
    public static LinearLayout linearTab1,linearTab2;
    public static FrameLayout frameLayout;
    public static FragmentManager manager;
    public  static RelativeLayout relativeView1,relativeView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_tab);
        manager=getSupportFragmentManager();
        activityTabs=EntryTab.this;
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        linearTab1=(LinearLayout)findViewById(R.id.linear_tab1);
        linearTab2=(LinearLayout)findViewById(R.id.linear_tab2);
        relativeView1=(RelativeLayout)findViewById(R.id.relative_view1);
        relativeView2=(RelativeLayout)findViewById(R.id.relative_view2);

        changeFragment(new PersonalDetail());
        relativeView2.setVisibility(View.GONE);
        relativeView1.setVisibility(View.VISIBLE);
        linearTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new FireExtinguishersDetails());
                relativeView1.setVisibility(View.GONE);
                relativeView2.setVisibility(View.VISIBLE);
            }
        });

        linearTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new PersonalDetail());
                relativeView2.setVisibility(View.GONE);
                relativeView1.setVisibility(View.VISIBLE);
            }
        });


    }

    public static void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
//            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transaction.addToBackStack(null);
        // transaction.commit();
        transaction.commitAllowingStateLoss();
        //activity.getSupportFragmentManager().executePendingTransactions();
    }
}