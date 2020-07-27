package com.example.notebook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class SpinnerAdapter extends BaseAdapter {

    private String[] mColorNotes;
    private LayoutInflater mLayoutInflater;

    SpinnerAdapter(@NonNull Context context){
        mColorNotes = context.getResources().getStringArray(R.array.color_array);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mColorNotes.length;
    }

    @Override
    public Object getItem(int position) {
        return mColorNotes[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.spinner_item, parent, false);
        }
            CardView cardview = view.findViewById(R.id.cardview_item);
            cardview.setCardBackgroundColor(Color.parseColor(mColorNotes[position]));

            return view;
    }

    public int getColorIndex(String selectedColorNote){
        for(int i = 0 ; i < mColorNotes.length;i++){
            if(mColorNotes[i].equals(selectedColorNote)){
                return i;
            }
        }
        return 0 ;
    }
}
