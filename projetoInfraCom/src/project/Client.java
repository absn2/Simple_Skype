package project;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class Client {

    public static void main(String[] args) throws IOException {
        int port = 8888;
        String servidorIP = "172.20.4.30";
        InetAddress adress = InetAddress.getByName(servidorIP);
        Socket socket = new Socket(adress, port); // inicia conexão com servidor
        DataInputStream entrada = new DataInputStream(socket.getInputStream()); // pega mensagem do servidor

        String [] info = entrada.readUTF().split(":");
        String clientIP = info[0];
        int clientPort = 8888;
        System.out.println("IP do Outro Client: " + clientIP);

        DatagramSocket clientSocket = new DatagramSocket(8888);
        gui graphicUi = new gui(clientIP, clientPort, clientSocket);
        graphicUi.init();

        Thread receive = new Receive(clientSocket, graphicUi);
        receive.start();

        // MANDA E RECEBE AUDIO

        RTPPacket pacoteRTP = new RTPPacket();

        Thread sendAudio = new sendAudio(pacoteRTP, InetAddress.getByName(clientIP), clientPort);
        sendAudio.start();

        Thread receiveAudio = new ReceiveAudio(pacoteRTP);
        receiveAudio.start();
    }
}