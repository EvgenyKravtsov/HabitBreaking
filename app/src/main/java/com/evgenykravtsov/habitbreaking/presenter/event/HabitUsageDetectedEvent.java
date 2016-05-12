package com.evgenykravtsov.habitbreaking.presenter.event;

import com.evgenykravtsov.habitbreaking.model.mode.ModeType;

public class HabitUsageDetectedEvent {

    private ModeType modeType;

    ////

    public ModeType getModeType() {
        return modeType;
    }

    public void setModeType(ModeType modeType) {
        this.modeType = modeType;
    }
}
