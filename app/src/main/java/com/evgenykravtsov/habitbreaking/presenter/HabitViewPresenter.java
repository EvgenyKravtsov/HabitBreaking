package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.view.HabitView;

public class HabitViewPresenter {

    private HabitView habitView;

    ////

    public HabitViewPresenter(HabitView habitView) {
        this.habitView = habitView;
    }

    ////

    public void unsubscribe() {
        habitView = null;
    }

    public boolean isUserRegistered() {

        // TODO Implement logic

        return false;
    }

}
