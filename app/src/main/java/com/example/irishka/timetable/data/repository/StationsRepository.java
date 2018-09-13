package com.example.irishka.timetable.data.repository;

import android.content.Context;

import com.example.irishka.timetable.R;
import com.example.irishka.timetable.data.database.dao.TripDao;
import com.example.irishka.timetable.data.mappers.StationsMapper;
import com.example.irishka.timetable.data.mappers.TripMapper;
import com.example.irishka.timetable.data.models.AllStationsModel;
import com.example.irishka.timetable.domain.entities.Country;
import com.example.irishka.timetable.domain.entities.Trip;
import com.example.irishka.timetable.domain.repository.IStationsRepository;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

//репозиторий служит прослойкой между data-слоем и UI
public class StationsRepository implements IStationsRepository {

    private StationsMapper stationsMapper;

    private TripMapper tripMapper;

    private Context context;

    private TripDao tripDao;

    @Inject
    StationsRepository(StationsMapper stationsMapper, TripMapper tripMapper, Context context, TripDao tripDao) {
        this.stationsMapper = stationsMapper;
        this.tripMapper = tripMapper;
        this.context =  context;
        this.tripDao = tripDao;
    }

    //вставка поездки
    //RxJava2 позволяет легко управлять потоками
    //поэтому не нужно использовать AsyncTask или вручную управлять трэдами
    //в основном потоке нельзя работать с БД, так как это может повлиять на скорость его выполнения
    @Override
    public void insertTrip(Trip trip){
        Completable.fromAction(() -> tripDao.insert(tripMapper.applyToDb(trip)))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    //получаем поездки из БД
    //также используем другой поток
    @Override
    public Single<List<Trip>> getTrips(){
        return tripDao.getTrips()
                .map(tripsDb -> tripMapper.mapTripsListFromDb(tripsDb))
                .subscribeOn(Schedulers.io());
    }

    //представляем JSON  виде строки для дальнейшей работы с данными
    public String loadJSON() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.all_stations);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        return writer.toString();
    }

    //получаем список всех станций
    @Override
    public List<Country> getStationsList() {

        AllStationsModel allStationsModel = null;
        try {
            allStationsModel = new Gson()
                    .fromJson(loadJSON(), AllStationsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationsMapper.mapAllStations(allStationsModel);
    }

    //получаем список станций после того, как пользователь отправил какой-либо поисковый запрос
    @Override
    public List<Country> getFilteredStationsList(String query) {

        AllStationsModel allStationsModel = null;
        try {
            allStationsModel = new Gson()
                    .fromJson(loadJSON(), AllStationsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationsMapper.mapFilteredStations(allStationsModel, query);
    }

    //пункты отправления (название города - список станций)
    @Override
    public  Map<String, List<String>> getFromMap() {

        AllStationsModel allStationsModel = null;
        try {
            allStationsModel = new Gson()
                    .fromJson(loadJSON(), AllStationsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationsMapper.mapFromMap(allStationsModel.getCitiesFrom());
    }

    //аналогично пункты назначения
    @Override
    public Map<String, List<String>> getToMap() {

        AllStationsModel allStationsModel = null;
        try {
            allStationsModel = new Gson()
                    .fromJson(loadJSON(), AllStationsModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationsMapper.mapToMap(allStationsModel.getCitiesTo());
    }
}
