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
import com.spurna.core.util.EnvironmentHelper;
import com.spurna.core.util.SetTime;
import com.spurna.core.util.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oriako on 05/03/2018.
 */

public class ScheduleFragment extends Fragment{

    public static final Integer MAX_BUTTONS = 5;

    private final String timeField = "timeText";
    private final String qtyField = "qtyText";
    private final String addButtonField = "addButton";
    private final String deleteButtonField = "deleteButton";

    private ScheduledTime [] timedArray = new ScheduledTime[MAX_BUTTONS];
    private List<ScheduledTime> scheduleList;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        scheduleList = getScheduledTimeList();
        for (int i = 0; i < scheduleList.size(); i++)
        {
            timedArray[i] = scheduleList.get(i);
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
        final View parentView = this.getView();

        for (int i = 0; i < MAX_BUTTONS; i++)
        {
            try
            {
                final int index = i;

                int timeTextId = getResources().getIdentifier(timeField + String.valueOf(index), "id", "com.spurna.test");
                EditText timeText = (EditText) view.findViewById(timeTextId);
                SetTime fromTime = new SetTime(timeText, timeText.getContext());


                int addButtonId = getResources().getIdentifier(addButtonField + String.valueOf(i), "id", "com.spurna.test");
                Button addButton = (Button) view.findViewById(addButtonId);

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int timeTextId = getResources().getIdentifier(timeField + String.valueOf(index), "id", "com.spurna.test");
                        int qtyTextId = getResources().getIdentifier(qtyField + String.valueOf(index), "id", "com.spurna.test");

                        EditText timeText = (EditText) parentView.findViewById(timeTextId);
                        EditText qtyText = (EditText) parentView.findViewById(qtyTextId);

                        Editable timeStr = timeText.getText();
                        Editable qtyStr = qtyText.getText();

                        try {
                            String[] time = timeStr.toString().split(":");
                            int hour = Integer.parseInt(time[0].trim());
                            int min = Integer.parseInt(time[1].trim());

                            Integer qty = Integer.parseInt(qtyStr.toString());

                            if (time != null && qty != null) {
                                ScheduledTime sdTime = timedArray[index];
                                if (sdTime == null)
                                    sdTime = CoreController.getBO(ScheduledTimeBO.class).getNew();

                                sdTime.setQty(qty);
                                sdTime.setTimeInSec((int) hour * 60 * 60 + min * 60);
                                timedArray[index] = sdTime;
                                sendNewScheduledTime(sdTime);
                            }
                        } catch (Throwable e) {

                        }

                        updateView(parentView);
                    }
                });

                int deleteButtonId = getResources().getIdentifier(deleteButtonField + String.valueOf(i), "id", "com.spurna.test");
                Button deleteButton = (Button) view.findViewById(deleteButtonId);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScheduledTime sdTime = timedArray[index];
                        deleteScheduledTime(sdTime);
                        timedArray[index] = null;
                        updateView(parentView);
                    }
                });
            }
            catch (Throwable e)
            {
                break;
            }
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

            try
            {
                EditText timeText = view.findViewById(timeTextId);
                EditText qtyText = view.findViewById(qtyTextId);

                ScheduledTime time = timedArray[i];
                if (time != null) {
                    int totalSecs = time.getTimeInSec();
                    int hours = totalSecs / 3600;
                    int minutes = (totalSecs % 3600) / 60;

                    timeText.setText(String.format("%02d:%02d", hours, minutes));
                    qtyText.setText(time.getQty().toString());

                    scheduleList.add(time);
                } else {
                    timeText.setText(null);
                    qtyText.setText(null);
                }
            }
            catch (Throwable e)
            {
                break;
            }
        }
    }

    private void sendNewScheduledTime(ScheduledTime timed)
    {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("productId", EnvironmentHelper.getInstance().getProductId());
        params.put("time", timed.getTimeInSec().toString());
        params.put("quantity", timed.getQty().toString());

        Utils.sendGet("scheduleset", params);
    }

    private void deleteScheduledTime(ScheduledTime timed)
    {
        Map<String,String> params = new LinkedHashMap<>();
        params.put("productId", EnvironmentHelper.getInstance().getProductId());
        params.put("time", timed.getTimeInSec().toString());

        Utils.sendGet("scheduleremove", params);
    }

    public List<ScheduledTime> getScheduledTimeList()
    {
        List<ScheduledTime> result = new ArrayList<>();

        Map<String,String> params = new HashMap<>();
        params.put("productId", EnvironmentHelper.getInstance().getProductId());

        try
        {
            String resultStr = Utils.sendGet("product", params);
            JSONArray array = new JSONArray(resultStr);
            if (array.length() > 0)
            {
                for (int index = 0; index < array.length(); index++)
                {
                    ScheduledTime schTime = CoreController.getBO(ScheduledTimeBO.class).getNew();
                    JSONObject subObject = array.getJSONObject(index);

                    if (subObject.has("time") && subObject.has("quantity"))
                    {
                        schTime.setTimeInSec(subObject.getInt("time"));
                        schTime.setQty(subObject.getInt("quantity"));

                        result.add(schTime);
                    }
                }
            }

        }
        catch (Throwable e)
        {

        }

        return result;
    }
}