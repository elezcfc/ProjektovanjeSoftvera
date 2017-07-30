/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Pice;
import exception.ServerException;

/**
 *
 * @author elezs
 */
public class SONewDrink extends AbstractSO{

    Pice pice;
    public SONewDrink(AbstractObject parametar) {
        pice = (Pice) parametar;
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        pice = (Pice) dBBroker.saveOrUpdateObject(pice);
    }

    public Pice getPice() {
        return pice;
    }
}
