package com.example.irishka.timetable.di.module;

import com.example.irishka.timetable.di.scopes.PerActivity;
import com.example.irishka.timetable.ui.mainActivity.MainActivity;
import com.example.irishka.timetable.ui.mainActivity.di.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = AndroidSupportInjectionModule.class)
public abstract class BuilderModule {

    //здесь провайдим все активити
    //в данном приложении активити одна
    //остальное-фрагменты
    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity provideMainActivity();
}
