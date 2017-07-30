/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import communication.Communication;
import constants.Constants;
import domen.AbstractObject;
import domen.Konobar;
import domen.Pice;
import domen.Racun;
import domen.Sto;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;
import transfer.ClientTransfer;
import transfer.ServerTransfer;

/**
 *
 * @author elezs
 */
public class Controller {

    private static Controller instance;

    private Controller() {

    }

    public static Controller getControllerInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public AbstractObject logInUser(String username, String password) throws Exception {
        System.out.println("Logovanje korisnika");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.LOG_IN_USER);
        Konobar k = new Konobar(username, password);
        ct.setParametar(k);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        System.out.println(st.getSuccesfull());
        if (st.getSuccesfull() == 1) {
            return (AbstractObject) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public Pice createDrink(Pice p) throws Exception {
        System.out.println("Kreiram pice");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.CREATE_DRINK);
        ct.setParametar(p);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (Pice) st.getData();
        } else {
//            throw new ServerException("Greska pri kreiranju pica!");
            Exception e = st.getException();
            throw e;
        }
    }

    public List<AbstractObject> getDrinks() throws Exception {
        System.out.println("Dohvatam listu pica");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.GET_DRINKS);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (List<AbstractObject>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public String logOutUser() throws Exception {
        System.out.println("Log out.");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.LOG_OUT_USER);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (String) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public List<AbstractObject> getTables() throws Exception {
        System.out.println("Uzimam stolove");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.GET_TABLES);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (List<AbstractObject>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public List<Racun> getBills() throws Exception {
        System.out.println("Uzimam racune");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(6);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (List<Racun>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public List<AbstractObject> getBills(Sto s) throws Exception {
        System.out.println("Uzimam racune");
        ClientTransfer ct = new ClientTransfer();
        ct.setParametar(s);
        ct.setOperation(Constants.GET_BILLS);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            if (st.getData() instanceof String) {
                throw new ServerException((String) st.getData());
            }
            return (List<AbstractObject>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        } //To change body of generated methods, choose Tools | Templates.
    }

    public Racun createBill(Racun racun) throws Exception {
        System.out.println("Kreiram racun");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.CREATE_BILL);
        ct.setParametar(racun);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (Racun) st.getData();
        } else {
//            throw new ServerException("Greska pri kreiranju pica!");
            Exception e = st.getException();
            throw e;
        }
    }

    public String deleteBill(Racun r) throws Exception {
        System.out.println("Brisem racun");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.DELETE_BILL);
        ct.setParametar(r);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (String) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public List<AbstractObject> searchDrinks(String filterWord) throws Exception {
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.SEARCH_DRINK);
        ct.setParametar(filterWord);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (List<AbstractObject>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public List<AbstractObject> searchBills(String filterWord) throws Exception {
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.SEARCH_BILL);
        ct.setParametar(filterWord);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (List<AbstractObject>) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public Sto createTable(Sto sto) throws Exception {
        System.out.println("Kreiram sto");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.CREATE_TABLE);
        ct.setParametar(sto);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (Sto) st.getData();
        } else {
//            throw new ServerException("Greska pri kreiranju pica!");
            Exception e = st.getException();
            throw e;
        }
    }

    public Sto deleteTable(Sto sto) throws Exception {
        System.out.println("Brisem racun");
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.DELETE_TABLE);
        ct.setParametar(sto);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (Sto) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public Sto updateTable(Sto sto) throws Exception {
        System.out.println("Updateujem sto");
        Sto t = new Sto();
        t.setStoID(sto.getStoID());
        t.setUkupanDnevniIznos(sto.getUkupanDnevniIznos());
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.UPDATE_TABLE);
        System.out.println(t.getUkupanDnevniIznos());
        ct.setParametar(t);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            return (Sto) st.getData();
        } else {
            Exception e = st.getException();
            throw e;
        }
    }

    public String resetTables() throws Exception{
        System.out.println("Resetujem stolove");
        String poruka = "";
        ClientTransfer ct = new ClientTransfer();
        ct.setOperation(Constants.RESET_TABLES);
        Communication.getInstance().sendRequest(ct);
        ServerTransfer st = Communication.getInstance().getResponse();
        if (st.getSuccesfull() == 1) {
            poruka = (String) st.getData();
            return poruka;
        } else {
            Exception e = st.getException();
            throw e;
        }
    }
}
