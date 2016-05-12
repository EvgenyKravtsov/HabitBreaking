package com.evgenykravtsov.habitbreaking.model.habitlogic.event;

public class TimeToDisplayDeliveredEvent {

    private long seconds;

    ////

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }
}
