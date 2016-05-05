package com.evgenykravtsov.habitbreaking.model.mode;

import android.content.Context;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.MainApp;

import java.util.ArrayList;
import java.util.List;

public class ModeFactory {

    public static List<Mode> provideModeList() {
        Context appContext = MainApp.getAppContext();

        List<Mode> modes = new ArrayList<>();

        Mode leisureMode = new Mode();
        leisureMode.setType(ModeType.LEISURE);
        leisureMode.setImage(appContext.getResources()
                .getDrawable(R.drawable.dummy_item_view_mode_image));
        leisureMode.setTitle(appContext.getString(R.string.leisure_mode_title));
        leisureMode.setDescription(appContext.getString(R.string.leisure_mode_description));
        modes.add(leisureMode);

        Mode controlMode = new Mode();
        controlMode.setType(ModeType.CONTROL);
        controlMode.setImage(appContext.getResources()
                .getDrawable(R.drawable.dummy_item_view_mode_image));
        controlMode.setTitle(appContext.getString(R.string.control_mode_title));
        controlMode.setDescription(appContext.getString(R.string.control_mode_description));
        modes.add(controlMode);

        Mode healthMode = new Mode();
        healthMode.setType(ModeType.HEALTH);
        healthMode.setImage(appContext.getResources()
                .getDrawable(R.drawable.dummy_item_view_mode_image));
        healthMode.setTitle(appContext.getString(R.string.health_mode_title));
        healthMode.setDescription(appContext.getString(R.string.health_mode_description));
        modes.add(healthMode);

        return modes;
    }
}
