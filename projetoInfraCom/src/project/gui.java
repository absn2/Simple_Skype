package project;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.net.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class gui {

    // INTERFACE
    public JTextArea textArea1;
    public JPanel panel1;
    public JTextField textField1;
    public JButton enviarButton;

    // LOGICA
    public String userName;
    public boolean firstMessage;
    public boolean clientOff;

    // INFORMACOES
    public String ip;
    public DatagramSocket socket;
    public Queue<String> filaDeEnvio;
    public int port;


    public gui(DatagramSocket socket) {
        this.ip = "0";
        this.port = 8088;
        this.socket = socket;
        this.clientOff = true;
        this.filaDeEnvio = new LinkedList<>();
        this.firstMessage = true;


        setInterface();

        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() > 0) {
                    filaDeEnvio.add(textField1.getText());
                    textField1.setText("");
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                    filaDeEnvio.add(textField1.getText());
                    textField1.setText("");
                }
            }
        });
    }

   /* public void sendClient (String msg) {
        if (firstMessage) {
            this.textArea1.append("Bem-vindo usúario " + msg + "! \n");
            this.textField1.setText("");
        }
        try {
            InetAddress IPServer = InetAddress.getByName(ip);
            byte[] sendData = msg.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
            this.socket.send(sendPacket);

            if (!this.firstMessage) {
                textArea1.append("Você: " + msg + "\n");
                System.out.println("Você: " + msg);
                textField1.setText("");
            } else {
                this.firstMessage = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

   /* public void sendMessage(String msg){
            if (this.clientOff || this.ip.equals("0")) {
                msgOff.add(msg);
                this.textField1.setText("");
            } else {
                int index = 0;
                while (!msgOff.isEmpty()) {
                    sendClient(msgOff.get(index));
                    msgOff.remove(index);
                    index++;
                }
                sendClient(msg);
            }
    }*/

    public void setInterface(){
        textArea1.setEditable(false);
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(540,540);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void print (String user, String msg) {
        textArea1.append(user + ": " + msg + "\n");
    }

    public void setIp (String ip) {
        this.ip = ip;
    }

    public void printConnect (String user) {
        textArea1.append("O usuário --->" + user +"<--- acabou de se conectar com você seja gentil!\n");
    }

    public void init() {
        textArea1.append("Bem-Vindo ao ZapGram! \n");
        textArea1.append("# Primeiramente digite seu nome de usuário! # \n\n");
    }

    public void setClientOff(String msg) {
        if (msg.equals("OFF")) {
            clientOff = true;
        } else {
            clientOff = false;
        }
    }
}