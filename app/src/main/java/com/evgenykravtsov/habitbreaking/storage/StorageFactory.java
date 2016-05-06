package com.evgenykravtsov.habitbreaking.storage;

import com.evgenykravtsov.habitbreaking.storage.habitstorage.HabitStorageInteractor;
import com.evgenykravtsov.habitbreaking.storage.habitstorage.HabitStorageSqliteInteractor;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageSharedPreferencesInteractor;

public class StorageFactory {

    public static SettingsStorageInteractor provideSettingsStorageInteractor() {
        return new SettingsStorageSharedPreferencesInteractor();
    }

    public static HabitStorageInteractor provideHabitStorageInteractor() {
        return new HabitStorageSqliteInteractor();
    }
}
