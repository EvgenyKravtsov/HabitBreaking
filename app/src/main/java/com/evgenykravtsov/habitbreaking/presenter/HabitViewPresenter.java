package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.MainApp;
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
}
