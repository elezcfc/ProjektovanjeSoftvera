/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import constants.Constants;
import controller.ServerController;
import exception.ServerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import param.cfg.ParamConfigurator;
import transfer.ClientTransfer;
import transfer.ServerTransfer;

/**
 *
 * @author elezs
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    //private static int noPort = 9000;
    private List<ClientThread> clients;
    private static boolean active = false;
    private Socket socket = new Socket();
    private ParamConfigurator pc;

    public Server() {
        try {
            pc = new ParamConfigurator();
            int noPort = pc.readPort();
            serverSocket = new ServerSocket(noPort);
            System.out.println("Kreiran server socket na portu: " + noPort);
            clients = new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nije kreiran server socket");
        }
    }

    @Override
    public void run() {
        System.out.println("Cekam klijente...");
        while (!serverSocket.isClosed()) {
            try {
                if (!socket.isClosed()) {
                    socket = new Socket();
                    socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket, clients);
                    clientThread.start();
                    clients.add(clientThread);
                    System.out.println("Novi klijent se povezao!");
//                    clientThread.test();
                }
            } catch (IOException ex) {
                System.out.println("Server se gasi");
            }
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static void setActive(boolean aActive) {
        active = aActive;
    }

    public void zaustaviNiti() {
        try {
            serverSocket.close();
            for (int i = 0; i < clients.size(); i++) {
                ClientThread clientThread = clients.get(i);
                if (clientThread.konobar != null) {
                    clientThread.konobar.setLoggedIn(false);
                    clientThread.exit();
                    clientThread.getSocket().close();
                }
//                clientThread.close();
//                clientThread.getSocket().close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void startCheck() {
//        while (!isInterrupted()) {
//            synchronized(this){
//                try {
//                    System.out.println("Radim proveru");
//                    ServerSocket s = new ServerSocket(9001);
//                    Socket socket = s.accept();
//                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                    ClientTransfer ct = (ClientTransfer) in.readUnshared();
//                    if (!isActive()) {
//                        ServerTransfer st = new ServerTransfer();
//                        st.setSuccesfull(1);
//                        st.setException(new ServerException("Server radi!"));
//                    }
//                } catch (IOException ex) {
//                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }
}
