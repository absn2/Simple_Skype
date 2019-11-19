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
        String servidorIP = "172.20.4.85";
        InetAddress adress = InetAddress.getByName(servidorIP);
        Socket socket = new Socket(adress, port); // inicia conexão com servidor
        DataInputStream entrada = new DataInputStream(socket.getInputStream()); // pega mensagem do servidor

        String [] info = entrada.readUTF().split("/");
        String clientIP = info[0];
        int clientPort = Integer.parseInt(info[1]);
        System.out.println("IP do Outro Client: " + clientIP);
        System.out.println("Porta do Outro Client: " + clientPort);

        DatagramSocket clientSocket = new DatagramSocket(clientPort);
        gui graphicUi = new gui(clientPort, clientSocket, clientIP);
        graphicUi.init();
        Thread receive = new Receive(clientSocket, graphicUi);
        receive.start();
    }
}