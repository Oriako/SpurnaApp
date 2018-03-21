package com.spurna.test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.spurna.core.CoreController;
import com.spurna.core.business.ScheduledTimeBO;
import com.spurna.core.model.ScheduledTime;
import com.spurna.core.util.SetTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Oriako on 05/03/2018.
 */

public class ScheduleFragment extends Fragment{

    public static final String SCHEDULE_CACHE_KEY = "CACHED_VALUE:SCHEDULE_CACHE_KEY";
    public static final Integer MAX_BUTTONS = 5;

    private final String timeField = "timeText";
    private final String qtyField = "qtyText";
    private final String addButtonField = "addButton";
    private final String deleteButtonField = "deleteButton";

    private ScheduledTime [] timedArray = new ScheduledTime[MAX_BUTTONS];

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
        else
        {
            for (int i = 0; i < cachedSchedule.size(); i++)
            {
                timedArray[i] = cachedSchedule.get(i);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        final View parentView = this.getView();

        for (int i = 0; i < MAX_BUTTONS; i++)
        {
            final int index = i;

            int timeTextId = getResources().getIdentifier(timeField + String.valueOf(index), "id", "com.spurna.test");
            EditText timeText = (EditText) view.findViewById(timeTextId);
            SetTime fromTime = new SetTime(timeText, timeText.getContext());


            int addButtonId = getResources().getIdentifier(addButtonField + String.valueOf(i), "id", "com.spurna.test");
            Button addButton = (Button) view.findViewById(addButtonId);

            addButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int timeTextId = getResources().getIdentifier(timeField + String.valueOf(index), "id", "com.spurna.test");
                    int qtyTextId = getResources().getIdentifier(qtyField + String.valueOf(index), "id", "com.spurna.test");

                    EditText timeText = (EditText) parentView.findViewById(timeTextId);
                    EditText qtyText = (EditText) parentView.findViewById(qtyTextId);

                    Editable timeStr = timeText.getText();
                    Editable qtyStr = qtyText.getText();

                    try {
                        String[] time = timeStr.toString().split ( ":" );
                        int hour = Integer.parseInt ( time[0].trim() );
                        int min = Integer.parseInt ( time[1].trim() );

                        Integer qty = Integer.parseInt(qtyStr.toString());

                        if (time != null && qty != null)
                        {
                            ScheduledTime sdTime = timedArray[index];
                            if (sdTime == null)
                                sdTime = CoreController.getBO(ScheduledTimeBO.class).getNew();

                            sdTime.setQty(qty);
                            sdTime.setTimeInSec((int) hour*60*60 + min*60);
                            timedArray[index] = sdTime;
                        }
                    }
                    catch (Throwable e)
                    {

                    }

                    updateView(parentView);
                }
            });

            int deleteButtonId = getResources().getIdentifier(deleteButtonField + String.valueOf(i), "id", "com.spurna.test");
            Button deleteButton = (Button) view.findViewById(deleteButtonId);
            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    timedArray[index] = null;
                    updateView(parentView);
            }
            });
        }

        updateView(view);
    }

    public void updateView(View view)
    {
        ArrayList<ScheduledTime> scheduleList = new ArrayList<>();

        for (int i = 0; i < MAX_BUTTONS; i++)
        {
            int timeTextId = getResources().getIdentifier(timeField + String.valueOf(i), "id", "com.spurna.test");
            int qtyTextId = getResources().getIdentifier(qtyField + String.valueOf(i), "id", "com.spurna.test");
            EditText timeText = view.findViewById(timeTextId);
            EditText qtyText = view.findViewById(qtyTextId);

            ScheduledTime time = timedArray[i];
            if (time != null)
            {
                int totalSecs = time.getTimeInSec();
                int hours = totalSecs / 3600;
                int minutes = (totalSecs % 3600) / 60;

                timeText.setText(String.format("%02d:%02d", hours, minutes));
                qtyText.setText(time.getQty().toString());

                scheduleList.add(time);
            }
            else
            {
                timeText.setText(null);
                qtyText.setText(null);
            }
        }

        CoreController.getInstance().updateCache(SCHEDULE_CACHE_KEY, scheduleList);
    }
}
