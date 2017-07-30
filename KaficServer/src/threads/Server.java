/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import exception.ServerException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author elezs
 */
public class Server extends Thread{

    private ServerSocket serverSocket;
    private static int noPort = 9000;
    private List<ClientThread> clients;
    private static boolean active = false;
    private Socket socket = new Socket();
    
    public Server(){
        try {
            serverSocket = new ServerSocket(noPort);
            System.out.println("Kreiran server socket na portu: "+noPort);
            clients = new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Nije kreiran server socket");
        }
    }

    @Override
    public void run() {
        System.out.println("Cekam klijente...");
        while(!serverSocket.isClosed()){
            try {
                if(!socket.isClosed()){
                    socket = new Socket();
                    socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket, clients);
                    clientThread.start();
                    clients.add(clientThread);
                    System.out.println("Novi klijent se povezao!");
                }else{
                    return;
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
            for (ClientThread clientThread : clients) {
                clientThread.getSocket().close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
