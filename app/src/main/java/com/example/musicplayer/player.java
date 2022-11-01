package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class

player extends AppCompatActivity {

    Button btnplay ,btnnxt,btnprev;
    TextView txtsname, txtsnext,txtsstop;
    SeekBar seekmusic;
    BarVisualizer visualizer;

    String sname;
    public  static  final String EXTRA_NAME=" song_name";
    static MediaPlayer mediaPlayer;
    ArrayList<File> mySong;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnplay= findViewById(R.id.plybtn);
        btnnxt= findViewById(R.id.btnnxt);
        btnprev= findViewById(R.id.btnprev);
        txtsname=findViewById(R.id.tp1);
        txtsnext=findViewById(R.id.tp2);

        txtsstop=findViewById(R.id.tp3);


        if(mediaPlayer!= null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i= getIntent();
        Bundle bundle= i.getExtras();


        mySong= (ArrayList)bundle.getParcelableArrayList("songs");
        String songName=i.getStringExtra("songname");
        position=bundle.getInt("pos",0);
        txtsname.setSelected(true);

        Uri uri= Uri.parse(mySong.get(position).toString());
        sname=mySong.get(position).getName();
        txtsname.setText(sname);
        mediaPlayer=mediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();


        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    btnplay.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }else
                {
                    btnplay.setBackgroundResource(R.drawable.ic_paus);
                    mediaPlayer.start();
                }
            }
        });






    }
}