package com.example.irishka.timetable.data.mappers;

import android.graphics.Movie;
import android.support.v4.util.Pair;

import com.example.irishka.timetable.data.models.AllStationsModel;
import com.example.irishka.timetable.data.models.CitiesFromModel;
import com.example.irishka.timetable.data.models.CitiesToModel;
import com.example.irishka.timetable.data.models.StationModel;
import com.example.irishka.timetable.domain.entities.Country;
import com.example.irishka.timetable.domain.entities.Station;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

//маппер нужен для того, чтобы преобразовать данные модели в сущности entity для дальнейшего использования
public class StationsMapper {

    @Inject
    StationsMapper() {
    }

    private Station apply(StationModel stationModel){
        Station station = new Station();
        station.setCityId(stationModel.getCityId());
        station.setCityTitle(stationModel.getCityTitle());
        station.setCountryTitle(stationModel.getCountryTitle());
        station.setDistrictTitle(stationModel.getDistrictTitle());
        station.setPoint(new Pair<>(stationModel.getPoint().getLatitude(), stationModel.getPoint().getLongitude()));
        station.setRegionTitle(stationModel.getRegionTitle());
        station.setStationId(stationModel.getStationId());
        station.setStationTitle(stationModel.getStationTitle());

        return station;
    }

    //маппим список всех станций, полученных из JSON
    public List<Country> mapAllStations(AllStationsModel allStationsModel) {

        List<Country> list = new ArrayList<>();

        List<Station> stations;

        for (int i = 0; i<allStationsModel.getCitiesFrom().size(); i++) {

            stations = new ArrayList<>();

            for (int j = 0; j < allStationsModel.getCitiesFrom().get(i).getStations().size(); j++) {
                stations.add(apply(allStationsModel.getCitiesFrom().get(i).getStations().get(j)));
            }

            list.add(new Country(allStationsModel.getCitiesFrom().get(i).getCityTitle().concat(", ").concat(allStationsModel.getCitiesFrom().get(i).getCountryTitle()), stations));

        }

        for (int i = 0; i<allStationsModel.getCitiesTo().size(); i++) {

            stations = new ArrayList<>();

            for (int j = 0; j < allStationsModel.getCitiesTo().get(i).getStations().size(); j++) {
                stations.add(apply(allStationsModel.getCitiesTo().get(i).getStations().get(j)));
            }
            list.add(new Country(allStationsModel.getCitiesTo().get(i).getCityTitle().concat(", ").concat(allStationsModel.getCitiesTo().get(i).getCountryTitle()), stations));

        }

        return list;

    }

    //маппим список станций после того, как пользователь отправил поисковый запрос
    public List<Country> mapFilteredStations(AllStationsModel allStationsModel, String query) {

        List<Country> list = new ArrayList<>();

        List<Station> stations;

        for (int i = 0; i<allStationsModel.getCitiesFrom().size(); i++) {

            stations = new ArrayList<>();

            for (int j = 0; j < allStationsModel.getCitiesFrom().get(i).getStations().size(); j++) {
                if (allStationsModel.getCitiesFrom().get(i).getStations().get(j).getStationTitle().toLowerCase().contains(query.toLowerCase()))
                stations.add(apply(allStationsModel.getCitiesFrom().get(i).getStations().get(j)));
            }

            if (stations.size() > 0)
            list.add(new Country(allStationsModel.getCitiesFrom().get(i).getCityTitle().concat(", ").concat(allStationsModel.getCitiesFrom().get(i).getCountryTitle()), stations));

        }

        for (int i = 0; i<allStationsModel.getCitiesTo().size(); i++) {

            stations = new ArrayList<>();

            for (int j = 0; j < allStationsModel.getCitiesTo().get(i).getStations().size(); j++) {
                if (allStationsModel.getCitiesTo().get(i).getStations().get(j).getStationTitle().toLowerCase().contains(query.toLowerCase()))
                stations.add(apply(allStationsModel.getCitiesTo().get(i).getStations().get(j)));
            }

            if (stations.size() > 0)
            list.add(new Country(allStationsModel.getCitiesTo().get(i).getCityTitle().concat(", ").concat(allStationsModel.getCitiesTo().get(i).getCountryTitle()), stations));
        }

        return list;

    }

    //маппим пункты отправления
    public Map<String, List<String>> mapFromMap(List<CitiesFromModel> citiesFromModels){

        Map<String, List<String>> map = new HashMap<>();

        List<String> stationsNames;
        for (int i = 0; i < citiesFromModels.size(); i++) {

            stationsNames = new ArrayList<>();

            for (int j = 0; j < citiesFromModels.get(i).getStations().size(); j++) {
                stationsNames.add(citiesFromModels.get(i).getStations().get(j).getStationTitle());
            }

            map.put(citiesFromModels.get(i).getCityTitle(), stationsNames);

        }
        return map;
    }

    //маппим пункты назначения
    public Map<String, List<String>> mapToMap(List<CitiesToModel> citiesToModels){

        Map<String, List<String>> map = new HashMap<>();

        List<String> stationsNames;
        for (int i = 0; i < citiesToModels.size(); i++) {

            stationsNames = new ArrayList<>();

            for (int j = 0; j < citiesToModels.get(i).getStations().size(); j++) {
                stationsNames.add(citiesToModels.get(i).getStations().get(j).getStationTitle());
            }

            map.put(citiesToModels.get(i).getCityTitle(), stationsNames);

        }
        return map;
    }

}
