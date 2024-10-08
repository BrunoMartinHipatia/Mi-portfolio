package com.bruno.room.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes")
    Single<List<Note>> getAll();
    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    Single<Note> find(int noteId);
    @Transaction
    @Query("SELECT * FROM notes")
    Single<List<NoteWithTags>> getNotesWithTags();
    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :noteId")
    Single<NoteWithTags> findWithTags(int noteId);
    @Insert
    Completable insertNote(Note note);
    @Update
    Completable updateNote(Note note);
    @Delete
    Completable deleteNote(Note note);
}
