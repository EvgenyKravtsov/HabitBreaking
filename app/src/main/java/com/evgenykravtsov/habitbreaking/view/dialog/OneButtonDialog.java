package com.evgenykravtsov.habitbreaking.view.dialog;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface OneButtonDialog {

    void showDialogWindow();

    void setImage(Drawable dialogImage);

    void setContentText(String contentText);

    void setButtonText(String buttonText);

    void setOnClickListener(View.OnClickListener onClickListener);

    void dismissDialogWindow();
}
