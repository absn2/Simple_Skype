package project;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class client {
    public static void main(String[] args) throws IOException {

        int port = 8888;
        String servidorIP = "172.20.4.165";
        InetAddress adress = InetAddress.getByName(servidorIP);
        DatagramSocket clientSocket = new DatagramSocket(8088); // msgs do cliente
        DatagramSocket statuSocket = new DatagramSocket(8008); // msgs de status
        Socket socket = new Socket(adress, port); // inicia conexão com servidor
        gui graphicUi = new gui (clientSocket);
        DataInputStream entrada = new DataInputStream(socket.getInputStream()); // pega mensagem do servidor
        String info = entrada.readUTF();
        String clientIP = info;
        System.out.println("IP do Outro client: " + clientIP);
        graphicUi.setIp(clientIP);
        graphicUi.init();
        Thread receive = new Receive(clientSocket, graphicUi);
        Thread status = new Status(statuSocket, graphicUi);
        receive.start();
        status.start();
    }
}

class Status extends Thread {
    private DatagramSocket statuSocket;
    private gui graphicGui;

    public Status(DatagramSocket statuSocket, gui graphicUi) {
        this.statuSocket = statuSocket;
        this.graphicGui = graphicGui;
    }

    public void run() {
        try {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                this.statuSocket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                msg = msg.trim();
                this.graphicGui.setClientOff(msg);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
