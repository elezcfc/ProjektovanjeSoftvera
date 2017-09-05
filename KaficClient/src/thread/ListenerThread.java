/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import communication.Communication;
import constants.Constants;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import transfer.ClientTransfer;
import transfer.ServerTransfer;
import view.LoginFrm;

/**
 *
 * @author elezs
 */
public class ListenerThread extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ServerSocket serverSocket;

    public ListenerThread(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("sdf");
    }

    public ListenerThread() {
        try {
            int port = 9001;
            serverSocket = new ServerSocket(port);
            System.out.println(port);
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        try {
            socket = serverSocket.accept();
            System.out.println("Soket osluskuje");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            ClientTransfer ct = (ClientTransfer) in.readUnshared();
            if (ct.getOperation() == 404) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    window.dispose();
                    socket.close();
                }
                LoginFrm lf = new LoginFrm();
                JOptionPane.showMessageDialog(lf, "Server je ugasen!");
                lf.setVisible(true);
                serverSocket.close();
            }else{
                System.out.println("Server radi");
            }
        } catch (IOException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//while (!isInterrupted() ||  !socket.isClosed()) {
//            System.out.println("Lisener pokrenut");
//            try {
//                sleep(1000);
//                ServerTransfer st = (ServerTransfer) Communication.getInstance().getResponse();
//                System.out.println("Primio  obj");
//                if(st.getData().equals(404)){
//                    Window[] windows = Window.getWindows();
//                    for (Window window : windows) {
//                        window.dispose();
//                    }
//                    LoginFrm lf = new LoginFrm();
//                    JOptionPane.showMessageDialog(lf, "Server je ugasen!");
//                    stopped = true;
//                    lf.setVisible(true);
//                    throw  new ServerException("sdf");
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
