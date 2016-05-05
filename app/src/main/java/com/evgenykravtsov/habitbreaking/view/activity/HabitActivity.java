package com.evgenykravtsov.habitbreaking.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.presenter.HabitViewPresenter;
import com.evgenykravtsov.habitbreaking.view.HabitView;
import com.evgenykravtsov.habitbreaking.view.dialog.DialogFactory;
import com.evgenykravtsov.habitbreaking.view.dialog.OneButtonDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HabitActivity extends AppCompatActivity implements HabitView {

    private static final String TAG = HabitActivity.class.getSimpleName();

    ////

    @Bind(R.id.toolbar) Toolbar toolbar;

    ////

    private HabitViewPresenter presenter;

    ////

    @OnClick(R.id.habit_screen_habit_button)
    public void onClickHabitButton() {
        // TODO Delete test code
        Log.d(TAG, "Habit button clicked");
    }

    @OnClick(R.id.habit_screen_change_mode_button)
    public void onClickChangeModeButton() {
        Intent startModeSelectionActivity = new Intent(this, ModeSelectionActivity.class);
        startActivity(startModeSelectionActivity);
    }

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        ButterKnife.bind(this);
        prepareToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new HabitViewPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribePresenter();
    }

    ////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_habit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.toolbar_button_restore:
                if (presenter.isUserRegistered()) {
                    prepareStatisticRestoreConfirmationDialog().showDialogWindow();
                } else {
                    prepareRegistrationRequestDialog().showDialogWindow();
                }
                break;
        }
        return true;
    }

    ////

    private void prepareToolbar() {
        setSupportActionBar(toolbar);
    }

    private void unsubscribePresenter() {
        presenter.unsubscribe();
        presenter = null;
    }

    private OneButtonDialog prepareStatisticRestoreConfirmationDialog() {
        OneButtonDialog oneButtonDialog = DialogFactory.provideOneButtonCustomDialog(this);
        oneButtonDialog.setImage(getResources().getDrawable(R.drawable.dummy_custom_dialog_content_image));
        oneButtonDialog.setContentText("Gonna restore ur data, maaan");
        oneButtonDialog.setButtonText("Proceed, bro");
        oneButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Data restore engaged...");
            }
        });
        return oneButtonDialog;
    }

    private OneButtonDialog prepareRegistrationRequestDialog() {
        final OneButtonDialog oneButtonDialog = DialogFactory.provideOneButtonCustomDialog(this);
        oneButtonDialog.setImage(getResources().getDrawable(R.drawable.dummy_custom_dialog_content_image));
        oneButtonDialog.setContentText("Dunno u, maaan");
        oneButtonDialog.setButtonText("Not for long, bro");
        oneButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startRegistrationActivity = new Intent(HabitActivity.this,
                        RegistrationActivity.class);
                startActivity(startRegistrationActivity);
                oneButtonDialog.dismissDialogWindow();
            }
        });
        return oneButtonDialog;
    }
}
