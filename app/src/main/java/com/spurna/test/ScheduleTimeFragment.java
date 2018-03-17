package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spurna.core.model.ScheduledTime;

/**
 * Created by Oriako on 06/03/2018.
 */

public class ScheduleTimeFragment extends Fragment {

    private ScheduledTime scheduledTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_time, container, false);
    }

    public void updateView(ScheduledTime scheduledTime)
    {

    }
}
