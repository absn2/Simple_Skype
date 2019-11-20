package project;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.net.*;

public class gui {

    // INTERFACE
    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton enviarButton;

    // LOGICA
    private String userName;
    private boolean firstMessage;

    // INFORMACOES
    private String ip;
    private DatagramSocket socket;
    private int port;


    public gui(String ip, int port, DatagramSocket socket) {
        this.port = port;
        this.ip = ip;
        this.socket = socket;

        this.firstMessage = true;

        setInterface();

        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() > 0) {
                    sendMessage();
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                    sendMessage();
                }
            }
        });
    }

    public void sendMessage(){
        String msg = "";

        if (firstMessage) {
            this.userName = textField1.getText();
            this.textArea1.append("Bem-vindo usúario " + userName + "! \n");
            this.textField1.setText("");

            msg = userName;
        }
        else{
            msg = textField1.getText();
        }

        try {
            InetAddress IPServer = InetAddress.getByName(ip);
            byte[] sendData = msg.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
            this.socket.send(sendPacket);

            if (!this.firstMessage) {
                textArea1.append("Você: " + msg + "\n");
                textField1.setText("");
            }
            else{
                this.firstMessage = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public void printConnect (String user) {
        textArea1.append("O usuário --->" + user +"<--- acabou de se conectar com você seja gentil!\n");
    }

    public void init() {
        textArea1.append("Bem-Vindo ao ZapGram! \n");
        textArea1.append("# Primeiramente digite seu nome de usuário! # \n\n");
    }
}