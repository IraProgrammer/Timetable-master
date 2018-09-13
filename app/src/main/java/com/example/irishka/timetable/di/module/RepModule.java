package com.example.irishka.timetable.di.module;

import com.example.irishka.timetable.data.repository.StationsRepository;
import com.example.irishka.timetable.domain.repository.IStationsRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepModule {

    //связываем интерфейс репозитория с конкретной реализацией
    @Binds
    @Singleton
    abstract IStationsRepository provideStationsRepository(StationsRepository moviesRepository);

}
