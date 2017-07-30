/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import exception.ServerException;

/**
 *
 * @author elezs
 */
public class SOSaveWaiter extends AbstractSO{

    private AbstractObject konobar;

    public SOSaveWaiter(AbstractObject konobar) {
        this.konobar = konobar;
    }
    
    @Override
    protected void runSpecificOperation() throws ServerException {
        konobar = dBBroker.saveOrUpdateObject(konobar);
    }

    public AbstractObject getKonobar() {
        return konobar;
    }

    public void setKonobar(AbstractObject konobar) {
        this.konobar = konobar;
    }
    
}
