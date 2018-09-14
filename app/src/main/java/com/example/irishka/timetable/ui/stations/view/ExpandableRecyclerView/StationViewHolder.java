package com.example.irishka.timetable.ui.stations.view.ExpandableRecyclerView;

import android.view.View;
import android.widget.TextView;

import com.example.irishka.timetable.R;
import com.example.irishka.timetable.domain.entities.Station;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationViewHolder extends ChildViewHolder {

    @BindView(R.id.tv_station)
    TextView station;

    public StationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Station station) {
        this.station.setText(station.getStationTitle());
    }
}
