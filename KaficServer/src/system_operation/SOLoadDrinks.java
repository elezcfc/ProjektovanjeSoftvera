/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Pice;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLoadDrinks extends AbstractSO{

    private List<AbstractObject> listOfDrinks;
    @Override
    protected void runSpecificOperation() throws ServerException {
        listOfDrinks = dBBroker.getAllObjects(new Pice());
    }

    public List<AbstractObject> getListOfDrinks() {
        return listOfDrinks;
    }
}
