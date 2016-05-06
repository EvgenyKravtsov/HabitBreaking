package com.evgenykravtsov.habitbreaking.storage.habitstorage;

import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitData;

public interface HabitStorageInteractor {

    int MAX_RECORDS = 2;

    ////

    void saveHabitData(HabitData habitData);
}
