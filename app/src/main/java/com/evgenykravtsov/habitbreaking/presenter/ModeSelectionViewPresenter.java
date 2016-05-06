package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;

public class ModeSelectionViewPresenter {

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;

    ////

    public ModeSelectionViewPresenter() {
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();
    }

    ////

    public void saveModeToSettingsStorage(int mode) {
        settingsStorageInteractor.saveSettingAsInteger(
                SettingsStorageInteractor.SETTING_KEY_MODE, mode);
    }
}
