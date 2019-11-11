package project;

import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

    public class Receive extends Thread {
        private DatagramSocket clientSocket;
        private gui graphicGui;

        public Receive(DatagramSocket clientSocket, gui graphicGui) throws IOException {
            this.clientSocket = clientSocket;
            this.graphicGui = graphicGui;
        }

        public void run() {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    this.clientSocket.receive(receivePacket);
                    String msg = new String(receivePacket.getData());
                    msg = msg.trim();
                    if (clientSocket.getLocalPort() == 8888) {
                        System.out.println("B: " + msg);
                        graphicGui.print("B",msg);
                    } else {
                        System.out.println("A: " + msg);
                        graphicGui.print("A",msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }