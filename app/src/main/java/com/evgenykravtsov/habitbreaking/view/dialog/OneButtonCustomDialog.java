package com.evgenykravtsov.habitbreaking.view.dialog;

import android.app.Activity;
import android.app.Dialog;

public class OneButtonCustomDialog extends Dialog {

    private Activity activity;

    ////

    public OneButtonCustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    ////


}
