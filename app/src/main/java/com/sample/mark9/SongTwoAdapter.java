package com.sample.mark9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SongTwoAdapter extends RecyclerView.Adapter<SongTwoAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Song> songs;
    private LayoutInflater songInf;


    public SongTwoAdapter (Context mContext, ArrayList<Song> songs){
        this.mContext=mContext;
        this.songs=songs;
        songInf= LayoutInflater.from(mContext);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view=songInf.inflate(R.layout.song_items, parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SongTwoAdapter.MyViewHolder holder, int position) {

        holder.songName.setText(songs.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView songName,artistName;
        ImageView songImage;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.songName);
            artistName=itemView.findViewById(R.id.artistName);
            songImage=itemView.findViewById(R.id.musicImg);
        }
    }

}
