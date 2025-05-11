package gameObject;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class SoundPlayer {
    private Clip clip;
    private boolean shouldRepeat = false;

    public SoundPlayer(String pathInClasspath) {
        try {
            InputStream audioSrc = SoundPlayer.class.getResourceAsStream(pathInClasspath);
            if (audioSrc == null) {
                System.err.println("找不到音效：" + pathInClasspath);
                return;
            }
            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP && shouldRepeat) {
                    clip.setFramePosition(0);
                    clip.start();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playOnce() {
        if (clip == null) return;
        shouldRepeat = false;
        clip.setFramePosition(0);
        clip.start();
    }

    public void playOnceThenRepeat() {
        if (clip == null) return;
        shouldRepeat = true;
        clip.setFramePosition(0);
        clip.start();
    }

    public void stop() {
        if (clip != null) {
            shouldRepeat = false;
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
    }

    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
