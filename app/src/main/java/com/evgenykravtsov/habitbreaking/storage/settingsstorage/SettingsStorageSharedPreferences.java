package com.evgenykravtsov.habitbreaking.storage.settingsstorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.evgenykravtsov.habitbreaking.model.MainApp;

public class SettingsStorageSharedPreferences implements SettingsStorageInteractor {

    private static final String SETTINGS_SHARED_PREFERENCES = "settings_shared_preferences";

    private SharedPreferences sharedPreferences;

    ////

    public SettingsStorageSharedPreferences() {
        sharedPreferences = MainApp.getAppContext().getSharedPreferences(SETTINGS_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    ////

    @Override
    public void saveSettingAsInteger(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public int loadSettingAsInteger(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
}
