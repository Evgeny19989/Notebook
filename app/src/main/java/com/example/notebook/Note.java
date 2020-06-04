package com.example.notebook;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey
    private int id;

    private String title;
    private String text;

}