/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Pice;
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOSearchDrinks extends AbstractSO{

    String filterWord;
    private List<AbstractObject> filteredList;

    public SOSearchDrinks(String rec) {
        filterWord = rec;
        filteredList = new ArrayList<>();
    }
    @Override
    protected void runSpecificOperation() throws ServerException {
        SOLoadDrinks sold = new SOLoadDrinks();
        sold.runOperation();
        List<AbstractObject> dbList = sold.getListOfDrinks();
        
        for (AbstractObject abstractObject : dbList) {
            Pice p = (Pice) abstractObject;
            if(String.valueOf(p.getPiceID()).toLowerCase().contains(filterWord) ||
                    p.getNazivPica().toLowerCase().contains(filterWord) ||
                    String.valueOf(p.getCena()).toLowerCase().contains(filterWord)){
                filteredList.add(p);
            }
        }
    }

    public List<AbstractObject> getFilteredList() {
        return filteredList;
    }
    
}
