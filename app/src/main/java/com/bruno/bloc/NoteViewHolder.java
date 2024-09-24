package com.bruno.bloc;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.Note;


public class NoteViewHolder extends RecyclerView.ViewHolder {

    private final TextView _nameTextView;


    public NoteViewHolder(@NonNull View itemView, NoteAdapter.NoteClickListener noteClickListener) {
        super(itemView);
        _nameTextView = itemView.findViewById(R.id.name);

        ImageButton buttonDelete = itemView.findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(view -> noteClickListener.onNoteDelete(getAdapterPosition()));
        ImageButton buttonEdit = itemView.findViewById(R.id.button_details);
        buttonEdit.setOnClickListener(view -> noteClickListener.onNoteEdit(getAdapterPosition()));
        ImageView seeButton = itemView.findViewById(R.id.seeNote);
        seeButton.setOnClickListener(view -> noteClickListener.onNoteCheck(getAdapterPosition()));
    }

    public void bind(Note note) {
        _nameTextView.setText(note.title);


    }
}