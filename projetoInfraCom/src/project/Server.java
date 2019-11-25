package project;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {

    public static void main(String[] args) {
        int port = 8888;
        try {
            System.out.println("Waiting ");
            ServerSocket tmpSocket = new ServerSocket(port);

            System.out.println("c1");
            Socket socket1 = tmpSocket.accept();

            System.out.println("c2");
            Socket socket2 = tmpSocket.accept();

            System.out.println("c3");

            String IPAdressA = socket1.getRemoteSocketAddress().toString();
            String IPAdressB = socket2.getRemoteSocketAddress().toString();

            System.out.println(IPAdressA.substring(1));

            System.out.println(IPAdressB.substring(1));


            DataOutputStream IPtoClientB = new DataOutputStream (socket2.getOutputStream());

            DataOutputStream IPtoClientA = new DataOutputStream (socket1.getOutputStream());

            IPtoClientA.writeUTF(IPAdressB.substring(1));
            System.out.println("First IP adress sent successcully");
            IPtoClientB.writeUTF(IPAdressA.substring(1));
            System.out.println("de boina");


            socket1.close();
            socket2.close();

            tmpSocket.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
