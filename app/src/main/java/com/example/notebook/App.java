package com.example.notebook;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    private static App instance;

    private NotebookDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, NotebookDatabase.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public NotebookDatabase getDatabase() {
        return database;
    }
}