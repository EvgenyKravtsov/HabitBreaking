package com.evgenykravtsov.habitbreaking.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evgenykravtsov.habitbreaking.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OneButtonCustomDialog extends Dialog implements OneButtonDialog {

    private Drawable dialogImage;
    private String contentText;
    private String buttonText;
    private View.OnClickListener onClickListener;

    ////

    @Bind(R.id.one_button_custom_dialog_image) ImageView dialogImageView;
    @Bind(R.id.one_button_custom_dialog_content_text) TextView contentTextView;
    @Bind(R.id.one_button_custom_dialog_button) Button button;

    ////

    public OneButtonCustomDialog(Activity activity) {
        super(activity);
    }

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_one_button_custom);
        ButterKnife.bind(this);
        prepareViews();
    }

    ////

    @Override
    public void showDialogWindow() {
        this.show();
    }

    @Override
    public void setImage(Drawable dialogImage) {
        this.dialogImage = dialogImage;
    }

    @Override
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Override
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void dismissDialogWindow() {
        this.dismiss();
    }

    ////

    private void prepareViews() {
        dialogImageView.setImageDrawable(dialogImage);
        contentTextView.setText(contentText);
        button.setText(buttonText);
        button.setOnClickListener(onClickListener);
    }
}
