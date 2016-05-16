package com.evgenykravtsov.habitbreaking.storage.settingsstorage;

public interface SettingsStorageInteractor {

    String SETTING_KEY_MODE = "setting_key_mode";
    String SETTING_KEY_HABIT_SERVICE_STATUS = "setting_key_habit_service_status";
    String SETTING_KEY_HABIT_TIMER_STATUS = "setting_key_habit_timer_status";
    String SETTING_KEY_HABIT_TIMER_VALUE = "setting_key_habit_timer_value";
    String SETTING_KEY_HABIT_TIMER_CONTROL_TIMESTAMP = "setting_key_habit_timer_control_timestamp";
    String SETTING_KEY_RESTRICTION_EXPIRE_DATE = "setting_key_restrction_expire_date";

    int SETTING_LEISURE_MODE_VALUE = 0;
    int SETTING_CONTROL_MODE_VALUE = 1;
    int SETTING_HEALTH_MODE_VALUE = 2;

    ////

    void saveSettingAsInteger(String key, int value);

    int loadSettingAsInteger(String key, int defaultValue);

    void saveSettingAsBoolean(String key, boolean value);

    boolean loadSettingAsBoolean(String key, boolean defaultValue);

    void saveSettingAsLong(String key, long value);

    long loadSettingAsLong(String key, long defaultValue);
}
