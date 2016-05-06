package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitCounter;
import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitData;
import com.evgenykravtsov.habitbreaking.model.mode.ModeType;
import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.habitstorage.HabitStorageInteractor;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.view.HabitView;

public class HabitViewPresenter {

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;
    private HabitStorageInteractor habitStorageInteractor;

    private HabitView habitView;

    ////

    public HabitViewPresenter(HabitView habitView) {
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();
        habitStorageInteractor = StorageFactory.provideHabitStorageInteractor();

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

    public ModeType getCurrentMode() {
        int currentMode = settingsStorageInteractor.loadSettingAsInteger(
                SettingsStorageInteractor.SETTING_KEY_MODE,
                SettingsStorageInteractor.SETTING_LEISURE_MODE_VALUE);

        switch (currentMode) {
            case SettingsStorageInteractor.SETTING_LEISURE_MODE_VALUE:
                return ModeType.LEISURE;
            case SettingsStorageInteractor.SETTING_CONTROL_MODE_VALUE:
                return ModeType.CONTROL;
            case SettingsStorageInteractor.SETTING_HEALTH_MODE_VALUE:
                return ModeType.HEALTH;
            default:
                return ModeType.LEISURE;
        }
    }

    public void processHabitUsage() {
        HabitData habitData = HabitCounter.countHabit();
        habitStorageInteractor.saveHabitData(habitData);
    }
}
