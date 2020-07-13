package com.example.notebook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> notes;

    Note note;

    private OnClickItemListener listener = new OnClickItemListener() {
        @Override
        public void onClickItem(Note note) {

        }
    };

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(notes.get(position));
        holder.itemView.setOnClickListener(
                v -> listener.onClickItem(notes.get(position))


        );
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox checkBox;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);


        }

        public void bind(Note note) {
            title.setText(note.getTitle());

        }
    }

    public interface OnNoteListener{
        void onNoteClick(int pos);
    }

    public void setListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    private interface OnClickItemListener {
        void onClickItem(Note note);

    }
}
