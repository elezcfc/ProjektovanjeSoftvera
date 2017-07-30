/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import domen.StavkaRacuna;
import exception.ServerException;

/**
 *
 * @author elezs
 */
public class SODeleteStavka extends AbstractSO{

    StavkaRacuna stavka;

    public SODeleteStavka(StavkaRacuna stavka) {
        this.stavka = stavka;
    }
    
    @Override
    protected void runSpecificOperation() throws ServerException {
        dBBroker.deleteObject(stavka);
    }
    
}
