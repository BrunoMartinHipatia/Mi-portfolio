package com.bruno.bloc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.Note;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private final List<Note> _notes;
    private final NoteClickListener _noteClickListener;

    public interface NoteClickListener {
        void onNoteEdit(int position);

        void onNoteDelete(int position);
        void onNoteCheck(int position);
    }

    public NoteAdapter(List<Note> notes, NoteClickListener noteClickListener) {
        _notes = notes;
        _noteClickListener = noteClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View studentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_student_item, parent, false);
        NoteViewHolder studentViewHolder = new NoteViewHolder(studentView, _noteClickListener);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(_notes.get(position));
    }

    @Override
    public int getItemCount() {
        return _notes.size();
    }
}