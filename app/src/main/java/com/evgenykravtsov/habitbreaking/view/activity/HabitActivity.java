package com.evgenykravtsov.habitbreaking.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.habitlogic.HabitService;
import com.evgenykravtsov.habitbreaking.presenter.HabitViewPresenter;
import com.evgenykravtsov.habitbreaking.view.HabitView;
import com.evgenykravtsov.habitbreaking.view.dialog.DialogFactory;
import com.evgenykravtsov.habitbreaking.view.dialog.OneButtonDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HabitActivity extends AppCompatActivity implements HabitView {

    // TODO Fix bug when timer not zero on Leisure mode activation

    private static final String TAG = HabitActivity.class.getSimpleName();

    ////

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.habit_screen_current_mode) TextView currentModeTextView;
    @Bind(R.id.habit_screen_time_zone_label) TextView timeZoneLabelTextView;
    @Bind(R.id.habit_screen_time_counter_label) TextView timeCounterTextView;

    ////

    private HabitViewPresenter presenter;

    ////

    @Override
    public void setTimeCounterText(String timeText) {
        timeCounterTextView.setText(timeText);
    }

    ////

    @OnClick(R.id.habit_screen_habit_button)
    public void onClickHabitButton() {
        presenter.processHabitUsage();
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
        startHabitService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new HabitViewPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
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

    private void unsubscribePresenter() {
        presenter.unsubscribe();
        presenter = null;
    }

    private void prepareToolbar() {
        setSupportActionBar(toolbar);
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

    private void refreshUi() {
        switch (presenter.getCurrentMode()) {
            case LEISURE:
                currentModeTextView.setText(getString(R.string.leisure_mode_title));
                timeZoneLabelTextView.setText(getString(R.string.from_last_usage_label));
                break;
            case CONTROL:
                currentModeTextView.setText(getString(R.string.control_mode_title));
                timeZoneLabelTextView.setText(getString(R.string.from_last_usage_label));
                break;
            case HEALTH:
                currentModeTextView.setText(getString(R.string.health_mode_title));
                timeZoneLabelTextView.setText(getString(R.string.from_last_usage_label));
                break;
        }
    }

    private void startHabitService() {
        if (!HabitService.habitServiceStatus) {
            Intent startHabitService = new Intent(this, HabitService.class);
            startService(startHabitService);
        }
    }
}
