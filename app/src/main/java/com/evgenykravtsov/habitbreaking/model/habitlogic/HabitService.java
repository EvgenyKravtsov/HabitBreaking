package com.evgenykravtsov.habitbreaking.model.habitlogic;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.habitlogic.event.TimeToDisplayDeliveredEvent;
import com.evgenykravtsov.habitbreaking.model.mode.ModeType;
import com.evgenykravtsov.habitbreaking.presenter.event.HabitUsageDetectedEvent;
import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.view.activity.HabitActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HabitService extends Service {

    // TODO Hide service notification while in leisure mode

    // Module dependencies
    private SettingsStorageInteractor settingsStorageInteractor;

    public static boolean habitServiceStatus;

    private static final String TAG = HabitService.class.getSimpleName();
    private static final int SERVICE_ID = 1;

    private HabitTimerRunnable habitTimerRunnable;

    ////

    @Override
    public void onCreate() {
        super.onCreate();
        settingsStorageInteractor = StorageFactory.provideSettingsStorageInteractor();

        // TODO Delete test code
        Log.d(TAG, "Habit service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // TODO Delete test code
        Log.d(TAG, "Habit service started");

        habitServiceStatus = true;
        EventBus.getDefault().register(this);
        showServiceNotification();

        if (settingsStorageInteractor.loadSettingAsBoolean(
                SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_STATUS,
                false)) {
            startHabitTimer(false, getCurrentMode());
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        habitServiceStatus = false;
        EventBus.getDefault().unregister(this);

        // TODO Delete test code
        Log.d(TAG, "Habit service destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    ////

    private void showServiceNotification() {
        Intent notificationIntent = new Intent(this, HabitActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Habit Service Notification") // TODO Add proper text
                .setSmallIcon(R.drawable.dummy_custom_dialog_content_image)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        startForeground(SERVICE_ID, notification);
    }

    private void startHabitTimer(boolean isNew, ModeType modeType) {
        if (habitTimerRunnable != null) {
            habitTimerRunnable.threadStatus = false;
        }
        habitTimerRunnable = new HabitTimerRunnable(isNew, modeType);
        new Thread(habitTimerRunnable).start();
    }

    // TODO Delete test method
    private long getRestrictionTimeInterval() {
        return 100000;
    }

    private ModeType getCurrentMode() {
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

    ////

    @Subscribe
    public void onHabitUsageDetectedEvent(HabitUsageDetectedEvent event) {
        settingsStorageInteractor.saveSettingAsBoolean(
                SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_STATUS,
                true);
        startHabitTimer(true, event.getModeType());
    }

    ////

    class HabitTimerRunnable implements Runnable {

        static final int TIMER_INCREMENT = 1; // seconds

        ModeType modeType;
        boolean threadStatus;
        long seconds;

        ////

        public HabitTimerRunnable(boolean isNew, ModeType modeType) {
            this.modeType = modeType;
            threadStatus = true;

            if (isNew) {
                seconds = 0;
            } else {
                seconds = settingsStorageInteractor.loadSettingAsLong(
                        SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_VALUE,
                        0);
            }
        }

        ////

        @Override
        public void run() {
            while (threadStatus) {
                switch (modeType) {
                    case LEISURE:
                        leisureTimerProcedure();
                        break;
                    case CONTROL:
                        restrictionTimerProcedure();
                        break;
                    case HEALTH:
                        restrictionTimerProcedure();
                        break;
                }
            }
        }

        ////

        private void leisureTimerProcedure() {
            try {
                TimeUnit.SECONDS.sleep(TIMER_INCREMENT);

                long controlTimestamp = settingsStorageInteractor.loadSettingAsLong(
                        SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_CONTROL_TIMESTAMP,
                        Calendar.getInstance().getTimeInMillis() / 1000);
                long currentTimestamp = Calendar.getInstance().getTimeInMillis() / 1000;

                seconds += currentTimestamp - controlTimestamp;
                settingsStorageInteractor.saveSettingAsLong(
                        SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_CONTROL_TIMESTAMP,
                        currentTimestamp);
                settingsStorageInteractor.saveSettingAsLong(
                        SettingsStorageInteractor.SETTING_KEY_HABIT_TIMER_VALUE,
                        seconds);

                TimeToDisplayDeliveredEvent event = new TimeToDisplayDeliveredEvent();
                event.setSeconds(seconds);
                EventBus.getDefault().post(event);

            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        private void restrictionTimerProcedure() {
            try {
                TimeUnit.SECONDS.sleep(TIMER_INCREMENT);

                // TODO Delete test code
                Log.d(TAG, "RESTRICED");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
