package com.sample.mark9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sample.mark9.MusicService.MusicBinder;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewAnimator;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class PlayerActive extends AppCompatActivity implements Runnable {

//    RelativeLayout relativeLayout;



    MediaPlayer mediaPlayer = new MediaPlayer();

    RelativeLayout playButton;

    private MusicService musicSrv = new MusicService();
    private Intent playIntent=null;
    private boolean musicBound=false;

    ListView playerList;

    TextView songTitle, artistName, startTime, endTime;
    SeekBar seekBar;


    ImageButton imageButton;
    ImageView btnPlay, btnFrwd,btnRvnd,btnNxt,btnPrev;

    TextView txtName;
//    SeekBar seekTime;
    String songToPlay;

    String sName;
    ArrayList<Song> songList=new ArrayList<>();
//    ArrayList<Song> songList;
    int position;

    boolean wasPlaying = false;

    Handler handler;
    Runnable runnable;

//    Thread updateSeekBar;
//    GridLayout viewSong;
//    ArrayList<Song> songList;

//    VideoView videoView;

//    WebView webView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_active);

        playerList=findViewById(R.id.playerListView);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        ViewAnimator viewAnimator=findViewById(R.id.viewAnim);
//        videoView=findViewById(R.id.gifVideo);

//        webView=findViewById(R.id.gifVideo);
//
//        WebSettings webSettings=webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        String file="file:android_assets/record.gif";
//        webView.loadUrl(file);

//        GifImageView gifImageView = (GifImageView) findViewById(R.id.gifImageView1);
//        gifImageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        Uri uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.record);
//        videoView.setVideoURI(uri);
//        videoView.start();


//        imageButton=findViewById(R.id.btnNav);
//
//        btnPause=findViewById(R.id.pause);
//        btnPlay=findViewById(R.id.play);
//        btnStop=findViewById(R.id.stop);
//        seekTime=findViewById(R.id.seek);
//        txtName=findViewById(R.id.musicTitle);
//        relativeLayout = findViewById(R.id.relativeLayout);

        songTitle=findViewById(R.id.musicTitle);
        artistName=findViewById(R.id.musicArtistName);
        startTime=findViewById(R.id.timeStart);
        endTime=findViewById(R.id.timeEnd);
        seekBar=findViewById(R.id.seekMusic);
        btnPlay=findViewById(R.id.control);
        btnFrwd=findViewById(R.id.forward);
        btnNxt=findViewById(R.id.next);
        btnRvnd=findViewById(R.id.rewind);
        btnPrev=findViewById(R.id.previous);
        playButton=findViewById(R.id.playBtn);





//        seekBar.setMax(musicSrv.getDur());
//
//        endTime.setText(seekBar.getMax());






//        Bundle bundle=getIntent().getExtras();
//        txtName.setText(bundle.getString("songName"));

//        initializeViews();

//        if(mediaPlayer!=null){
//            mediaPlayer.stop();
//        }
//        initMusicPlayer();

//        playBtnSong();

//        musicSrv.player.seekTo(seekBar.getProgress());
//
//        seekBar.setMax(musicSrv.player.getDuration());

//        try {




            if (musicSrv.isPng()) {
//                clearMediaPlayer();
                seekBar.setProgress(0);
//                wasPlaying = true;
//                btnPlay.setBackgroundResource(R.drawable.ic_action_play);
                musicSrv.pausePlayer();
            }


            else {



//                btnPlay.setBackgroundResource(R.drawable.ic_action_pause);


//                AssetFileDescriptor descriptor = getAssets().openFd("first.mp3");
//                musicSrv.player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//                descriptor.close();

//                musicSrv.player.prepare();
//                musicSrv.player.setVolume(0.5f, 0.5f);
//                musicSrv.player.setLooping(true);
                seekBar.setMax(musicSrv.player.getDuration());

//                musicSrv.player.start();
                musicSrv.go();


            }

            new Thread(this){

        public void run() {

            int currentPosition = musicSrv.player.getCurrentPosition();
            int total = musicSrv.player.getDuration();


            while (musicSrv.player != null && musicSrv.player.isPlaying() && currentPosition < total) {
                try {
                    Thread.sleep(1000);
                    currentPosition = musicSrv.player.getCurrentPosition();
                } catch (InterruptedException e) {
                    return;
                } catch (Exception e) {
                    return;
                }

                seekBar.setProgress(currentPosition);

            }
        }
            };


//            wasPlaying = false;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }

//        new Thread(this).start();







        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

//                seekBarHint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
//                seekBarHint.setVisibility(View.VISIBLE);
                int x = (int) Math.ceil(progress / 1000f);

                if ( x == 0 && musicSrv.player != null && !musicSrv.isPng()) {
//                    clearMediaPlayer();
//                    playMusic.setImageDrawable(ContextCompat.getDrawable(PlayerActive.this, android.R.drawable.ic_media_play));
                    PlayerActive.this.seekBar.setProgress(0);
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


                if (musicSrv.player != null && musicSrv.isPng()) {
                    musicSrv.player.seekTo(seekBar.getProgress());
                }
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtnSong();



//                if(musicSrv.isPng()){
//                    btnPlay.setBackgroundResource(R.drawable.ic_action_play);
//                    musicSrv.pausePlayer();
//
////                    seekBar.setMax(musicSrv.getPosn());
////                    handler.postDelayed(runnable,0);
//                }else{
//                    btnPlay.setBackgroundResource(R.drawable.ic_action_pause);
//                    musicSrv.go();
////                    updateSeekBar();
//
//
////                    handler.removeCallbacks(runnable);
//                }
            }


        });

        receiveSong();
        playSong();







    }

    private void playBtnSong() {

        try {




            if (musicSrv.isPng()) {
//                clearMediaPlayer();
                seekBar.setProgress(0);
//                wasPlaying = true;
                btnPlay.setBackgroundResource(R.drawable.ic_action_play);
                musicSrv.pausePlayer();
            }


            else {



                btnPlay.setBackgroundResource(R.drawable.ic_action_pause);


//                AssetFileDescriptor descriptor = getAssets().openFd("first.mp3");
//                musicSrv.player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//                descriptor.close();

//                musicSrv.player.prepare();
//                musicSrv.player.setVolume(0.5f, 0.5f);
//                musicSrv.player.setLooping(true);
                seekBar.setMax(musicSrv.player.getDuration());

//                musicSrv.player.start();
                musicSrv.go();


            }

            new Thread(this).start();

//            wasPlaying = false;
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void run() {

        int currentPosition = musicSrv.player.getCurrentPosition();
        int total = musicSrv.player.getDuration();


        while (musicSrv.player != null && musicSrv.player.isPlaying() && currentPosition < total) {
            try {
                Thread.sleep(1000);
                currentPosition = musicSrv.player.getCurrentPosition();
            } catch (InterruptedException e) {
                return;
            } catch (Exception e) {
                return;
            }

            seekBar.setProgress(currentPosition);

        }
    }

    private void clearMediaPlayer() {
        musicSrv.player.stop();
        musicSrv.player.release();
        musicSrv.player = null;
    }


    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
//            //pass list
            musicSrv.setList(songList);
            musicSrv.musicPause();
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



 /*   private void initMusicPlayer() {

//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);


    }*/


    //    /**
//     * Initialize widgets
//     */
//    private void initializeViews(){
//
//
//
//    }
    private void receiveSong(){

//receive passed song via intent



        Intent i=this.getIntent();
        Bundle bundle=i.getExtras();

        songList=(ArrayList) bundle.getParcelableArrayList("SONG_KEY");
//        songToPlay=i.getStringExtra("SONG_KEY");
        position=bundle.getInt("position",0);

        SongAdapter songAdapter = new SongAdapter(this, songList);
        playerList.setAdapter(songAdapter);




    }

    /*//connect to the service
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

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }*/

    private void playSong() {
//TextView to display the song name

//        if(mediaPlayer !=null && mediaPlayer.isPlaying()) {
//
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer.reset();
//
//
//        }


        String name = songList.get(position).getTitle();
        String artist=songList.get(position).getArtist();
        songTitle.setText(name);
        artistName.setText(artist);

//        handler =new Handler();
//
//        seekBar.setMax(musicSrv.getDur());
//
//
//        if(musicSrv.player.isPlaying()){
//            runnable=new Runnable() {
//                @Override
//                public void run() {
//                    seekBar.setProgress(musicSrv.getPosn());
//                    handler.postDelayed(this,50);
//                }
//            };
//            handler.postDelayed(runnable,1000);
//        }
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                if (fromUser){
//                    musicSrv.seek(progress);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });


//        runnable=new Runnable() {
//            @Override
//            public void run() {
//                seekBar.setProgress(musicSrv.getPosn());
//
//                handler.postDelayed(this,500);
//            }
//
//        };
//
//        int duration = musicSrv.getDur();
//
//        String sDuration=convertFormat(duration);
//
//        endTime.setText(sDuration);

//        int duration=musicSrv.getDur();
//
//        seekBar.setMax(duration);
//
//        endTime.setText(seekBar.getMax());








//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(musicSrv.isPng()){
//                    btnPlay.setBackgroundResource(R.drawable.ic_action_play);
//                    musicSrv.pausePlayer();
//                }else{
//                    btnPlay.setBackgroundResource(R.drawable.ic_action_pause);
//                    musicSrv.go();
//                }
//
//
//            }
//        });

        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                musicSrv.playNext();
                musicSrv.playSong();
                sName=songList.get(musicSrv.getSongPosn()).getTitle();
                songTitle.setText(sName);

                btnPlay.setBackgroundResource(R.drawable.ic_action_pause);
//                musicSrv.playNext();

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSrv.playPrev();
                musicSrv.playSong();
                sName=songList.get(musicSrv.getSongPosn()).getTitle();
                songTitle.setText(sName);

                btnPlay.setBackgroundResource(R.drawable.ic_action_pause);

            }
        });


//        updateSeekBar=new Thread(){
//            @Override
//            public void run() {
//
//                int currentPosition = musicSrv.player.getCurrentPosition();
//                int total = musicSrv.player.getDuration();
//
//
//                while (musicSrv.player != null && musicSrv.player.isPlaying() && currentPosition < total) {
//                    try {
//                        Thread.sleep(1000);
//                        currentPosition = musicSrv.player.getCurrentPosition();
//                    } catch (InterruptedException e) {
//                        return;
//                    } catch (Exception e) {
//                        return;
//                    }
//
//                    seekBar.setProgress(currentPosition);
//
//                }
//            }
//        };

//        seekBar.setMax(musicSrv.player.getDuration()/1000);
//        updateSeekBar.start();
//        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.MULTIPLY);
//        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.red),PorterDuff.Mode.SRC_IN);
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
//
//                musicSrv.seek(position);
//
//            }
//        });
//
//        String endDurTime=createTime(musicSrv.player.getDuration());
//        endTime.setText(endDurTime);
//
//        final Handler handler=new Handler();
//        final int delay=1000;
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String currentTime=createTime(musicSrv.player.getCurrentPosition());
//                startTime.setText(currentTime);
//                handler.postDelayed(this,delay);
//
//            }
//        }, delay);

        btnFrwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSrv.isPng()){
                    musicSrv.player.seekTo(musicSrv.getPosn()+10000);
//                    startTime.setText(convertFormat(musicSrv.getPosn()));
//                    musicSrv.seek(musicSrv.getPosn());
                }
            }
        });

        btnRvnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicSrv.isPng()){
                    musicSrv.player.seekTo(musicSrv.getPosn()-10000);
                }
            }
        });

