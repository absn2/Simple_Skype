package project;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.net.*;

public class gui {
    private JTextArea textArea1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton enviarButton;
    private String userName;
    private boolean firstMessage;
    private int port;

    public gui(int port, DatagramSocket socket) {
        this.port = port;
        this.firstMessage = true;
        textArea1.setEditable(false);
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(540,540);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() > 0) {
                    if (firstMessage) {
                        userName = textField1.getText();
                        textArea1.append("Bem-vindo usúario " + "--->" + userName + "<---" + "!\n");
                        textField1.setText("");
                        firstMessage = false;
                        try {
                            InetAddress IPServer = InetAddress.getByName("localhost");
                            String msg = userName;
                            byte[] sendData = msg.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                            socket.send(sendPacket);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            InetAddress IPServer = InetAddress.getByName("localhost");
                            String msg = textField1.getText();
                            byte[] sendData = msg.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                            socket.send(sendPacket);
                            textArea1.append("Você: " + msg + "\n");
                            textField1.setText("");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                    if (firstMessage) {
                        userName = (textField1.getText()).toUpperCase();
                        textArea1.append("Bem-vindo usúario " + userName + "! \n");
                        textField1.setText("");
                        firstMessage = false;
                        try {
                            InetAddress IPServer = InetAddress.getByName("localhost");
                            String msg = userName;
                            byte[] sendData = msg.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                            socket.send(sendPacket);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            InetAddress IPServer = InetAddress.getByName("localhost");
                            String msg = textField1.getText();
                            byte[] sendData = msg.getBytes();
                            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                            socket.send(sendPacket);
                            textArea1.append("Você: " + msg + "\n");
                            textField1.setText("");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
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
