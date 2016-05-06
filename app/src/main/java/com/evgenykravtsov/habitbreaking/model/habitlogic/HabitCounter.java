package com.evgenykravtsov.habitbreaking.model.habitlogic;

import java.util.Calendar;

public class HabitCounter {

    private static final String TAG = HabitCounter.class.getSimpleName();

    ////

    public static HabitData countHabit() {
        Calendar habitDate = Calendar.getInstance();

        habitDate.set(Calendar.HOUR_OF_DAY, 0);
        habitDate.set(Calendar.MINUTE, 0);
        habitDate.set(Calendar.SECOND, 0);
        habitDate.set(Calendar.MILLISECOND, 0);

        HabitData habitData = new HabitData();
        habitData.setDay(habitDate.getTimeInMillis() / 1000);
        return habitData;
    }
}
