package project;
import java.io.DataOutputStream;
import java.net.*;

public class server extends Thread {

    public static void main(String[] args) {
        int port = 8888;
        int port2 = 8887;
        try {
            ServerSocket tmpsocket = new ServerSocket(port);
            ServerSocket tmpsocket2 = new ServerSocket(port2);

            System.out.println("Aguardando cliente 1!");
            Socket socket = tmpsocket.accept();
            System.out.println("Cliente 1 conectado!\n");

            System.out.println("Aguardando cliente 2!");
            Socket socket2 = tmpsocket2.accept();
            System.out.println("Cliente 2 conectado!\n");

            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            saida.writeUTF(Integer.toString(socket2.getLocalPort()));

            DataOutputStream saida2 = new DataOutputStream(socket2.getOutputStream());
            saida2.writeUTF(Integer.toString(socket.getLocalPort()));

            System.out.println("Servidor Fechado!");
            tmpsocket.close();
            tmpsocket2.close();
        } catch (BindException e) {
            System.out.println("Endereco em uso");
        } catch (Exception e) {
            System.out.println("Error: "+ e);
        }
    }
}
