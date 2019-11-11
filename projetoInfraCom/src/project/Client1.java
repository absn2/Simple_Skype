package project;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client1 {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int port = 8888;
        String adress = "localhost";
        Socket socket = new Socket(adress, port);
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        String portClient = entrada.readUTF();
        System.out.println("Cliente B:");
        System.out.println("Porta do Cliente A: " + portClient);
        DatagramSocket clientSocket = new DatagramSocket(8888);
        gui graphicUi = new gui(Integer.parseInt(portClient), clientSocket);
        graphicUi.init();
        Thread receive = new Receive(clientSocket, graphicUi);
        //Thread send = new Send(Integer.parseInt(portClient), clientSocket);
        receive.start();
        //send.start();
    }
}