package com.evgenykravtsov.habitbreaking.view.dialog;

import android.app.Activity;

public class DialogFactory {

    public static OneButtonDialog provideOneButtonCustomDialog(Activity activity) {
        return new OneButtonCustomDialog(activity);
    }
}
