package com.amar.vibetune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListSongAdapter extends ArrayAdapter<String> {
    String [] songlist;
    Context context;
    public ListSongAdapter(@NonNull Context context,String [] songlist) {
        super(context,R.layout.songlist,songlist);
        this.context = context;
        this.songlist = songlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.songlist,parent,false);
        }
        TextView text = view.findViewById(R.id.textTitle);
        ImageView imageView = view.findViewById(R.id.imageicon);
        text.setText(songlist[position]);
        return view;
    }
}
