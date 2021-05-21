package com.sample.mark9;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NewMusicViewer extends ArrayAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;
    private Activity context;
    private int images;


    public NewMusicViewer(@NonNull Activity context, int images, @NonNull ArrayList<Song> songs) {
        super(context, images, songs);
        this.context=context;
        this.images=images;
        this.songs=songs;
        songInf=LayoutInflater.from(context);

    }

    public class ViewHolder{
        ImageView imageView;
        TextView songName,artistName;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if(convertView==null){
            convertView = (RelativeLayout) songInf.inflate
                    (R.layout.song_items, parent, false);
            holder=new ViewHolder();
            holder.songName=(TextView)convertView.findViewById(R.id.songName);
            holder.artistName=(TextView)convertView.findViewById(R.id.artistName);
            holder.imageView=(ImageView)convertView.findViewById(R.id.musicImg);
            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();

        }

        Song currSong=songs.get(position);

        String name=songs.get(position).getPath();


        if(name==null){

            holder.imageView.setImageResource(images);
            holder.imageView.setMaxHeight(50);
            holder.imageView.setMaxWidth(50);

        }else {
            Drawable images=Drawable.createFromPath(name);
            holder.imageView.setImageDrawable(images);
        }

        holder.songName.setText(currSong.getTitle());
        holder.artistName.setText(currSong.getArtist());



        return convertView;

    }
}
