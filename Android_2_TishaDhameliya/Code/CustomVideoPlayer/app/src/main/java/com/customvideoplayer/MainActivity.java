package com.customvideoplayer;

import android.Manifest;
import android.app.VoiceInteractor;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.StyledPlayerView;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    ExoPlayer player;
//    StyledPlayerView playerView;
    private static final int REQUEST_PERMISSION = 100;
    VideoView videoView;
    List<File> videoFiles;
    Button playbtn,pausebtn,nextbtn,previousbtn;
    int currentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        playerView=findViewById(R.id.player_view);
//        playbtn=findViewById(R.id.play_btn);
        videoView=findViewById(R.id.videoView);
        pausebtn=findViewById(R.id.pause_btn);
        nextbtn=findViewById(R.id.next_btn);
        previousbtn=findViewById(R.id.previous_btn);

        //Permissions
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
        }
        else {
            loadVideos();
        }

        pausebtn.setOnClickListener(v -> {
            if(videoView.isPlaying()){
                videoView.pause();
                pausebtn.setText("Pause");
            }else {
                videoView.start();
                pausebtn.setText("Pause");
            }
        });

        nextbtn.setOnClickListener(v -> {
            playNextVideo();
        });

        previousbtn.setOnClickListener(v -> {
            playPreviousVideo();
        });

        videoView.setOnCompletionListener(mp -> playNextVideo());

        //Initialize
//        player = new ExoPlayer.Builder(this).build();
//        playerView.setPlayer(player);
//
//        //Load video
//        String videoPath= " ";
//        MediaItem mediaItem=MediaItem.fromUri(videoPath);
//        player.setMediaItem(mediaItem);
//
//        //set uo button
//        playbtn.setOnClickListener(v -> player.play());
//        pausebtn.setOnClickListener(v -> player.pause());
//
//        //for next and previous
//        nextbtn.setOnClickListener(v -> {
//
//        });
//
//        previousbtn.setOnClickListener(v -> {
//
//        });

    }

    private void playPreviousVideo() {
        if(videoFiles!=null && videoFiles.isEmpty()){
            currentIndex=(currentIndex-1 + videoFiles.size()) % videoFiles.size();
            playVideo(currentIndex);
        }
    }

    private void loadVideos() {
        File directory=new File(getExternalFilesDir(null).getPath());
        videoFiles=new ArrayList<>();
        if(directory.exists()){
            for(File file:directory.listFiles()){
                if(file.getName().endsWith(".mp4")){
                    videoFiles.add(file);
                }
            }
        }

        if(!videoFiles.isEmpty()){
            playVideo(currentIndex);
        } else {
            Toast.makeText(this, "No video files found", Toast.LENGTH_SHORT).show();
        }

    }

    private void playVideo(int index) {
        if(index>=0 && index<videoFiles.size()){
            Uri videoUri = Uri.fromFile(videoFiles.get(index));
            videoView.setVideoURI(videoUri);
            videoView.start();
            pausebtn.setText("Pause");
        }
    }

    private void playNextVideo() {
        if(videoFiles!=null && !videoFiles.isEmpty()){
            currentIndex=(currentIndex+1) % videoFiles.size();
            playVideo(currentIndex);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                loadVideos();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    @Override
//    protected void onStop() {
//        super.onStop();
//        player.release();
//    }
}