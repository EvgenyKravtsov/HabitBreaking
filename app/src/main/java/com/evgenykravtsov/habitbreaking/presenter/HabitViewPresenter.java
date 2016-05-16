package com.evgenykravtsov.habitbreaking.presenter;

import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitCounter;
import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitData;
import com.evgenykravtsov.habitbreaking.model.habitlogic.event.LockHabitEvent;
import com.evgenykravtsov.habitbreaking.model.habitlogic.event.TimeToDisplayDeliveredEvent;
import com.evgenykravtsov.habitbreaking.model.mode.ModeType;
import com.evgenykravtsov.habitbreaking.presenter.event.HabitUsageDetectedEvent;
import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.habitstorage.HabitStorageInteractor;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.view.HabitView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Locale;

public class HabitViewPresenter {

    private static final String TAG = HabitViewPresenter.class.getSimpleName();

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;
    private HabitStorageInteractor habitStorageInteractor;

    private HabitView habitView;

    ////

    public HabitViewPresenter(HabitView habitView) {
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();
        habitStorageInteractor = StorageFactory.provideHabitStorageInteractor();
        this.habitView = habitView;
        EventBus.getDefault().register(this);
    }

    ////

    public void unsubscribe() {
        habitView = null;
        EventBus.getDefault().unregister(this);
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

        settingsStorageInteractor.saveSettingAsLong(
                SettingsStorageInteractor.SETTING_KEY_RESTRICTION_EXPIRE_DATE,
                Calendar.getInstance().getTimeInMillis() / 1000 + 40);

        HabitUsageDetectedEvent event = new HabitUsageDetectedEvent();
        event.setModeType(getCurrentMode());
        EventBus.getDefault().post(event);
    }

    ////

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTimeToDisplayDelivered(TimeToDisplayDeliveredEvent event) {
        if (event.getSeconds() < 0) {
            habitView.setHabitButtonStatus(true);
            habitView.setTimeCounterText("00:00:00");
            return;
        }

        long rawSeconds = event.getSeconds();

        int hours = (int) rawSeconds / 3600;
        int minutes = (int) (rawSeconds - hours * 3600) / 60;
        int seconds = (int) rawSeconds - hours * 3600 - minutes * 60;

        String timeString = String.format(Locale.ROOT, "%s:%s:%s",
                hours >= 10 ? hours : "0" + hours,
                minutes >= 10 ? minutes : "0" + minutes,
                seconds >= 10 ? seconds : "0" + seconds);

        habitView.setTimeCounterText(timeString);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLockHabit(LockHabitEvent event) {
        habitView.setHabitButtonStatus(false);
    }
}
