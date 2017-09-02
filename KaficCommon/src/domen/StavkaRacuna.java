/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elezs
 */
public class StavkaRacuna extends AbstractObject {

    private Racun racun;
    private Pice pice;
    private int brStavkeRacuna;
    private int kolicina;
    
    public StavkaRacuna() {
    }

    public StavkaRacuna(Racun racun, Pice pice, int brStavkeRacuna, int kolicina) {
        this.racun = racun;
        this.pice = pice;
        this.brStavkeRacuna = brStavkeRacuna;
        this.kolicina = kolicina;
    }

    @Override
    public String getTableName() {
        return "stavkaRacuna";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s', '%s', '%s'",racun.getRacunID(), brStavkeRacuna, pice.getPiceID(), kolicina); 
    }

    @Override
    public String getPK() {
        return "brStavkeRacuna";
    }

    @Override
    public int getPKValue() {
        return brStavkeRacuna;
    }

    @Override
    public String getComKey() {
        return "racunID = "+racun.getRacunID();
    }

    @Override
    public List<AbstractObject> RStoTabele(ResultSet rs) {
        List<AbstractObject> stavke = new ArrayList<>();
        try {
            while (rs.next()) {
                int racunID = rs.getInt("racunID");
                int piceID = rs.getInt("piceID");
                int brStavkeRacuna = rs.getInt("brStavkeRacuna");
                int kolicina = rs.getInt("kolicina");
                
                Pice p = new Pice(piceID, null, 0);
                Racun r = new Racun(racunID, piceID, null, null, null, 0);

                StavkaRacuna stavkaRacuna = new StavkaRacuna(r, p, brStavkeRacuna, kolicina);
                stavke.add(stavkaRacuna);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StavkaRacuna.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stavke;
    }

    @Override
    public String getUpdate() {
        return String.format("kolicina = '%s'", kolicina);
//        return String.format("brStavkeRacuna = '%s', kolicina = '%s'", brStavkeRacuna, kolicina);
    }

    @Override
    public void setPKValue(String pk) {
        
    }

    @Override
    public String getFK() {
        return "racunID";
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Pice getPice() {
        return pice;
    }

    public void setPice(Pice pice) {
        this.pice = pice;
    }

    public int getBrStavkeRacuna() {
        return brStavkeRacuna;
    }

    public void setBrStavkeRacuna(int brStavkeRacuna) {
        this.brStavkeRacuna = brStavkeRacuna;
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "racun=" + racun.getRacunID() + ", pice=" + pice + ", brStavkeRacuna=" + brStavkeRacuna + '}';
    }

    @Override
    public int getFKValue() {
        return racun.getRacunID();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StavkaRacuna other = (StavkaRacuna) obj;
        if (this.brStavkeRacuna != other.brStavkeRacuna) {
            return false;
        }
        if (!Objects.equals(this.racun.getRacunID(), other.racun.getRacunID())) {
            return false;
        }
        if(this.racun.getRacunID() == other.racun.getRacunID() && this.pice.getPiceID() == other.getPice().getPiceID()){
            return true;
        }
        return true;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

}
