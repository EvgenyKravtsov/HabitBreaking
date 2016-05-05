package com.evgenykravtsov.habitbreaking.view.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.mode.ModeFactory;
import com.evgenykravtsov.habitbreaking.view.adapter.ModeListAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModeSelectionActivity extends AppCompatActivity {

    private static final String TAG = ModeSelectionActivity.class.getSimpleName();

    ////

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.mode_screen_recycler_view) RecyclerView modeListRecyclerView;

    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);
        ButterKnife.bind(this);
        prepareToolbar();
        prepareModeListView();
    }

    ////

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

        ModeListAdapter adapter = new ModeListAdapter(this, ModeFactory.provideModeList(), null);
        modeListRecyclerView.setAdapter(adapter);
    }
}
