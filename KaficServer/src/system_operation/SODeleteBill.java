/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import controller.ServerController;
import domen.Racun;
import domen.StavkaRacuna;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SODeleteBill extends AbstractSO{

    Racun r;
    public SODeleteBill(Racun racun) {
        r = racun;
    }

    @Override
    protected void runSpecificOperation() throws ServerException {
        List<StavkaRacuna> stavke = r.getStavkeRacuna();
        String poruka = "";
        if(stavke == null || stavke.isEmpty()){
            throw new ServerException("Nema stavki za brisanje!");
        }else{
            for (StavkaRacuna stavkaRacuna : stavke) {
                poruka = ServerController.getServerController().deleteStavka(stavkaRacuna);
            }
            if(poruka.equals("sve ful")){
                dBBroker.deleteObject(r);
            }else{
                throw new ServerException("Neuspesno  brisanje stavki!");
            }
        }
    }
    
}
