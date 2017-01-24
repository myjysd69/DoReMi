package net.zeany.doremi;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final int DO_LOW = 0;
    private static final int RE = 1;
    private static final int MI = 2;
    private static final int FA = 3;
    private static final int SOL = 4;
    private static final int LA = 5;
    private static final int SI = 6;
    private static final int DO_HIGH = 7;

    private SoundPool soundPool;
    private int[] soundIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundIds = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(8).build();
        }
        else {
            soundPool = new SoundPool(8, AudioManager.STREAM_NOTIFICATION, 0);
        }

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId, 1f, 1f, 0, 0, 1f);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        for (int i = 0 ; i < soundIds.length ; i++) {
            soundIds[i] = 0;
        }

        soundPool.release();
        soundPool = null;
    }

    public void onButtonClicked(View view) {
        int soundIndex = -1;
        int rawSound = 0;

        switch (view.getId()) {
            case R.id.do_l:
                soundIndex = DO_LOW;
                rawSound = R.raw.do_l;
                break;
            case R.id.re:
                soundIndex = RE;
                rawSound = R.raw.re;
                break;
            case R.id.mi:
                soundIndex = MI;
                rawSound = R.raw.mi;
                break;
            case R.id.fa:
                soundIndex = FA;
                rawSound = R.raw.fa;
                break;
            case R.id.sol:
                soundIndex = SOL;
                rawSound = R.raw.sol;
                break;
            case R.id.la:
                soundIndex = LA;
                rawSound = R.raw.la;
                break;
            case R.id.si:
                soundIndex = SI;
                rawSound = R.raw.si;
                break;
            case R.id.do_h:
                soundIndex = DO_HIGH;
                rawSound = R.raw.do_h;
                break;
        }

        if (soundIndex != -1) {
            if (soundIds[soundIndex] == 0) {
                soundIds[soundIndex] = soundPool.load(MainActivity.this, rawSound, 1);
            } else {
                soundPool.play(soundIds[soundIndex], 1f, 1f, 0, 0, 1f);
            }
        }
    }
}
