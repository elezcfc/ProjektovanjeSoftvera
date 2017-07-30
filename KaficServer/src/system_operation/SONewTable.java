/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Sto;
import exception.ServerException;

/**
 *
 * @author elezs
 */
public class SONewTable extends AbstractSO {

    AbstractObject sto;

    public SONewTable(Sto sto) {
        this.sto = sto;
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        sto = dBBroker.saveObject(sto);
    }

    public AbstractObject getSto() {
        return sto;
    }
}
