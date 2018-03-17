package com.spurna.core.business;

import com.spurna.core.model.ScheduledTime;

/**
 * Created by Oriako on 06/03/2018.
 */

public class ScheduledTimeBO extends GenericBO {

    public ScheduledTime getNew()
    {
        return new ScheduledTime();
    }
}
