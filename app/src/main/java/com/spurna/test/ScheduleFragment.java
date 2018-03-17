package com.spurna.test;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spurna.core.CoreController;
import com.spurna.core.model.ScheduledTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oriako on 05/03/2018.
 */

public class ScheduleFragment extends Fragment implements View.OnClickListener{

    public static final String SCHEDULE_CACHE_KEY = "CACHED_VALUE:SCHEDULE_CACHE_KEY";
    public static final Integer MAX_BUTTONS = 5;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        List<ScheduledTime> cachedSchedule = (List<ScheduledTime>) CoreController.getInstance().getCache().get(SCHEDULE_CACHE_KEY);

        if (cachedSchedule == null) {
            ArrayList<ScheduledTime> scheduleList = new ArrayList<>();
            CoreController.getInstance().updateCache(SCHEDULE_CACHE_KEY, scheduleList);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.buttonLayout0, new ScheduleAddButtonFragment());
                ft.commit();
            }
        }
    }

    @Override
    public void onClick(View view)
    {
        updateView();
    }

    public void updateView()
    {
        List<ScheduledTime> cachedSchedule = (List<ScheduledTime>) CoreController.getInstance().getCache().get(SCHEDULE_CACHE_KEY);

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null)
        {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null)
            {
                for (int index = 0; index < MAX_BUTTONS; index++)
                {
                    int id = 0;
                    switch (index)
                    {
                        case 0:
                            id = R.id.buttonLayout0;
                            break;
                        case 1:
                            id = R.id.buttonLayout1;
                            break;
                        case 2:
                            id = R.id.buttonLayout2;
                            break;
                        case 3:
                            id = R.id.buttonLayout3;
                            break;
                        case 4:
                            id = R.id.buttonLayout4;
                            break;
                    }

                    if (index < cachedSchedule.size()) {
                        ScheduleTimeFragment timeF = new ScheduleTimeFragment();
                        ScheduledTime scheduledTime = cachedSchedule.get(index);
                        timeF.updateView(scheduledTime);
                        ft.replace(id, timeF);

                    } else {
                        ft.replace(id, new ScheduleAddButtonFragment());
                    }
                    ft.commit();
                }
            }
        }
    }
}
