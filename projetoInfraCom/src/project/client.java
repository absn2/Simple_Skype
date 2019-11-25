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
        Thread envio = new EnviarMensagem(clientIP, graphicUi, clientSocket);

        receive.start();
        status.start();
        envio.start();
    }
}

class Status extends Thread {
    private DatagramSocket statuSocket;
    private gui graphicGui;

    public Status(DatagramSocket statuSocket, gui graphicUi) {
        this.statuSocket = statuSocket;
        this.graphicGui = graphicUi;
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

class EnviarMensagem extends Thread{
    private String IP;
    private gui GUI;
    private DatagramSocket socket;

    public EnviarMensagem(String ip, gui lherme, DatagramSocket skt){
        this.IP = ip;
        this.GUI = lherme;
        this.socket = skt;
    }

    public void run() {
        boolean firstMessage = true;
        while (true) {
            System.out.println(GUI.clientOff);
            while(!GUI.clientOff && !GUI.filaDeEnvio.isEmpty()) {
                String msg = GUI.filaDeEnvio.remove();
                try {
                    InetAddress IPServer = InetAddress.getByName(this.IP);
                    byte[] sendData = msg.getBytes();

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, 8088);
                    this.socket.send(sendPacket);
                    if (!firstMessage) {
                        System.out.println("Você: " + msg);
                    } else {
                        firstMessage = false;
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
           // if(!firstMessage)
              //  GUI.textField1.setText("");
        }
    }
}
