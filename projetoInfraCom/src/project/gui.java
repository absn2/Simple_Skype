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
    private int port;

    public gui(int port, DatagramSocket socket) {
        textArea1.setEditable(false);
        JFrame janela = new JFrame();
        janela.setContentPane(panel1);
        janela.setVisible(true);
        janela.pack();
        janela.setSize(540,540);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.port = port;

        enviarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() > 0) {
                    try {
                        InetAddress IPServer = InetAddress.getByName("localhost");
                        String msg = textField1.getText();
                        byte[] sendData = msg.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                        socket.send(sendPacket);
                        if (port == 8887) {
                            textArea1.append("Usuário A: " + msg + "\n");
                            textField1.setText("");
                        } else {
                            textArea1.append("Usuário B: " + msg + "\n");
                            textField1.setText("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                    try {
                        InetAddress IPServer = InetAddress.getByName("localhost");
                        String msg = textField1.getText();
                        byte[] sendData = msg.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, port);
                        socket.send(sendPacket);
                        if (port == 8887) {
                            textArea1.append("Usuário A: " + msg + "\n");
                            textField1.setText("");
                        } else {
                            textArea1.append("Usuário B: " + msg + "\n");
                            textField1.setText("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void print (String user, String msg) {
        textArea1.append("Usuário " + user + ": " + msg + "\n");
    }

    public void init() {
        textArea1.append("Bem-Vindo ao Bate Papo Boul! \n");
        if (port == 8887) {
            textArea1.append("----> CLIENTE A <---- \n");
        } else {
            textArea1.append("----> CLIENTE B <---- \n");
        }
    }
}
