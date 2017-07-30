/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domen.Konobar;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import transfer.ClientTransfer;
import transfer.ServerTransfer;

/**
 *
 * @author elezs
 */
public class Communication {
    
    private static Communication instance;
    private Socket socket;
    ObjectInputStream in;
    ObjectOutputStream out;
    
    private Communication (){
        
    }
    
    public static Communication getInstance(){
        if(instance == null){
            instance = new Communication();
        }
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(this.socket.getOutputStream());
        in = new ObjectInputStream(this.socket.getInputStream());
        System.out.println("Podesio sam out!!!");
    }
    
    public void sendRequest(ClientTransfer kt) throws IOException {
        out.writeUnshared(kt);
    }

    public ServerTransfer getResponse() throws IOException, ClassNotFoundException {
        return (ServerTransfer) in.readUnshared();
    }
    
}
