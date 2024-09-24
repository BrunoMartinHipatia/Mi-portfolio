package com.bruno.room.db;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class TagWithNotes {
    @Embedded
    public Tag tag;
    @Relation(
            parentColumn = "tagId",
            entityColumn = "noteId",
            associateBy = @Junction(NoteTagCrossRef.class)
    )   public List<Note> notes;
    //TODO crear empty

    public static TagWithNotes Empty(){
        TagWithNotes tagWithNotes = new TagWithNotes();
        tagWithNotes.tag = new Tag();
        tagWithNotes.notes = new ArrayList<>();
        return tagWithNotes;
    }


}