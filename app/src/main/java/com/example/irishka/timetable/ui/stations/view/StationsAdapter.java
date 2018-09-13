package com.example.irishka.timetable.ui.stations.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.irishka.timetable.R;
import com.example.irishka.timetable.domain.entities.Country;
import com.example.irishka.timetable.domain.entities.Station;
import com.example.irishka.timetable.ui.stations.view.ExpandableRecyclerView.CountryViewHolder;
import com.example.irishka.timetable.ui.stations.view.ExpandableRecyclerView.StationViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class StationsAdapter extends ExpandableRecyclerViewAdapter<CountryViewHolder, StationViewHolder> {

    OnItemClickListener onItemClickListener;

    StationsAdapter(List<? extends ExpandableGroup> groups, OnItemClickListener onItemClickListener) {
        super(groups);
        this.onItemClickListener = onItemClickListener;
    }

     public interface OnItemClickListener{
        void onItemClick(Station station);
     }

    @Override
    public CountryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public StationViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.station_item, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(StationViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final Station artist = ((Country) group).getItems().get(childIndex);
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(artist));
        holder.onBind(artist);
    }

    @Override
    public void onBindGroupViewHolder(CountryViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setGenreTitle(group);
    }
}
