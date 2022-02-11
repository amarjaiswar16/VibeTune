package com.amar.vibetune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   // RecyclerView recyclerView;
    ListView list;
    String [] songName = {"main","look","justin","market","relation","reakj","adfkj","asdlkj",
            "main","look","justin","market","relation","reakj","adfkj","asdlkj"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // recyclerView = findViewById(R.id.recycler);
        list = findViewById(R.id.listView);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //Toast.makeText(MainActivity.this, "Runtime Permission Given", Toast.LENGTH_SHORT).show();
                        ArrayList<File> mySongs = fetchSongs(Environment.getExternalStorageDirectory());
                        String [] items = new String[mySongs.size()];
                        for(int i=0;i<mySongs.size();i++){
                            items[i] = mySongs.get(i).getName().replace("-128-kbps-sound","").replace(".mp3","");
                            //items[i] = String.valueOf(mySongs.get(i).getName().subSequence(0,20));

                        }

                         ListSongAdapter listSongAdapter = new ListSongAdapter(MainActivity.this,items);
                         list.setAdapter(listSongAdapter);
                         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                             @Override
                             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                 Intent intent = new Intent(MainActivity.this,SongActivity.class);
                                 String currentSong = list.getItemAtPosition(i).toString();
                                 intent.putExtra("songList",mySongs);
                                 intent.putExtra("currentSong",currentSong);
                                 intent.putExtra("position",i);
                                 startActivity(intent);

                             }
                         });
//                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                        recyclerView.setAdapter(new SongAdapter(items));
//                        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
//
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                       permissionToken.continuePermissionRequest();
                    }
                })
                .check();




    }

    public ArrayList<File> fetchSongs(File file) {
        ArrayList arrayList = new ArrayList();
        File [] songs = file.listFiles();
        if(songs != null) {
            for(File myFile: songs) {
                 if(!myFile.isHidden() && myFile.isDirectory()) {
                     arrayList.addAll(fetchSongs(myFile));
                 }else{
                     if(myFile.getName().endsWith(".mp3") && !myFile.getName().startsWith(".")) {
                         arrayList.add(myFile);
                     }
                 }
            }
        }
        return arrayList;
    }
}