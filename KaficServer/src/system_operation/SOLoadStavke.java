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
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLoadStavke extends AbstractSO{

    private List<StavkaRacuna> stavke;
    Racun racun;

    public SOLoadStavke(Racun racun) {
        this.racun = racun;
        stavke = new ArrayList<>();
    }
    
    @Override
    protected void runSpecificOperation() throws ServerException {
        List<AbstractObject> absStavke = dBBroker.getObjectByPK(new StavkaRacuna(), String.valueOf(racun.getRacunID()), 2);
        List<AbstractObject> absPica = dBBroker.getAllObjects(new Pice());
        
        for (AbstractObject absStavka : absStavke) {
            StavkaRacuna stavka = (StavkaRacuna) absStavka;
            for (AbstractObject absPice : absPica) {
                Pice p = (Pice) absPice;
                if(stavka.getPice().getPiceID() == p.getPiceID()){
                    stavka.setPice(p);
                }
            }
            stavke.add(stavka);
        }
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }
    
}
