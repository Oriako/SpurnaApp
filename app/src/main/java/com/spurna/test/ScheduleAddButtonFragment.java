package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.spurna.core.CoreController;
import com.spurna.core.business.ScheduledTimeBO;
import com.spurna.core.business.UserBO;
import com.spurna.core.model.ScheduledTime;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Oriako on 06/03/2018.
 */

public class ScheduleAddButtonFragment extends Fragment {

    Button addButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_button, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        addButton = view.findViewById(R.id.addButton);
        if (addButton != null)
        {
            addButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    ScheduledTime sdTime = CoreController.getBO(ScheduledTimeBO.class).getNew();
                    ArrayList<ScheduledTime> timeList = (ArrayList<ScheduledTime>) CoreController.getInstance().getCache().get(ScheduleFragment.SCHEDULE_CACHE_KEY);
                    if (timeList != null)
                    {
                        timeList.add(sdTime);
                    }
                }
            });
        }
    }
}
