package com.bruno.bloc;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bruno.R;
import com.bruno.room.db.TagWithNotes;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagViewHolder> {

    private final List<TagWithNotes> _tags;
    private final TagClickListener _tagClickListener;

    public interface TagClickListener {
        void onTagSave(int position);


        void onTagDelete(int position);
    }

    public TagAdapter(List<TagWithNotes> tags, com.bruno.bloc.TagAdapter.TagClickListener tagClickListener) {
        _tags = tags;
        _tagClickListener = tagClickListener;
    }


    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View tagView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tag_item, parent, false);

        TagViewHolder tagViewHolder = new TagViewHolder(tagView, _tagClickListener);
        return tagViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull TagViewHolder holder, int position) {
        holder.bind(_tags.get(position));
    }

    @Override
    public int getItemCount() {
        return _tags.size();
    }
}

