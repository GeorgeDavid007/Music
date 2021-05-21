package com.sample.mark9;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.net.Uri;

import android.database.Cursor;


import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements Serializable {

    public static final int PERMISSION_REQUEST_CODE=100;

    private MusicService musicSrv;
    private Intent playIntent=null;
    private boolean musicBound=false;

//    private SongAdapter songAdapter;

    private ArrayList<Song> songName=new ArrayList<>();
    private ArrayList<CommonModel> songCommon=new ArrayList<>();

    private ArrayList<Song> songList;
    private ListView songView;
    int image;
    ArrayList<String> songImage = new ArrayList<>();
//    MediaPlayer musicPlay;



//    ArrayList<Song> songNames=new ArrayList<>();

//    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

//        songCommon=getAllMusicPathList(this);

        songList=getAllAudio(this);
        songName=getSongAlbumArt(this);
//        getAlbumArt();

        if(songList!=null) {

            Collections.sort(songList, new Comparator<Song>() {
                public int compare(Song a, Song b) {
                    return a.getTitle().compareTo(b.getTitle());
                }
            });

        }

        songView=findViewById(R.id.recyclerView);



        /*recyclerView=findViewById(R.id.recycleViewSongs);
        recyclerView.setHasFixedSize(true);
        if(!(songList.size()<1)){
            SongTwoAdapter songTwoAdapter=new SongTwoAdapter(this,songList);
            recyclerView.setAdapter(songTwoAdapter);

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));*/

        MusicAdapter musicAdapter = new MusicAdapter(MainActivity.this, songList);
        songView.setAdapter(musicAdapter);

//        NewMusicViewer musicViewer=new NewMusicViewer(this,image,songList);
//        songView.setAdapter(musicViewer);





        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openService(view, position);

            }
        });






//        ArrayAdapter<Song> myAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songList);
//        songView.setAdapter(myAdapter);
//                    songAdapter = new SongAdapter(MainActivity.this, songList);
//                    songView.setAdapter(songAdapter);

//            songViewed();
//            songClick();








//        songList= getAllAudio(this);
//
//        songNames=getAllAudio(this);

//        Collections.sort(songList, new Comparator<Song>(){
//            public int compare(Song a, Song b){
//                return a.getTitle().compareTo(b.getTitle());
//            }
//        });
//
////        ArrayAdapter<Song> myAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songList);
////        songView.setAdapter(myAdapter);
//        SongAdapter songAdapter = new SongAdapter(MainActivity.this, songList);
//        songView.setAdapter(songAdapter);
//
//        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                openService(view,position);
//
//            }
//        });





//        SongAdapter songAdapter = new SongAdapter(MainActivity.this, songList);
//        songView.setAdapter(songAdapter);

        /*songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openPlayerActivity(position);
                 view.getTag().toString();
            }
        });*/



    }

    public void butClk(View view){
        Intent intent=new Intent(this,ImageFileView.class);
        startActivity(intent);

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
                    songImage.add(Albumids);
                } while (cursor.moveToNext());
            }cursor.close();
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    /*public static ArrayList<CommonModel> getAllMusicPathList(Context context) {
        ArrayList<CommonModel> musicPathArrList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursorAudio = context.getContentResolver().query(songUri, null, null, null, null);
        if (cursorAudio != null && cursorAudio.moveToFirst()) {

            Cursor cursorAlbum;
            if (cursorAudio != null && cursorAudio.moveToFirst()) {

                do {
                    Long albumId = Long.valueOf(cursorAudio.getString(cursorAudio.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                    cursorAlbum = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                            MediaStore.Audio.Albums._ID + "=" + albumId, null, null);

                    if(cursorAlbum != null && cursorAlbum.moveToFirst()){
                        String albumCoverPath = cursorAlbum.getString(cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                        String data = cursorAudio.getString(cursorAudio.getColumnIndex(MediaStore.Audio.Media.DATA));
                        musicPathArrList.add(new CommonModel(data,albumCoverPath ,false));
                    }

                } while (cursorAudio.moveToNext());
            }
        }
        return musicPathArrList;
    }*/





    //    @Override
