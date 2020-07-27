package com.example.notebook;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> notes;
    private OnClickItemListener listener;
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

        if (listener != null) {
            holder.itemView.setOnClickListener(
                    v -> listener.onClickItem(notes.get(position).getId())
            );
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.dateText);
        }

        public void bind(Note note) {
            title.setText(note.getTitle());
            date.setText(note.getDate());
            ((CardView) itemView ).setCardBackgroundColor(Color.parseColor(note.getColor()));
        }
    }

    public void setOnClickItemListener(OnClickItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickItemListener {
        void onClickItem(int noteId);
    }

   public void setNotes(List<Note> notes){
        this.notes = notes;
   }

   public Note getNoteAt(int pos){
     return notes.get(pos);
   }
}