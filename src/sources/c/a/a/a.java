package c.a.a;

import android.media.MediaPlayer;
import java.io.IOException;

/* compiled from: AudioPlayer */
public class a {
    private MediaPlayer mMediaPlayer = new MediaPlayer();
    private String mPath;

    public a(String str) {
        this.mPath = str;
    }

    public void destroy() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
    }

    public boolean isPlaying() {
        return this.mMediaPlayer.isPlaying();
    }

    public void pause() {
        this.mMediaPlayer.pause();
    }

    public void play() {
        this.mMediaPlayer.start();
    }

    public boolean prepare() {
        try {
            this.mMediaPlayer.setDataSource(this.mPath);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.prepare();
            return true;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void resume() {
        this.mMediaPlayer.start();
    }

    public void setLoop(boolean z) {
        this.mMediaPlayer.setLooping(z);
    }

    public void stop() {
        this.mMediaPlayer.stop();
    }
}