//    protected void onPause() {
//        super.onPause();
////        musicSrv.unbindService(musicConnection);
//        musicSrv.onUnbind(playIntent);
//    }

    private void openService(View view, int position) {




        Intent i=new Intent(MainActivity.this,PlayerActive.class);
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
//        musicSrv.setSong(position);
        musicSrv.playSong();
        i.putExtra("SONG_KEY",songList);
        i.putExtra("position", position);




//        String songName= songList.get(position).getPath();
//
//        i.putExtra("SONG_KEY",songList);
        startActivity(i);
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        musicSrv.musicPause();
//    }



    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;

        super.onDestroy();
    }


    /*private void openPlayerActivity(int position) {


        Intent i=new Intent(this,PlayerActive.class);

        i.putExtra("SONG_KEY",songList);


//        String songName= songList.get(position).getPath();
//
//        i.putExtra("SONG_KEY",songList);
        i.putExtra("position",position);
        startActivity(i);
    }*/


    public  ArrayList<Song> getAllAudio(Context context){

        ArrayList<Song> tempAudioList = new ArrayList<>();

        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {

                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,//for path
                MediaStore.Audio.Media.ARTIST,



        };

        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor != null){

            while(cursor.moveToNext()){

                Long ID=cursor.getLong(0);
                String album=cursor.getString(1);
                String title=cursor.getString(2);
                String duration=cursor.getString(3);
                String path=cursor.getString(4);
                String artist=cursor.getString(5);



                Song files=new Song(ID,path,title,artist,album,duration);
                //log check
                Log.e("Path : " + path,"album :"+album);
                tempAudioList.add(files);

            }

            cursor.close();
        }

        ArrayList<Song> tempFiles=new ArrayList<>();

        Uri scoop = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] paramem ={ MediaStore.Audio.Albums.ALBUM_ART };

        Cursor c=context.getContentResolver().query(scoop,paramem,null,null,null);

        if(c!=null){

            while (c.moveToNext()){

                String image = c.getString(c.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

                Song files=new Song(image);
                tempFiles.add(files);

            }
        }





        return tempAudioList;

    }

    public ArrayList<Song> getSongAlbumArt(Context context){

        ArrayList<Song> tempFiles=new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] projection={ MediaStore.Audio.Albums.ALBUM_ART };

        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if(cursor!=null){

            while (cursor.moveToNext()){

                String image = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));

                Song files=new Song(image);
                tempFiles.add(files);

            }
        }


        return tempFiles;
    }




    public void requestPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE )) {

//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                Toast.makeText(this, "Please give permission", Toast.LENGTH_SHORT).show();
//            }




        }else {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//            ActivityCompat.requestPermissions(this, new String[]
//                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==PERMISSION_REQUEST_CODE){

            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

//                songList=getAllAudio(this);
//
//                if(songList!=null){
//
//                    Collections.sort(songList, new Comparator<Song>(){
//                        public int compare(Song a, Song b){
//                            return a.getTitle().compareTo(b.getTitle());
//                        }
//                    });
//
////        ArrayAdapter<Song> myAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,songList);
////        songView.setAdapter(myAdapter);
////                    songAdapter = new SongAdapter(MainActivity.this, songList);
////                    songView.setAdapter(songAdapter);
//
//                    songViewed();
//                    songClick();






//                Toast.makeText(this,"Permission Granted", Toast.LENGTH_SHORT).show();

            }else {

                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//                ActivityCompat.requestPermissions(this, new String[]
//                        {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);


            }

        }

    }

    private void songViewed() {
//        SongAdapter songAdapter = new SongAdapter(MainActivity.this, songList);
//        songView.setAdapter(songAdapter);

//        MusicAdapter musicAdapter = new MusicAdapter(MainActivity.this, songList);
//        songView.setAdapter(musicAdapter);
    }

    private void songClick() {


            songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openService(view,position);

                }
            });


    }

    /*public void SongPicked(View view) {

        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();

        Intent i=new Intent(this,PlayerActive.class);

        i.putExtra("SONG_KEY",songList);




//        String songName= songList.get(position).getPath();
//
//        i.putExtra("SONG_KEY",songList);
        startActivity(i);
    }*/

   /* public void songClicked(@NotNull View view) {




    }*/

//  public void songPicked(){
//
////        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////            @Override
////            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
////                Intent intent=new Intent(getApplicationContext(),PlayerActive.class);
////
////                String songName=(String)songList.get(position).getTitle();
////
////                Bundle bundle= new Bundle();
////
////                bundle.putString("songName",songName);
////                bundle.putInt("position",position);
////                intent.putExtras(bundle);
////                startActivity(intent);
////
////                Log.e("Name"+ songName,"postion"+position);
//
//
//
//
////                startActivity(new Intent(getApplicationContext(),PlayerActive.class)
////                        .putExtra("songs",songNames)
////                        .putExtra("songName",songName)
////                        .putExtra("pos",position)
////                );
//
//
//
//
//
//            }
//        });
//
//    }

}