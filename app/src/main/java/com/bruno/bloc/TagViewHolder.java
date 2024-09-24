package com.bruno.bloc;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.Tag;
import com.bruno.room.db.TagWithNotes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagViewHolder extends RecyclerView.ViewHolder {

    private final TextView _titleTextView;
    private final TextView _numTextView;
    List<TagWithNotes> lista = new ArrayList<>();

    public TagViewHolder(@NonNull View itemView, TagAdapter.TagClickListener tagClickListener) {
        super(itemView);
        _titleTextView = itemView.findViewById(R.id.nameTag);
        _numTextView = itemView.findViewById(R.id.countTags);
        ImageButton buttonDelete = itemView.findViewById(R.id.button_deleteTag);
        buttonDelete.setOnClickListener(view -> tagClickListener.onTagDelete(getAdapterPosition()));
        ImageButton buttonEdit = itemView.findViewById(R.id.button_editTag);
        buttonEdit.setOnClickListener(view -> tagClickListener.onTagSave(getAdapterPosition()));
    }



    public void bind(TagWithNotes tagWithNotess) {
        _titleTextView.setText(tagWithNotess.tag.tag);
        lista.add(tagWithNotess);
            Map<Tag, Integer> tagSelectionCount = new HashMap<>();
            for (TagWithNotes tagWithNotes : lista) {
                Tag tag = tagWithNotes.tag;
                int count = tagWithNotes.notes.size();
                tagSelectionCount.put(tag, count);
            }
            int count = 0;
            for (Map.Entry<Tag, Integer> entry : tagSelectionCount.entrySet()) {
                count = entry.getValue();
                _numTextView.setText(String.valueOf(count));
            }
    }
}