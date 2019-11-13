package project;


import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class App {
    public static void main(String[] args) {
        System.out.println("Playing");

        AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
        DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);

        //Thread 1: Record data from mic and copy in a circular buffer
        MicRecord recorder = new MicRecord(format, targetInfo);

        //Thread 2: Read data from ring buffer and play sound
        MicPlayer player = new MicPlayer(format, sourceInfo);

        recorder.start();
        player.start();

        try {
            recorder.join();
            player.join();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
