package com.evgenykravtsov.habitbreaking.storage.settingsstorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.evgenykravtsov.habitbreaking.model.MainApp;

public class SettingsStorageSharedPreferencesInteractor implements SettingsStorageInteractor {

    private static final String SETTINGS_SHARED_PREFERENCES = "settings_shared_preferences";

    private SharedPreferences sharedPreferences;

    ////

    public SettingsStorageSharedPreferencesInteractor() {
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

    @Override
    public void saveSettingAsBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public boolean loadSettingAsBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void saveSettingAsLong(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @Override
    public long loadSettingAsLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }
}
