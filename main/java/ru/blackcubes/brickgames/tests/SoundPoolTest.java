package ru.blackcubes.brickgames.tests;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class SoundPoolTest extends Activity implements View.OnTouchListener {

    SoundPool soundPool;
    int explosionId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setOnTouchListener(this);
        setContentView(textView);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
        soundPool = (new SoundPool.Builder()).setAudioAttributes(attributes).setMaxStreams(20).build();

        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd("sounds/explosion.mp3");
            explosionId = soundPool.load(descriptor, 1);
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                    System.out.println("On load complete. Sample id: " + sampleId + ", status: " + status);
                    soundPool.play(sampleId, 1.0f, 1.0f, 1, -1, 1.0f);
                }
            });
            soundPool.load(assetManager.openFd("sounds/music.mp3"), 1);
        } catch (IOException e) {
            textView.setText("Couldn't load sound effect from asset, " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (explosionId != -1) {
                soundPool.play(explosionId, 0.99f, 0.99f, 1, 0, 0.99f);
            }
        }
        return true;
    }
}
