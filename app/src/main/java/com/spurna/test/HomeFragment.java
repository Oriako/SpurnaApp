package com.spurna.test;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.spurna.core.CoreController;
import com.spurna.core.business.ScheduledTimeBO;
import com.spurna.core.model.ScheduledTime;
import com.spurna.core.util.Utils;

/**
 * Created by Oriako on 05/03/2018.
 */

public class HomeFragment extends Fragment {

    private Button linkDeviceButton;
    private Button statusButton;
    private Button scheduleButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.spurna_fragment, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        linkDeviceButton = view.findViewById(R.id.buttonLink);
        linkDeviceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               Utils.pushFragment(getActivity(), R.id.rootLayout, new ConfigurationFragment());
            }
        });

        statusButton = view.findViewById(R.id.buttonCheck);
        statusButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.pushFragment(getActivity(), R.id.rootLayout, new StatusFragment());
            }
        });

        scheduleButton = view.findViewById(R.id.buttonSchedule);
        scheduleButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Utils.pushFragment(getActivity(), R.id.rootLayout, new ScheduleFragment());
            }
        });
    }

}
