package com.amar.vibetune;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    String [] songlist;
    public SongAdapter(String [] songlist)
    {
        this.songlist = songlist;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.songlist,parent,false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
         String song = songlist[position];
         holder.txt.setText(song);
//         holder.txt.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Toast.makeText(view.getContext(), "Clicked", Toast.LENGTH_SHORT).show();
//             }
//         });
    }

    @Override
    public int getItemCount() {
        return songlist.length;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        ImageView imageView;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textTitle);
            imageView = itemView.findViewById(R.id.imageicon);
        }
    }

}
