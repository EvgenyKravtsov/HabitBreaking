package com.evgenykravtsov.habitbreaking.view.event;

import com.evgenykravtsov.habitbreaking.model.mode.ModeType;

public class ModeChosenEvent {

    private ModeType chosenType;

    ////

    public ModeType getChosenType() {
        return chosenType;
    }

    public void setChosenType(ModeType chosenType) {
        this.chosenType = chosenType;
    }
}
