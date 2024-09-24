package com.bruno.room.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class NoteWithTags {
    @Embedded
    public Note note;
    @Relation(
            parentColumn = "noteId",
            entityColumn = "tagId",
            associateBy = @Junction(NoteTagCrossRef.class))
    public List<Tag> tags;

    public static NoteWithTags Empty() {
        NoteWithTags note = new NoteWithTags();
        note.note = new Note();
        note.tags = new ArrayList<>();
        return note;
    }
}