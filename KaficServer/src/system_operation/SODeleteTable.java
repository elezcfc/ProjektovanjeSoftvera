/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Sto;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SODeleteTable extends AbstractSO {

    Sto table;

    public SODeleteTable(Sto table) {
        this.table = table;
    }

    @Override
    protected void runSpecificOperation() throws ServerException {

        SOLoadBills solb = new SOLoadBills(table);
        solb.runOperation();
        List<AbstractObject> billList = solb.getBills();

        for (AbstractObject abstractObject : billList) {
            Racun r = (Racun) abstractObject;
            for (StavkaRacuna stavka : r.getStavkeRacuna()) {
                SODeleteStavka sods = new SODeleteStavka(stavka);
                sods.runOperation();
            }
            SODeleteBill sodb = new SODeleteBill(r);
            sodb.runOperation();
        }
        dBBroker.deleteObject(table);
    }

    public Sto getTable() {
        return table;
    }
}
