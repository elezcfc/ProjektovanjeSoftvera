/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import controller.ServerController;
import domen.AbstractObject;
import domen.Konobar;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Sto;
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLoadBills extends AbstractSO {

    private List<AbstractObject> bills;
    private Sto table;

    public SOLoadBills() {
    }

    public SOLoadBills(Sto table) {
        this.table = table;
        bills = new ArrayList<>();
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        List<AbstractObject> billsAbs = dBBroker.getObjectByFK(new Racun(), String.valueOf(table.getStoID()));

        for (AbstractObject billAbs : billsAbs) {
            double iznos = 0;
            Racun bill = (Racun) billAbs;
            List<StavkaRacuna> stavke = ServerController.getServerController().getStavke(bill);
            for (StavkaRacuna stavka : bill.getStavkeRacuna()) {
                if (stavka.getRacun().getRacunID() == bill.getRacunID()) {
                    stavke.add(stavka);
                }
            }
            for (StavkaRacuna stavkaRacuna : stavke) {
                stavkaRacuna.setRacun(bill);
                bill.getStavkeRacuna().add(stavkaRacuna);
                iznos += stavkaRacuna.getPice().getCena();
                bill.setIznos(iznos);
            }
            bills.add(bill);
        }
        List<AbstractObject> konobari = ServerController.getServerController().getUsers();
        for (AbstractObject abstractObject : bills) {
            Racun r = (Racun) abstractObject;
            List<Konobar> kons = new ArrayList<>();
            for (AbstractObject konobar : konobari) {
                Konobar k = (Konobar) konobar;
                if(r.getKonobar().getKonobarID() == k.getKonobarID()){
                    r.setKonobar(k);
                }
            }

        }
    }

    public List<AbstractObject> getBills() {
        return bills;
    }

}
