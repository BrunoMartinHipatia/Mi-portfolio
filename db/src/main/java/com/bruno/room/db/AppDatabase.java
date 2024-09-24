package com.bruno.room.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

    @Database(entities = {Note.class, Tag.class, NoteTagCrossRef.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
        public abstract NotesDao notesDao();
        public abstract TagDao tagsDao();

        public abstract NotesWithTagsDao noteWithTagsDao();
        public static AppDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "tagnotes-database").build();
    }
}