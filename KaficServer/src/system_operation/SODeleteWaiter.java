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
public class SODeleteWaiter extends AbstractSO {

    private AbstractObject user;

    public SODeleteWaiter(AbstractObject user) {
        this.user = user;
    }

    public AbstractObject getUser() {
        return user;
    }
    
    @Override
    protected void runSpecificOperation() throws ServerException {
        dBBroker.deleteObject(user);
    }

    
}
