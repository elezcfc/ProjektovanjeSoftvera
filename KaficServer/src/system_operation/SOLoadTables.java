/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Sto;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLoadTables extends AbstractSO {

    private List<AbstractObject> tables;

    @Override
    protected void runSpecificOperation() throws ServerException {
        tables = dBBroker.getAllObjects(new Sto());
    }

    public List<AbstractObject> getTables() {
        return tables;
    }

}
