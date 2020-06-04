import sun.audio.*;

import javax.sound.sampled.*;
import java.io.*;

public class SoundGame {

    private String path;

    public String getPath() {
        return path;
    }

    public SoundGame() {
        path = "Audio\\NjamShort2.wav";
    }

    public void PlayMusicSDL(String file) {

        SourceDataLine clipSDL = null;
        AudioInputStream ais = null;
        byte[] b = new byte[2048];
        try {
            File f = new File (file);
            ais = AudioSystem.getAudioInputStream (f);
            AudioFormat af = ais.getFormat ();
            DataLine.Info info = new DataLine.Info (SourceDataLine.class, af);
            if (AudioSystem.isLineSupported (info)) {
                clipSDL = (SourceDataLine) AudioSystem.getLine (info);
                clipSDL.open (af);
                clipSDL.start ();
                int num = 0;
                while ((num = ais.read (b)) != -1)
                    clipSDL.write (b, 0, num);
                clipSDL.drain ();
                ais.close ();
                clipSDL.stop ();
                clipSDL.close ();
            } else {
                System.exit (0);
            }
        } catch (Exception e) {
            System.out.println (e);
        }
    }
}
