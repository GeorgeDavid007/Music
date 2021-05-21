package com.sample.mark9;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    private ArrayList<Song> songs;
    private ArrayList<CommonModel> songCom;
    private LayoutInflater songInf;

    public MusicAdapter (Context c, ArrayList<Song> theComSongs){

        songs=theComSongs;
        songInf=LayoutInflater.from(c);




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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        @SuppressLint("ViewHolder") RelativeLayout songLay = (RelativeLayout) songInf.inflate
                (R.layout.song_items, parent, false);
        //get title and artist views
        TextView songView = (TextView)songLay.findViewById(R.id.songName);
        TextView artistView = (TextView)songLay.findViewById(R.id.artistName);
        ImageView songImage = (ImageView)songLay.findViewById(R.id.musicImg);

        //get song using position
        Song currSong = songs.get(position);

        String imageFile=songs.get(position).getPath();
//        Drawable images=Drawable.createFromPath(imageFile);

//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(songs.get(position).getPath());
//        byte[] artBytes =  mmr.getEmbeddedPicture();
//        if(artBytes!=null)
//        {
//            //     InputStream is = new ByteArrayInputStream(mmr.getEmbeddedPicture());
//            Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
//            songImage.setImageBitmap(bm);
//        }
//        else
//        {
//            songImage.setImageDrawable(images);
//        }





//        Uri trackUri = ContentUris.withAppendedId(
//                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,position);

        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
//        songImage.setImageDrawable(images);



        //set position as tag
        songLay.setTag(position);

        return songLay;




    }
}
