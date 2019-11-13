package project;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

public class MicRecord extends Thread {

    private AudioFormat format;
    private DataLine.Info targetInfo;

    public MicRecord(AudioFormat format, DataLine.Info targetInfo) {
        this.format = format;
        this.targetInfo = targetInfo;
    }

    public void run() {
        CircularBuffer buf;
        TargetDataLine targetDataLine = null;
        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetDataLine.open(format);
            targetDataLine.start();
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }

        int dataRead = -1;
        byte[] targetData = new byte[targetDataLine.getBufferSize() / 5];

        while(true) {
            try {
                //read from mic
                dataRead = targetDataLine.read(targetData, 0, targetData.length);
                if(dataRead == -1) {
                    System.out.println("Error reading microphone input");
                }
                else {
                    //write to buffer
                    buf = CircularBuffer.getBufferObject();
                    buf.writeToBuffer(targetData);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}