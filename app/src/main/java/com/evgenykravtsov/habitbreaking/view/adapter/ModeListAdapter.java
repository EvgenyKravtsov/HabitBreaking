package com.evgenykravtsov.habitbreaking.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evgenykravtsov.habitbreaking.R;
import com.evgenykravtsov.habitbreaking.model.MainApp;
import com.evgenykravtsov.habitbreaking.model.mode.Mode;
import com.evgenykravtsov.habitbreaking.model.mode.ModeType;
import com.evgenykravtsov.habitbreaking.storage.StorageFactory;
import com.evgenykravtsov.habitbreaking.storage.settingsstorage.SettingsStorageInteractor;
import com.evgenykravtsov.habitbreaking.view.event.ModeChosenEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ModeListAdapter extends RecyclerView.Adapter<ModeListAdapter.ViewHolder>
        implements RecyclerView.OnItemTouchListener {

    public interface OnItemClickListener {

        void onItemClick();
    }

    ////

    private static final String TAG = ModeListAdapter.class.getSimpleName();

    private List<Mode> modes;

    ////

    public ModeListAdapter(List<Mode> modes) {
        this.modes = modes;
    }

    public void setModes(List<Mode> modes) {
        this.modes = modes;
        notifyDataSetChanged();
    }

    ////

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_mode, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Mode mode = modes.get(position);
        holder.imageView.setImageDrawable(mode.getImage());
        holder.titleTextView.setText(mode.getTitle());
        holder.descriptionTextView.setText(mode.getDescription());

        // TODO Delete test code
        if (mode.getActivationStatus()) {
            holder.chooseButton.setText("CHOSEN");
        } else {
            holder.chooseButton.setText("CHOOSE");
        }
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }

    ////

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    ////

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button chooseButton;

        ////

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.mode_item_view_image);
            titleTextView = (TextView) itemView.findViewById(R.id.mode_item_view_title);
            descriptionTextView = (TextView) itemView.findViewById(R.id.mode_item_view_description);
            chooseButton = (Button) itemView.findViewById(R.id.mode_item_view_choose_button);

            chooseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Context appContext = MainApp.getAppContext();
                    ModeChosenEvent event = new ModeChosenEvent();

                    if (titleTextView.getText()
                            .equals(appContext.getString(R.string.leisure_mode_title))) {
                        event.setChosenType(ModeType.LEISURE);
                    }

                    if (titleTextView.getText()
                            .equals(appContext.getString(R.string.control_mode_title))) {
                        event.setChosenType(ModeType.CONTROL);
                    }

                    if (titleTextView.getText()
                            .equals(appContext.getString(R.string.health_mode_title))) {
                        event.setChosenType(ModeType.HEALTH);
                    }

                    EventBus.getDefault().post(event);
                }
            });
        }
    }
}