//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                if(fromUser){
//                    musicSrv.seek(progress);
//
//                }
//
//                startTime.setText(convertFormat(musicSrv.getPosn()));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        /*if(musicSrv!=null){

            musicSrv.setList(songList);
            musicSrv.setSong(position);



            musicSrv.playSong();


        }else {

            Log.e("Error......","With MusicSrv"+musicSrv);

        }*/





//
//        String name = songList.get(position).getTitle();
//        txtName.setText(name);
//
//        long newPos = songList.get(position).getID();
//
//        Uri trackUri = ContentUris.withAppendedId(
//                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                newPos);
//
////        Uri uri = Uri.parse(songList.get(position).toString());
//
////        mediaPlayer = MediaPlayer.create(this, uri);
//
//        try {
//            mediaPlayer.setDataSource(getApplicationContext(),trackUri);
//        } catch (IOException e) {
//            Log.e("MUSIC SERVICE", "Error setting data source", e);
//        }
//
//        mediaPlayer.prepareAsync();
//
//
//        Log.e("Media Player" ,"Value "+mediaPlayer);

//        if(mediaPlayer!=null){
//            mediaPlayer.start();
//        }else {
//
//            Toast.makeText(this,"Still Error in MediaPlayer",Toast.LENGTH_LONG).show();
//
//        }

