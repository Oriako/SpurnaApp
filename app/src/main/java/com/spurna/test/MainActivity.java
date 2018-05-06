package com.spurna.test;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.spurna.core.CoreController;
import com.spurna.core.business.ScheduledTimeBO;
import com.spurna.core.model.ScheduledTime;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button configButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigationView();
        configButton = (Button) findViewById(R.id.configButton);
        configButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pushFragment(new ConfigurationFragment());
            }
        });
    }

    private void setupNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mTextMessage = (TextView) findViewById(R.id.textView);
        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(1));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }


    }

    protected void selectFragment(MenuItem item) {

        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.action_home:
                if (configButton != null)
                    configButton.setVisibility(View.VISIBLE);
                mTextMessage.setText(R.string.title_home);
                //mTextMessage.setVisibility(View.INVISIBLE);
                pushFragment(new HomeFragment());
                break;

            case R.id.action_schedule:
                if (configButton != null)
                    configButton.setVisibility(View.INVISIBLE);
                mTextMessage.setText(R.string.title_schedule);
                mTextMessage.setVisibility(View.VISIBLE);
                pushFragment(new ScheduleFragment());
                break;

            case R.id.action_status:
                if (configButton != null)
                    configButton.setVisibility(View.INVISIBLE);
                mTextMessage.setText(R.string.title_status);
                mTextMessage.setVisibility(View.VISIBLE);
                pushFragment(new StatusFragment());
                break;

        }
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }
}
