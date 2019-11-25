package project;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.net.*;
import java.util.ArrayList;


public class gui {

    // INTERFACE
    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton enviarButton;

    // LOGICA
    private String userName;
    private boolean firstMessage;
    private boolean clientOff;

    // INFORMACOES
    private String ip;
    private DatagramSocket socket;
    private ArrayList<String> msgOff;
    private int port;


    public gui(DatagramSocket socket) {
        this.ip = "0";
        this.port = 8888;
        this.socket = socket;
        this.clientOff = false;
        this.msgOff = new ArrayList<String>();
        this.firstMessage = true;


        setInterface();

        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() > 0) {
                    sendMessage(textField1.getText());
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                    sendMessage(textField1.getText());
                }
            }
        });
    }

    public void sendClient (String msg) {
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
                textField1.setText("");
            } else {
                this.firstMessage = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendMessage(String msg){
            if (this.clientOff || this.ip.equals("0")) {
                msgOff.add(msg);
            } else {
                int index = 0;
                while (!msgOff.isEmpty()) {
                    sendClient(msgOff.get(index));
                    msgOff.remove(index);
                    index++;
                }
                sendClient(msg);
            }
    }

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
            clientOff = false;
        } else {
            clientOff = true;
        }
    }
}