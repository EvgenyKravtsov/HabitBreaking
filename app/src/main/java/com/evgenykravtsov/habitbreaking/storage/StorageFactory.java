package com.evgenykravtsov.habitbreaking.storage;

import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageSharedPreferences;

public class StorageFactory {

    public static SettingsStorageInteractor provideSettingsStorageInteractor() {
        return new SettingsStorageSharedPreferences();
    }
}
