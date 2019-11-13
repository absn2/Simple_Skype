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
        private String userName;
        private boolean firstMessage;
        private gui graphicGui;

        public Receive(DatagramSocket clientSocket, gui graphicGui) throws IOException {
            this.clientSocket = clientSocket;
            this.firstMessage = true;
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
                    if (firstMessage) {
                        this.userName = msg;
                        firstMessage = false;
                        System.out.println("O usuário " + this.userName +", acabou de se conectar com você seja gentil!");
                        graphicGui.printConnect(this.userName);

                    } else {
                        System.out.println(this.userName + ": " + msg);
                        graphicGui.print(this.userName, msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }