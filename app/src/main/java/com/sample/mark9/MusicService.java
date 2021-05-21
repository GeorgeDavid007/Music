package com.sample.mark9;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;


import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener{
//    MainActivity mainFrame = new MainActivity();
//    PlayerActive mActivity = new PlayerActive();


//    Context context;
    //media player
    MediaPlayer player=new MediaPlayer();
    //song list
    private ArrayList<Song> songs = new ArrayList<>();
    //current position
    private int songPosn;

//    PlayerActive playerActive=new PlayerActive();

    Song playSong=new Song();




    private final IBinder musicBind = new MusicBinder();



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        mp.setLooping(true);
//        if(playerActive.btnNxt.performClick()){
//            mp.release();
//        }



    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();


    }

    public void musicPause(){

        if(player.isPlaying()){

            player.setLooping(true);
        }

    }

    public void setSong(int songIndex){
        songPosn=songIndex;
    }



//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        player.stop();
//    }



    public void onCreate(){
        //create the service
        super.onCreate();
//initialize position
        songPosn=0;
//create player
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);

    }

    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        player.reset();

        int newSongPos=songPosn;



        //get song
        playSong=songs.get(songPosn);
//get id
        long currSong = playSong.getID();

        String name= songs.get(songPosn).getTitle();

        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);

        player=MediaPlayer.create(this,trackUri);
        player.setLooping(true);
        player.start();
        return START_STICKY;
    }*/

    public void playSong(){

        if(player !=null && player.isPlaying()) {
//
//            mediaPlayer.stop();
            player.reset();
//            player.stop();

        }
//
//
//
        //play a song
//        player.reset();

//        this.context=context;
        //get song
        playSong=songs.get(songPosn);
//get id
        long currSong = playSong.getID();

        String name= songs.get(songPosn).getTitle();
//set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);

        Log.e("Player :"+player,"Bruhh........");

//        player=MediaPlayer.create(this,trackUri);


            try{
                player.setDataSource(getApplicationContext(),trackUri);

                Log.e("Player :"+player,"Bruhh........");
            }
            catch(Exception e){
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }



        player.prepareAsync();

    }


    public int getPosn(){
        return player.getCurrentPosition();
    }



    public void musicRelease(){
        player.release();
    }

    public int getDur(){
        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }

    public void stop(){
        player.stop();
    }

    public int getSongPosn(){
        return songPosn;
    }

    public void playPrev(){
        songPosn--;
        if(songPosn<0) songPosn=songs.size()-1;
//        playSong();
    }

    //skip to next
    public void playNext(){
        songPosn++;
        if(songPosn>=songs.size()) {songPosn=0;}
//        playSong();
    }



}
