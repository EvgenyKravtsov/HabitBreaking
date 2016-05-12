package com.evgenykravtsov.habitbreaking.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startHabitService(context);
    }

    ////

    private void startHabitService(Context context) {
        Intent startHabitService = new Intent(context, HabitService.class);
        context.startService(startHabitService);
    }
}
