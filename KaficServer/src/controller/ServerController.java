/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dbbroker.DBBroker;
import domen.AbstractObject;
import domen.Konobar;
import domen.Pice;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Sto;
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;
import system_operation.SODeleteBill;
import system_operation.SODeleteStavka;
import system_operation.SODeleteTable;
import system_operation.SODeleteWaiter;
import system_operation.SOLoadBills;
import system_operation.SOLoadDrinks;
import system_operation.SOLoadStavke;
import system_operation.SOLoadTables;
import system_operation.SOLoadWaiterList;
import system_operation.SOLogin;
import system_operation.SONewBill;
import system_operation.SONewDrink;
import system_operation.SONewTable;
import system_operation.SOSaveWaiter;
import system_operation.SOSearchBills;
import system_operation.SOSearchDrinks;
import system_operation.SOSearchWaiter;
import transfer.ClientTransfer;

/**
 *
 * @author elezs
 */
public class ServerController {

    private static ServerController instance;
    private List<AbstractObject> listUsers;

    private ServerController() {

    }

    public static ServerController getServerController() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public AbstractObject logInUser(Konobar konobar) throws ServerException {
        SOLogin sol = new SOLogin();
        sol.setParamKonobar(konobar);
        sol.runOperation();
        return sol.getLoggedInUser();
//        saveLoggedInUser(sol.getLoggedInUser());
    }

    public List<AbstractObject> getUserList() throws ServerException {
        if (listUsers == null) {
            listUsers = getUsers();
        }
        return listUsers;
    }

    public List<AbstractObject> getUsers() throws ServerException {
        SOLoadWaiterList load = new SOLoadWaiterList();
        load.runOperation();
        return load.getList();
    }

    public AbstractObject saveUser(AbstractObject konobar) throws ServerException {
        SOSaveWaiter sosu = new SOSaveWaiter(konobar);
        sosu.runOperation();
        return sosu.getKonobar();
    }

    public void deleteUser(AbstractObject user) throws ServerException {
        SODeleteWaiter sodu = new SODeleteWaiter(user);
        sodu.runOperation();
    }

    public AbstractObject createDrink(AbstractObject parametar) throws ServerException {
        SONewDrink sond = new SONewDrink(parametar);
        sond.runOperation();
        return sond.getPice();
    }

    public List<AbstractObject> getDrinks() throws ServerException {
        SOLoadDrinks sold = new SOLoadDrinks();
        sold.runOperation();
        return sold.getListOfDrinks();
    }

    public List<AbstractObject> getTables() throws ServerException {
        SOLoadTables solt = new SOLoadTables();
        solt.runOperation();
        return solt.getTables();
    }

    public List<AbstractObject> getBills(Sto s) throws ServerException {
        SOLoadBills solb = new SOLoadBills(s);
        solb.runOperation();
        return solb.getBills();
    }

    public Racun createBill(Racun r) throws ServerException {
        SONewBill sonb = new SONewBill(r);
        sonb.runOperation();
        return sonb.getRacun();
    }

    private void saveLoggedInUser(AbstractObject loggedInUser) {
        for (AbstractObject abs : listUsers) {
            Konobar k = (Konobar) abs;
            if (k.equals(loggedInUser)) {
                k.setLoggedIn(true);
            }
        }
    }

    private List<Konobar> transformList(List<AbstractObject> absList) {
        List<Konobar> list = new ArrayList<>();

        for (AbstractObject absK : absList) {
            Konobar k = (Konobar) absK;
            list.add(k);
        }

        return list;
    }

    public void logoutUser(Konobar konobar) {
        for (AbstractObject abs : listUsers) {
            Konobar k = (Konobar) abs;
            if (k.equals(konobar)) {
                k.setLoggedIn(false);
            }
        }
    }

    public String deleteBill(Racun racun) throws ServerException {
        SODeleteBill sodb = new SODeleteBill(racun);
        sodb.runOperation();
        return "sve ful";
    }

    public String deleteStavka(StavkaRacuna stavkaRacuna) throws ServerException {
        SODeleteStavka sods = new SODeleteStavka(stavkaRacuna);
        sods.runOperation();
        return "sve ful";
    }

    public List<StavkaRacuna> getStavke(Racun r) throws ServerException {
        SOLoadStavke sols = new SOLoadStavke(r);
        sols.runOperation();
        return sols.getStavke();
    }

    public List<AbstractObject> searchKonobar(String rec) throws ServerException {
        SOSearchWaiter sosk = new SOSearchWaiter(rec);
        sosk.runOperation();
        return sosk.getFilteredList();
    }

    public List<AbstractObject> searchDrink(String rec) throws ServerException {
        SOSearchDrinks sosd = new SOSearchDrinks(rec);
        sosd.runOperation();
        return sosd.getFilteredList();
    }

    public List<AbstractObject> searchBill(String rec, Sto s) throws ServerException {
        SOSearchBills sosb = new SOSearchBills(rec, s);
        sosb.runOperation();
        return sosb.getFilteredList();
    }

    public AbstractObject createTable(Sto sto) throws ServerException {
        SONewTable sont = new SONewTable(sto);
        sont.runOperation();
        return sont.getSto();
    }

    public Sto deleteTable(Sto deleteTable) throws ServerException {
        SODeleteTable sodt = new SODeleteTable(deleteTable);
        sodt.runOperation();
        return sodt.getTable();
    }

    public Sto updateTableSum(Sto sto) throws ServerException {
        DBBroker dbb = new DBBroker();
        dbb.openConnection();
        Sto table = (Sto) dbb.saveOrUpdateObject(sto);
        dbb.commitTransaction();
        dbb.closeConnection();
        return table;
    }

    public String resetTables() throws ServerException {
        List<AbstractObject> tables = getTables();
        double iznos = 0;
        for (AbstractObject table : tables) {
            Sto s = (Sto) table;
            iznos += s.getUkupanDnevniIznos();
            s.setZauzet(false); //*
            s.setUkupanDnevniIznos(0); //*
//            List<AbstractObject> bills = getBills(s);
//            for (AbstractObject bill : bills) {
//                Racun r = (Racun) bill;
//                SODeleteBill sodb = new SODeleteBill(r);
//                sodb.runOperation();
//            }
//            s.setUkupanDnevniIznos(0);
//            updateTableSum(s);
        }
        return "Ukupan prihod za danasnji dan je: "+iznos;
    }
}
