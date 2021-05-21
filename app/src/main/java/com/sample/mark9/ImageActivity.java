package com.sample.mark9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    List<String> Albumid=new ArrayList<>();
    List<String> SongName=new ArrayList<>();
    ListView listView;
    String Album_id[],SongNames[];
    @Override  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        listView=(ListView)findViewById(R.id.lists);
        getAlbumArt();
        if(Albumid!=null){
            //converting List<String> to Arrays[]
            Album_id=Albumid.toArray(new String[Albumid.size()]);}
        else{
            Log.d("Error","List string is null");
        }
        getSong();
        if(SongName!=null){
            SongNames=SongName.toArray(new String[SongName.size()]);
        }
        else{
            Log.d("Error","Songtrack is empty");
        }

        CustomizedListAdapter adapter=new CustomizedListAdapter(this,null,Album_id,SongNames);
        listView.setAdapter(adapter);
        getSelectedPath();
    }
    public void getSong(){
        try{
            ContentResolver c=getContentResolver();
            Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor1=c.query(uri,null,MediaStore.Audio.Media.IS_MUSIC+ "=1",null,null);
            if(cursor1 !=null && cursor1.moveToFirst()){
                int songname=cursor1.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                do{
                    String songs=cursor1.getString(songname);
                    SongName.add(songs);
                }while(cursor1.moveToNext());
            }
        }catch(NumberFormatException e){
            e.printStackTrace();
        }
    }
    public void getAlbumArt() {
        try {
            ContentResolver cr = getContentResolver();
            Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int albumart = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                do {
                    String Albumids = cursor.getString(albumart);
                    Albumid.add(Albumids);
                } while (cursor.moveToNext());
            }cursor.close();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
    public void getSelectedPath(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override      public void onItemClick(AdapterView adapterView, View view, int i, long l) {
                String path= String.valueOf(listView.getItemAtPosition(i));
                if(path.equals("null")){
                    ImageView imgv=(ImageView)view.findViewById(R.id.imageView);
                    imgv.setImageResource(R.drawable.replay_selected);
                    imgv.setMaxHeight(50);
                    imgv.setMaxWidth(50);
                }
                else{
                    Drawable image=Drawable.createFromPath(path);
                    ImageView imgview=(ImageView)view.findViewById(R.id.imageView);
                    imgview.setImageDrawable(image);
                }
            }
        });
    }
}