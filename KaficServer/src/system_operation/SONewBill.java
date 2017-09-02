/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Pice;
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
public class SONewBill extends AbstractSO {

    private Racun racun;
    private Sto sto;

    public SONewBill(Racun r) {
        racun = r;
        sto = racun.getSto();
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        if (racun.getRacunID() == 0) {
            Racun racun1 = (Racun) dBBroker.saveOrUpdateObject(racun);
            dBBroker.commitTransaction();
            for (StavkaRacuna st : racun.getStavkeRacuna()) {
                st.setRacun(racun1);
                StavkaRacuna s = (StavkaRacuna) dBBroker.saveOrUpdateObject(st);
//                racun1.getStavkeRacuna().add(s);
            }
            racun = racun1;
//            calculateBill(racun);
            sto.getDnevniRacuni().add(racun);
//            updateSto();
        } else {
            sto.getDnevniRacuni().remove(racun);
            updateRacun();
        }
    }

    private void updateRacun() throws ServerException {
        double iznos;
        List<StavkaRacuna> stavke = ucitajStavke();
        List<StavkaRacuna> noveStavke = new ArrayList<>();
        for (StavkaRacuna s : racun.getStavkeRacuna()) {
            if (!stavke.contains(s)) {
//                s.setBrStavkeRacuna(0);
                StavkaRacuna st = (StavkaRacuna) dBBroker.saveOrUpdateObject(s);
                noveStavke.add(st);
            }else {
                int index = stavke.indexOf(s);
                if (s.getKolicina() != stavke.get(index).getKolicina()) {
                    StavkaRacuna st = (StavkaRacuna) dBBroker.saveOrUpdateObject(s);
                    noveStavke.add(st);
                }
            }
        }
        for (StavkaRacuna stavka : stavke) {
            for (StavkaRacuna s : racun.getStavkeRacuna()) {
                if (!racun.getStavkeRacuna().contains(stavka)) {
                    if (stavka.getRacun().getRacunID() == s.getRacun().getRacunID()) {
                        dBBroker.deleteObject(stavka);
                    }
                }

            }
        }
//        calculateBill(racun);
        racun = (Racun) dBBroker.saveOrUpdateObject(racun);
//        sto.getDnevniRacuni().add(racun);
        //updateSto();
    }

    private List<StavkaRacuna> ucitajStavke() throws ServerException {
        List<AbstractObject> absStavke = dBBroker.getAllObjects(new StavkaRacuna());
        List<StavkaRacuna> stavke = new ArrayList<>();
        for (AbstractObject stavkaRacuna : absStavke) {
            StavkaRacuna s = (StavkaRacuna) stavkaRacuna;
            stavke.add(s);
        }
        for (StavkaRacuna s : stavke) {
            List<AbstractObject> pica = dBBroker.getObjectByPK(new Pice(), String.valueOf(s.getPice().getPiceID()), 1);
            Pice p = (Pice) pica.get(0);
            s.setPice(p);
        }
        return stavke;
    }

    private void calculateBill(Racun r) {
        double iznos = 0;
        if (!r.getStavkeRacuna().isEmpty()) {
            for (StavkaRacuna s : r.getStavkeRacuna()) {
                iznos += s.getPice().getCena();
            }
        }
        racun.setIznos(iznos);
    }

    public Racun getRacun() {
        return racun;
    }

}
