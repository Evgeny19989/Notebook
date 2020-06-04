package com.example.notebook;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Note.class, version = 1)
public abstract class NotebookDatabase extends RoomDatabase {
    public abstract NotebookDao getNotebookDao();
}