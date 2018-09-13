package com.example.irishka.timetable.ui.addTrip.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.example.irishka.timetable.domain.entities.Trip;
import com.example.irishka.timetable.domain.repository.IStationsRepository;
import com.example.irishka.timetable.ui.BasePresenter;
import com.example.irishka.timetable.ui.addTrip.view.AddTripView;

import javax.inject.Inject;

//благодаря Moxy данные не потеряются при смене ориентации экрана
@InjectViewState
public class AddTripPresenter extends BasePresenter<AddTripView> {

    private IStationsRepository stationsRepository;

    @Inject
    AddTripPresenter(IStationsRepository repository) {
        this.stationsRepository = repository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getFromMap();
        getToMap();
    }

    //вставка поездки с последующим вызовом метода фрагмента
    public void insertTrip(Trip trip) {
        stationsRepository.insertTrip(trip);
        getViewState().showToast();
    }

    //получение пунктов откправления
    private void getFromMap() {
        getViewState().setFromMap(stationsRepository.getFromMap());
    }

    //получение пунктов назначения
    private void getToMap() {
        getViewState().setToMap(stationsRepository.getToMap());
    }
}
