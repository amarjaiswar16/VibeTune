package com.amar.vibetune;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {
    TextView songTitle;
    ImageView imgback,imgplay,imgnext;
    ArrayList<File> songs;
    MediaPlayer mediaPlayer;
    String textContent;
    int position;
    SeekBar seekBarsong;
    Thread updateseekbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        songTitle = findViewById(R.id.txtsong);
        imgback = findViewById(R.id.previous);
        imgplay = findViewById(R.id.play);
        imgnext = findViewById(R.id.next);
        seekBarsong = findViewById(R.id.seekBar);


        Intent intent = getIntent();
        String s1 = intent.getStringExtra("songList");
        String s2 = intent.getStringExtra("currentSong");
        String s3 = intent.getStringExtra("position");

        Bundle bundle = intent.getExtras();
        songs = (ArrayList) bundle.getParcelableArrayList("songList");
        textContent = intent.getStringExtra("currentSong");
        songTitle.setText(textContent);
        songTitle.setSelected(true);

        position = intent.getIntExtra("position",0);
        Uri uri = Uri.parse(songs.get(position).toString());
        mediaPlayer = MediaPlayer.create(this,uri);
        mediaPlayer.start();
        seekBarsong.setMax(mediaPlayer.getDuration());

        seekBarsong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarsong.getProgress());
            }
        });

        updateseekbr = new Thread() {
            @Override
            public void run() {
                int currentPosition = 0;
                try {
                    while (currentPosition < mediaPlayer.getDuration()) {
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBarsong.setProgress(currentPosition);
                        sleep(800);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        updateseekbr.start();


        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position != songs.size() -1){
                    position = position + 1;
                }else{
                    position = 0;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                imgplay.setImageResource(R.drawable.pause);
                seekBarsong.setMax(mediaPlayer.getDuration());
                //seekBarsong.setMax(mediaPlayer.getCurrentPosition());
                textContent = songs.get(position).getName().replace("-128-kbps-sound","").replace(".mp3","").toString();
                songTitle.setText(textContent);

            }
        });

        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    imgplay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();

                }else{
                    imgplay.setImageResource(R.drawable.pause);
                    mediaPlayer.start();
                }

            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if(position != 0){
                    position = position - 1;
                }else{
                    position = songs.size() - 1;
                }
                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                mediaPlayer.start();
                imgplay.setImageResource(R.drawable.pause);
                seekBarsong.setMax(mediaPlayer.getDuration());
                textContent = songs.get(position).getName().replace("-128-kbps-sound","").replace(".mp3","").toString();
                songTitle.setText(textContent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        updateseekbr.interrupt();
    }
}