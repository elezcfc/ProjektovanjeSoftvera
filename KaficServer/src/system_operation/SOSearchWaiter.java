/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.AbstractObject;
import domen.Konobar;
import exception.ServerException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOSearchWaiter extends AbstractSO{

    String filterWord;
    private List<AbstractObject> filteredList;

    public SOSearchWaiter(String rec) {
        filterWord = rec;
        filteredList = new ArrayList<>();
    }
    
    @Override
    protected void runSpecificOperation() throws ServerException {
        SOLoadWaiterList solu = new SOLoadWaiterList();
        solu.runOperation();
        List<AbstractObject> dbList = solu.getList();
        filterWord = filterWord.toLowerCase();
        
        for (AbstractObject abstractObject : dbList) {
            Konobar k = (Konobar) abstractObject;
            if(k.getJMBG().toLowerCase().contains(filterWord) ||
                    k.getName().toLowerCase().contains(filterWord) ||
                    k.getUsername().toLowerCase().contains(filterWord) ||
                    k.getPassword().toLowerCase().contains(filterWord)){
                filteredList.add(k);
            }
        }
        
    }

    public List<AbstractObject> getFilteredList() {
        return filteredList;
    }
}
