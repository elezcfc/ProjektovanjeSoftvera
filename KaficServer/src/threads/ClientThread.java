/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import com.sun.corba.se.impl.io.OutputStreamHook;
import constants.Constants;
import controller.ServerController;
import domen.AbstractObject;
import domen.Konobar;
import domen.Pice;
import domen.Racun;
import domen.Sto;
import exception.ServerException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerError;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ClientTransfer;
import transfer.ServerTransfer;

/**
 *
 * @author elezs
 */
class ClientThread extends Thread {

    private Socket socket;
    private List<ClientThread> loggedInClients;
    ObjectInputStream in;
    ObjectOutputStream out;
    Konobar konobar;
    AbstractObject pice;

    ClientThread(Socket socket, List<ClientThread> clients) {
        this.socket = socket;
        this.loggedInClients = clients;
    }

    @Override
    public void run() {
        System.out.println("Klijent thread pokrenut!");
        int sto = 0;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            while (!isInterrupted() || !socket.isClosed()) {
                System.out.println("Cekam objekat!");
                ClientTransfer ct = (ClientTransfer) in.readUnshared();
                ServerTransfer st = new ServerTransfer();
                try {
                    int operation = ct.getOperation();
                    System.out.println(operation);
                    Sto s = new Sto();
                    switch (operation) {
                        case Constants.LOG_IN_USER:
                            konobar = (Konobar) ServerController.getServerController().logInUser((Konobar) ct.getParametar());
                            konobar.setLoggedIn(true);
                            st.setData(konobar);
                            break;
                        case Constants.LOG_OUT_USER:
                            konobar.setLoggedIn(false);
                            loggedInClients.remove(this);
                            ServerController.getServerController().logoutUser(konobar);
                            st.setData("Uspesno ste se izlogovali.");
                            break;
                        case Constants.CREATE_DRINK:
                            pice = ServerController.getServerController().createDrink((AbstractObject) ct.getParametar());
                            if (pice != null) {
                                st.setData(pice);
                            }
                            break;
                        case Constants.GET_DRINKS:
                            List<AbstractObject> listOfDrinks = ServerController.getServerController().getDrinks();
                            st.setData(listOfDrinks);
                            break;
                        case Constants.GET_TABLES:
                            List<AbstractObject> listOfTables = ServerController.getServerController().getTables();
                            st.setData(listOfTables);
                            break;
                        case Constants.GET_BILLS:
                            s = (Sto) ct.getParametar();
                            sto = s.getStoID();
                            List<AbstractObject> listOfBills = ServerController.getServerController().getBills(s);
                            if (listOfBills == null || listOfBills.isEmpty()) {
                                st.setData("Ne postoji trazena vrednost!");
                            } else {
                                st.setData(listOfBills);
                            }
                            break;
                        case Constants.CREATE_BILL:
                            Racun r = (Racun) ct.getParametar();
                            r.setKonobar(konobar);
                            Racun racunUpd = ServerController.getServerController().createBill(r);
                            if (racunUpd != null) {
                                st.setData(racunUpd);
                            }
                            break;
                        case Constants.DELETE_BILL:
                            Racun racun = (Racun) ct.getParametar();
                            String message = ServerController.getServerController().deleteBill(racun);
                            if (message != null) {
                                st.setData(message);
                            }
                            break;
                        case Constants.SEARCH_DRINK:
                            List<AbstractObject> filteredList = ServerController.getServerController().searchDrink((String) ct.getParametar());
                            if (filteredList != null) {
                                st.setSuccesfull(1);
                                st.setData(filteredList);
                            }
                            break;
                        case Constants.SEARCH_BILL:
                            List<AbstractObject> filteredListBill = ServerController.getServerController().searchBill((String) ct.getParametar(), new Sto(sto, 0));
                            if (filteredListBill != null) {
                                st.setSuccesfull(1);
                                st.setData(filteredListBill);
                            } else {
                                st.setSuccesfull(0);
                                st.setException(new ServerException("Nije nadjen ni jedan rezultat!"));
                            }
                            break;
                        case Constants.CREATE_TABLE:
                            AbstractObject absSto = ServerController.getServerController().createTable((Sto) ct.getParametar());
                            if (absSto == null) {
                                st.setSuccesfull(0);
                                st.setException(new ServerException("Neuspesno kreiranje stola!"));
                            } else {
                                st.setData(absSto);
                            }
                            break;
                        case Constants.DELETE_TABLE:
                            Sto deleteTable = (Sto) ct.getParametar();
                            Sto table = ServerController.getServerController().deleteTable(deleteTable);
                            if (table == null) {
                                st.setSuccesfull(0);
                                st.setException(new ServerException("Neuspesno brisanje stola"));
                            } else {
                                st.setSuccesfull(1);
                                st.setData(table);
                            }
                            break;
                        case Constants.UPDATE_TABLE:
                            Sto stoo = (Sto) ct.getParametar();
                            Sto updateTable = ServerController.getServerController().updateTableSum(stoo);
                            if (updateTable == null) {
                                st.setSuccesfull(0);
                                st.setException(new ServerException("Neuspesan update"));
                            } else {
                                st.setSuccesfull(1);
                                st.setData(updateTable);
                            }
                            break;
                        case Constants.RESET_TABLES:
                            String poruka = ServerController.getServerController().resetTables();
                            if (poruka != null && poruka != "") {
                                st.setSuccesfull(1);
                                st.setData(poruka);
                            } else {
                                st.setSuccesfull(0);
                                st.setException(new ServerException("Reset stolova nije uspeo!"));
                            }
                        case Constants.CHECK:
                            st.setSuccesfull(1);
                            st.setException(new ServerException("Server radi!"));
                    }
                    st.setSuccesfull(1);

                } catch (ServerException ex) {
                    st.setSuccesfull(-1);
                    st.setException(ex);
                }
//                } catch (Throwable ex) {
//                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
                out.writeUnshared(st);
            }
//            ServerTransfer st = new ServerTransfer();
//            st.setException(new ServerException("Server ne radi!"));
//            out.writeUnshared(st);
//            socket.close();
        } catch (SocketException ex) {
            try {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Klijent odlazi...");
                ServerController.getServerController().logoutUser(konobar);
//                sendNotification();
//                in.close();
//                out.close();
                socket.close();
                loggedInClients.remove(this);
            } catch (IOException ex1) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

//    void close() throws IOException {
//        ServerTransfer st = new ServerTransfer();
//        st.setException(new ServerException("Server ne radi!"));
//        out.writeUnshared(st);
//        socket.close();
//    }
//    private void sendNotification() throws IOException {
//        
//        Socket socket = ss.accept();
//        ObjectOutputStream out = (ObjectOutputStream) socket.getOutputStream();
//        ServerTransfer st = new ServerTransfer();
//        st.setException(new ServerException("server ne radi"));
//        st.setData("Server ne radi");
//        out.writeUnshared(st);
//    }
    void exit() throws IOException {
        int port = 9001;
        Socket socket = new Socket("localhost", port);
        System.out.println("Postavljen soket");
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(404);
        out.writeUnshared(ct);
        out.close();
        socket.close();
    }
    
    public void test(){
        try {
            Socket socket = new Socket("localhost", 2000);
            System.out.println("Postavljen soket");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ClientTransfer ct = new ClientTransfer();
            ct.setOperation(222);
            out.writeUnshared(ct);
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
//        Runnable runnable = new Runnable() {
//            @Override
//            public synchronized void run() {
//                try {
//                    ServerSocket sSocket = new ServerSocket(9001);
//                    Socket socket = sSocket.accept();
//                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                    ServerTransfer st = new ServerTransfer();
//                    st.setException(new ServerException("server ne radi"));
//                    st.setData("Server ne radi");
//                    out.writeUnshared(st);
//                    out.flush();
//                    System.out.println("Poslat info");
//                    out.close();
//                    socket.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        };