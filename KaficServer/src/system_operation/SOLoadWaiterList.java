/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Konobar;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLoadWaiterList extends AbstractSO{

    private List<AbstractObject> list;
    @Override
    protected void runSpecificOperation() throws ServerException {
        list = dBBroker.getAllObjects(new Konobar());
    }
    public List<AbstractObject> getList() {
        return list;
    }
}
