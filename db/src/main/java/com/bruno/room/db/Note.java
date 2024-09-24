package com.bruno.room.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")public class Note {
    @PrimaryKey(autoGenerate = true)
    public int noteId;
    public String title;
    public String body;
}
