package com.evgenykravtsov.habitbreaking.view.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.mode.ModeFactory;
import com.evgenykravtsov.habitbreaking.presenter.ModeSelectionPresenter;
import com.evgenykravtsov.habitbreaking.view.adapter.ModeListAdapter;
import com.evgenykravtsov.habitbreaking.view.event.ModeChosenEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModeSelectionActivity extends AppCompatActivity {

    private static final String TAG = ModeSelectionActivity.class.getSimpleName();

    ////

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.mode_screen_recycler_view) RecyclerView modeListRecyclerView;

    ////

    private ModeSelectionPresenter presenter;
    private ModeListAdapter adapter;

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        prepareToolbar();
        prepareModeListView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new ModeSelectionPresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unsubscribePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    ////

    private void unsubscribePresenter() {
        presenter = null;
    }

    private void prepareToolbar() {
        toolbar.setTitle(getString(R.string.mode_selection_screen_toolbar_title));
        toolbar.setNavigationIcon(R.drawable.navigation_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ModeSelectionActivity.this);
            }
        });
        setSupportActionBar(toolbar);
    }

    private void prepareModeListView() {
        modeListRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        modeListRecyclerView.setLayoutManager(layoutManager);

        adapter = new ModeListAdapter(ModeFactory.provideModeList());
        modeListRecyclerView.setAdapter(adapter);
    }

    ////

    @Subscribe
    public void onModeChosenEvent(ModeChosenEvent event) {
        int mode = 0;
        switch (event.getChosenType()) {
            case LEISURE:
                mode = 0;
                break;
            case CONTROL:
                mode = 1;
                break;
            case HEALTH:
                mode = 2;
                break;
        }

        presenter.saveModeToSettingsStorage(mode);
        adapter.setModes(ModeFactory.provideModeList());
    }
}
