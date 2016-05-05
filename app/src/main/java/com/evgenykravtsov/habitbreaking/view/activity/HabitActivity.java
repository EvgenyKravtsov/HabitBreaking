package com.evgenykravtsov.habitbreaking.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.presenter.HabitViewPresenter;
import com.evgenykravtsov.habitbreaking.view.HabitView;

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
        // TODO Delete test code
        Log.d(TAG, "Change mode button clicked");
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
                // TODO Delete test code
                Log.d(TAG, "Restore button clicked");
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
}
