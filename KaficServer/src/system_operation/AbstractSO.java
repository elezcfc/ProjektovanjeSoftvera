/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import dbbroker.DBBroker;
import exception.ServerException;

/**
 *
 * @author elezs
 */
public abstract class AbstractSO {

    protected DBBroker dBBroker;

    public AbstractSO() {
        this.dBBroker = new DBBroker();
    }

    synchronized public void runOperation() throws ServerException {
        openConnection();
        runSpecificOperation();
        commitTransaction();
        closeConnection();
    }

    private void openConnection() throws ServerException {
        dBBroker.openConnection();
    }

    private void commitTransaction() throws ServerException {
        dBBroker.commitTransaction();
    }

    private void closeConnection() throws ServerException {
        dBBroker.closeConnection();
    }

    protected abstract void runSpecificOperation() throws ServerException;
}
