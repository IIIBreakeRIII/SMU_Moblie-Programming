package com.cookandroid.mp3player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewMP3;
    Button btnPlay, btnStop;
    TextView tvMP3;
    ProgressBar pbMP3;

    ArrayList<String> mp3List;
    String selectedMP3;

    String mp3Path = Environment.getExternalStorageDirectory().getPath() + "/" ;
    MediaPlayer mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 MP3 플레이어");
        ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);

        mp3List = new ArrayList<String>();

        File[] listFiles = new File(mp3Path).listFiles();
        String fileName, extName;
        for(File file : listFiles) {
            fileName = file.getName();
            extName = fileName.substring(fileName.length() - 3);
            if (extName.equals((String) "mp3")) {
                mp3List.add(fileName);
            }

            ListView listViewMP3 = (ListView) findViewById(R.id.listViewMP3);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, mp3List);
            listViewMP3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            listViewMP3.setAdapter(adapter);
            listViewMP3.setItemChecked(0, true);

            listViewMP3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    selectedMP3 = mp3List.get(arg2);
                }
            });
            selectedMP3 = mp3List.get(0);

            btnPlay = (Button) findViewById(R.id.btnPlay);
            btnStop = (Button) findViewById(R.id.btnStop);
            tvMP3 = (TextView) findViewById(R.id.tvMP3);
            pbMP3 = (ProgressBar) findViewById(R.id.pbMP3);

            btnPlay.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        mPlayer = new MediaPlayer();
                        mPlayer.setDataSource(mp3Path + selectedMP3);
                        mPlayer.prepare();
                        mPlayer.start();
                        btnPlay.setClickable(false);
                        btnStop.setClickable(true);
                        tvMP3.setText("실행중인 음악 :  " + selectedMP3);
                        pbMP3.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                    }
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mPlayer.stop();
                    mPlayer.reset();
                    btnPlay.setClickable(true);
                    btnStop.setClickable(false);
                    tvMP3.setText("실행중인 음악 :  ");
                    pbMP3.setVisibility(View.INVISIBLE);
                }
            });
            btnStop.setClickable(false);

        }
    }
}