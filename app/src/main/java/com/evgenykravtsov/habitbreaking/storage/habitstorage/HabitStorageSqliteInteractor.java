package com.evgenykravtsov.habitbreaking.storage.habitstorage;

import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitData;
import com.evgenykravtsov.habitbreaking.storage.AppSqliteDatabase;

public class HabitStorageSqliteInteractor implements HabitStorageInteractor {

    private static final String TAG = HabitStorageSqliteInteractor.class.getSimpleName();

    // Module dependencies
    private AppSqliteDatabase appSqliteDatabase;

    ////

    public HabitStorageSqliteInteractor() {
        appSqliteDatabase = AppSqliteDatabase.getInstance();
    }

    ////

    @Override
    public void saveHabitData(final HabitData habitData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long day = habitData.getDay();

                boolean isUniqueDay = appSqliteDatabase.getHabitCountByDay(day) == 0;

                if (isUniqueDay) {
                    if (appSqliteDatabase.getRowCountFromHabitTable() ==
                            HabitStorageInteractor.MAX_RECORDS) {
                        appSqliteDatabase.deleteFirstRowFromHabitTable();
                    }
                    appSqliteDatabase.writeNewHabitDay(day);
                } else {
                    appSqliteDatabase.updateHabitCountByDay(day);
                }
            }
        }).start();
    }
}
