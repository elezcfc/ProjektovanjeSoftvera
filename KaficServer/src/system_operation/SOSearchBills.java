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
public class SOSearchBills extends AbstractSO {

    String filterWord;
    private List<AbstractObject> filteredList;
    Sto sto;

    public SOSearchBills(String filterWord, Sto s) {
        this.filterWord = filterWord;
        this.filteredList = new ArrayList<>();
        sto = s;
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        SOLoadBills solb = new SOLoadBills(sto);
        solb.runOperation();
        List<AbstractObject> dbList = solb.getBills();
        boolean dodat = false;
        for (AbstractObject abstractObject : dbList) {
            Racun r = (Racun) abstractObject;
            if (String.valueOf(r.getIznos()).contains(filterWord)
                    || r.getKonobar().getName().toLowerCase().contains(filterWord)) {
                for (StavkaRacuna stavka : r.getStavkeRacuna()) {
                    if (stavka.getPice().getNazivPica().toLowerCase().contains(filterWord)) {
                        filteredList.add(r);
                    }
                }
                if(dodat == false){
                    filteredList.add(r);
                }
            }
        }
    }

    public List<AbstractObject> getFilteredList() {
        return filteredList;
    }
}
