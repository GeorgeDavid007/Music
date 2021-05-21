package com.sample.mark9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;

public class ImageFileView extends AppCompatActivity {


    ListView listView;
    ArrayList<Song> songsList =new ArrayList<>();
    ImageView songImage;
    Song songs=new Song();
    MainActivity songNames=new MainActivity();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        listView=findViewById(R.id.listSong);
        songImage = (ImageView)findViewById(R.id.musicImg);



        songsList=songNames.getAllAudio(this);











        MusicAdapter musicAdapter = new MusicAdapter(this, songsList);
        listView.setAdapter(musicAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




//                Drawable image=Drawable.createFromPath(songsList.get(position).getAlbum());
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();

                Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,position);

//                mmr.setDataSource(String.valueOf(R.raw.first));

                try{
                    mmr.setDataSource(ImageFileView.this,trackUri);

                    Log.e("Player :"+mmr,"Bruhh........");
                }
                catch(Exception e){
                    Log.e("MUSIC SERVICE", "Error setting data source", e);
                }


                byte[] artBytes =  mmr.getEmbeddedPicture();
                if(artBytes!=null)
                {
                    //     InputStream is = new ByteArrayInputStream(mmr.getEmbeddedPicture());
                    Bitmap bm = BitmapFactory.decodeByteArray(artBytes, 0, artBytes.length);
                    songImage.setImageBitmap(bm);
                }
                else
                {
//                    songImage.setImageDrawable(image);
                    songImage.setImageResource(R.drawable.ic_icoplay);
                }


            }
        });








    }
}