package com.example.irishka.timetable.ui.stations.di;

import android.support.v7.widget.LinearLayoutManager;

import com.example.irishka.timetable.di.scopes.PerFragment;
import com.example.irishka.timetable.ui.stations.view.StationsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class StationsFragmentModule {

    @Provides
    @PerFragment
    static LinearLayoutManager providesLinearLayoutManager(StationsFragment stationsFragment){
        return new LinearLayoutManager(stationsFragment.getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }
}
