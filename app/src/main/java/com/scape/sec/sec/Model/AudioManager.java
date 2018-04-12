package com.scape.sec.sec.Model;

import android.media.MediaPlayer;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.scape.sec.sec.R;

import java.io.IOException;

/**
 * Created by Me on 26/10/2016.
 */

public class AudioManager {
    MediaPlayer mp;
    String _keyAudio;
    FloatingActionButton fab;

    public  AudioManager(String key,FloatingActionButton fab){
        _keyAudio = key;
        this.fab = fab;
    }

    public void play(){
        try {
            if(mp == null){
                mp = new MediaPlayer();
                mp.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
                mp.setDataSource(_keyAudio);

                mp.prepareAsync();

                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                        fab.setImageResource(R.drawable.ic_launcher_background);
                    }
                });
            }else {
                mp.stop();
                mp.release();
                mp = null;
                fab.setImageResource(R.drawable.ic_launcher_background);
            }

        } catch (IllegalStateException ise) {
            Log.e("play", "prepare() failed" + ise.toString());
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        }catch (NullPointerException npe){
            Log.e("NullPointerException", npe.toString());
        }
    }
}
