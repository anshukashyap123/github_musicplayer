package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.ListViewSong);


        runtimePermission();//making a function 1 for runtime permission


    }

    public void runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displaySongs();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();// again again show for permission

                    }
                }).check();


    }


    public ArrayList<File> findSong(File file) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] allFiles = file.listFiles();

        if (allFiles != null) {   //these things make a big eroor be
            for (File singleFile : allFiles) {
                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    arrayList.addAll(findSong(singleFile));
                } else if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {
                    arrayList.add(singleFile);

                }


            }
        }
        return arrayList;

    }//this lines code for read all mp4 songs from device



    void displaySongs() {
        final ArrayList<File> mysong = findSong(Environment.getExternalStorageDirectory());
        items = new String[mysong.size()];
        for (int i = 0; i < mysong.size(); i++) {
            items[i] = mysong.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }

//        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(myadapter);

        cutomAdapter cutomAdapter= new cutomAdapter();
        listView.setAdapter(cutomAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songnName=(String)listView.getItemAtPosition(position);
                startActivity(new Intent(getApplicationContext(),player.class)
                        .putExtra("songs",mysong)
                        .putExtra("songname",songnName)
                        .putExtra("pos",position));
            }
        });


    }//another one for display the song



    class cutomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View myview= getLayoutInflater().inflate(R.layout.list_item, null);
            TextView textsong= myview.findViewById(R.id.t1);
            textsong.setSelected(true);
            textsong.setText(items[position]);
            return myview;
        }
    }
}

//*************************************************************************************************************************