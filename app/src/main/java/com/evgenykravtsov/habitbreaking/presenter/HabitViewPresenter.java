package com.evgenykravtsov.habitbreaking.presenter;

import android.content.Context;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.MainApp;
import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.view.HabitView;

public class HabitViewPresenter {

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;

    private HabitView habitView;

    ////

    public HabitViewPresenter(HabitView habitView) {
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();

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

    public String getCurrentModeTitle() {
        int currentMode = settingsStorageInteractor.loadSettingAsInteger(
                SettingsStorageInteractor.SETTING_KEY_MODE,
                SettingsStorageInteractor.SETTING_LEISURE_MODE_VALUE);

        Context appContext = MainApp.getAppContext();

        switch (currentMode) {
            case SettingsStorageInteractor.SETTING_LEISURE_MODE_VALUE:
                return appContext.getString(R.string.leisure_mode_title);
            case SettingsStorageInteractor.SETTING_CONTROL_MODE_VALUE:
                return appContext.getString(R.string.control_mode_title);
            case SettingsStorageInteractor.SETTING_HEALTH_MODE_VALUE:
                return appContext.getString(R.string.health_mode_title);
            default:
                return appContext.getString(R.string.leisure_mode_title);
        }
    }
}
