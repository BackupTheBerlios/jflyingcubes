/*
 * Player.java
 *
 * Created on 24. April 2005, 17:06
 */

package de.jflyingcubes.player.sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;
import javazoom.spi.mpeg.sampled.file.MpegEncoding;


/**
 *
 * @author dm
 */
public class Player {
    
    private SourceDataLine sourceDataLine;
    private AudioFormat audioFormat;
    private AudioInputStream audioInputStream;
    private String filename = "";
    private boolean stop = false;
    private boolean loop = false;
    /** Creates a new instance of Player */
    public Player(String filename) {
        this.filename = filename;
    }
    
    public void play() {
        play(this.loop);
    }
    
    public void play(boolean loop) {
        if (filename == null)
            return;
        this.loop = loop;
        try{
            new PlayThread().start();
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void stop() {
        this.stop = true;
    }
    
    public static void main(String[] arg) {
        Player p = new Player("D:\\Projects\\jflyingcubes\\audio\\welcome.wav");
        p.play();
    }
    
    private class PlayThread extends Thread{
        byte tempBuffer[] = new byte[10000];
        
        public void run(){
            do {
                if (load())
                    play();
            } while (loop && !stop);

        }
        
        private boolean load() {
            try {
                File soundFile = new File(filename);
                if (filename.endsWith(".mp3")) {
                    System.out.println("'mp3' file format is not supported!");
                    System.exit(1);
                } else {
                    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                    audioFormat = audioInputStream.getFormat();
                }
                DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class, audioFormat);

                sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
            return false;
        }
        
        private void play() {
            try{
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                
                int cnt;
                
                    while((cnt = audioInputStream.read(
                            tempBuffer,0,tempBuffer.length)) != -1
                            && stop == false){
                        if(cnt > 0){
                            sourceDataLine.write(tempBuffer, 0, cnt);
                        }
                    }

                sourceDataLine.drain();
                sourceDataLine.close();
            }catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }
}