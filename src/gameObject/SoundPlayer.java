package gameObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 * 音效播放類別，負責載入並播放指定路徑的音效檔案。
 * 支援單次播放及循環播放功能。
 */
public class SoundPlayer {
    /** 音效剪輯物件 */
    private Clip clip;

    /** 是否需要循環播放 */
    private boolean shouldRepeat = false;

    /**
     * 建構子，從 classpath 中指定的路徑載入音效檔案。
     * 
     * @param pathInClasspath 音效檔案在 classpath 中的路徑（如 "/shoot.wav"）
     */
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

            // 監聽音效播放結束事件，若設定循環播放，則重新開始播放
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

    /**
     * 播放音效一次。
     * 若音效正在播放，會從頭開始播放。
     */
    public void playOnce() {
        if (clip == null) return;
        shouldRepeat = false;
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * 播放音效一次，播放結束後自動循環重複播放。
     */
    public void playOnceThenRepeat() {
        if (clip == null) return;
        shouldRepeat = true;
        clip.setFramePosition(0);
        clip.start();
    }

    /**
     * 停止播放音效，並重置播放位置。
     */
    public void stop() {
        if (clip != null) {
            shouldRepeat = false;
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
    }

    /**
     * 檢查音效是否正在播放中。
     * 
     * @return 正在播放時回傳 true，否則回傳 false
     */
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
