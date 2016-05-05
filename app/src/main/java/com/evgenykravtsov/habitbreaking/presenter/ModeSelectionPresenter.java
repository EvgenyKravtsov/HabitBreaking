package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;

public class ModeSelectionPresenter {

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;

    ////

    public ModeSelectionPresenter() {
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();
    }

    ////

    public void saveModeToSettingsStorage(int mode) {
        settingsStorageInteractor.saveSettingAsInteger(
                SettingsStorageInteractor.SETTING_KEY_MODE, mode);
    }
}
