package com.sample.mark9;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CustomizedListAdapter extends ArrayAdapter {
    private Activity context;
    private Integer Images[];
    private String SongName[];
    private String Path[];
    public CustomizedListAdapter(Activity context,Integer Images[],String Path[],String SongName[]){
        super(context,R.layout.song_items,Path);
        this.context=context;
        this.Images=Images;
        this.Path=Path;
        this.SongName=SongName;
    }
    private class ViewHolder{
        ImageView imgview;
        TextView txtview;
        TextView songtxt;
    }
    @NonNull
    @Override  public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater=context.getLayoutInflater();
        if(convertView==null){
            convertView=inflater.inflate(R.layout.song_items,null);
            holder=new ViewHolder();
            holder.imgview=(ImageView)convertView.findViewById(R.id.imageView);
            holder.txtview=(TextView)convertView.findViewById(R.id.songName);
            holder.songtxt=(TextView)convertView.findViewById(R.id.artistName);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.imgview.setImageResource(R.mipmap.ic_launcher);
        holder.txtview.setText(Path[position]);
        holder.songtxt.setText(SongName[position]);
        return convertView;
    }
}