//        try{
//            mediaPlayer.setDataSource(getApplicationContext(), uri);
//            Log.d("Uri item","Uri : "+uri);
//        }
//        catch(Exception e){
//            Log.e("MUSIC SERVICE", "Error setting data source", e);
//        }

//        mediaPlayer.prepareAsync();


//            mediaPlayer.start();

//        }



//        if (name==null) {
//            Toast.makeText(this, "Hey PlayerActivity has received a null or empty song."+name, Toast.LENGTH_LONG).show();
//        } else {
//            try {
//                mediaPlayer.reset();
//                // in recursive version
//                mediaPlayer=MediaPlayer.create(this,uri);
////                mediaPlayer.setDataSource(name);
////                mediaPlayer.prepare();
//                mediaPlayer.start();

//            } catch (Exception e) {
//                Toast.makeText(getBaseContext(), "Cannot Play Song!", Toast.LENGTH_SHORT).show();

//            }
//        }
    }

//    @SuppressLint("DefaultLocale")
//    private String convertFormat(int duration) {
//
//        return String.format("%02d:%02d",
//                TimeUnit.MILLISECONDS.toMinutes(duration),
//                TimeUnit.MILLISECONDS.toSeconds(duration)-
//                TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
//    }


//    public String createTime(int duration){
//        String time="";
//
//        int min=(int) (duration % (1000 * 60 * 60)) / (1000 * 60);
//        int sec = (int) ((duration % (1000 * 60 * 60)) % (1000 * 60) / 1000);
//
//        time+=min+":";
//
//        if(sec<10){
//            time+="0";
//        }
//
//        time+=sec;
//
//        return time;
//    }
//
//    private void updateSeekBar() {
//        seekBar.setProgress(musicSrv.player.getCurrentPosition());
//        endTime.setText(milliSecondsToTimer(musicSrv.player.getCurrentPosition()));
//        handler.postDelayed(runnable, 50);
//    }
//
//    private String milliSecondsToTimer(long milliseconds) {
//        String finalTimerString = "";
//        String secondsString = "";
//
//        // Convert total duration into time
//        int hours = (int) (milliseconds / (1000 * 60 * 60));
//        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
//        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
//        // Add hours if there
//        if (hours > 0) {
//            finalTimerString = hours + ":";
//        }
//
//        // Prepending 0 to seconds if it is one digit
//        if (seconds < 10) {
//            secondsString = "0" + seconds;
//        } else {
//            secondsString = "" + seconds;
//        }
//
//        finalTimerString = finalTimerString + minutes + ":" + secondsString;
//
//        // return timer string
//        return finalTimerString;
//    }


//    public void btnClk(View view){
//
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
//
//
//

//    @Override
//    public void onCompletion(MediaPlayer mp) {
//
//    }
//
//    @Override
//    public boolean onError(MediaPlayer mp, int what, int extra) {
//        return false;
//    }
//
//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        mp.start();
//
//    }
}