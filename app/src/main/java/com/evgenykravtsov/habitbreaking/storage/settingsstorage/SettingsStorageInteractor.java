package com.evgenykravtsov.habitbreaking.storage.settingsstorage;

public interface SettingsStorageInteractor {

    String SETTING_KEY_MODE = "setting_key_mode";

    int SETTING_LEISURE_MODE_VALUE = 0;
    int SETTING_CONTROL_MODE_VALUE = 1;
    int SETTING_HEALTH_MODE_VALUE = 2;

    ////

    void saveSettingAsInteger(String key, int value);

    int loadSettingAsInteger(String key, int defaultValue);
}
