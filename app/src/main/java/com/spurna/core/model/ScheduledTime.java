package com.spurna.core.model;

/**
 * Created by Oriako on 06/03/2018.
 */

public class ScheduledTime {

    private Integer timeInSec;
    private Integer qty;


    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }
    public Integer getTimeInSec() {
        return timeInSec;
    }
    public void setTimeInSec(Integer timeInSec) {
        this.timeInSec = timeInSec;
    }
}
