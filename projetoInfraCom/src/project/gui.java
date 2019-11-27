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
    private JTextArea statusCliente;
    private JTextArea IPdoOutro;

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
                enviarMsg();
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()==KeyEvent.VK_ENTER && textField1.getText().length() > 0) {
                enviarMsg();
            }
            }
        });
    }
    public void enviarMsg(){
        if (textField1.getText().length() > 0) {
            filaDeEnvio.add(textField1.getText());
            if (firstMessage) {
                textArea1.append("Bem-vindo usúario " + textField1.getText() + "! \n");
                firstMessage = false;
            } else {
                textArea1.append("Você: " + textField1.getText() + "\n");
            }
            textField1.setText("");
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
        this.textArea1.append(user + ": " + msg + "\n");
    }

    public void setIp (String ip) {
        this.ip = ip;
        this.IPdoOutro.setText(ip);
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
            this.statusCliente.setText("O outro cliente esta Offline");
        } else {
            clientOff = false;
            this.statusCliente.setText("O outro cliente esta Online");
        }
    }
}