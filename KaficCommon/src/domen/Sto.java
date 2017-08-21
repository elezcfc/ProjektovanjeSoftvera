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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elezs
 */
public class Sto extends AbstractObject {

    private int stoID;
    private double ukupanDnevniIznos;
    private List<Racun> dnevniRacuni;
    private boolean zauzet;

    public Sto() {
    }

    public Sto(int stoID, double ukupanDnevniIznos, List<Racun> dnevniRacuni) {
        this.stoID = stoID;
        this.ukupanDnevniIznos = ukupanDnevniIznos;
        this.dnevniRacuni = dnevniRacuni;
        zauzet = false;
    }

    public Sto(int stoID, double ukupanDnevniIznos) {
        this.stoID = stoID;
        this.ukupanDnevniIznos = ukupanDnevniIznos;
        dnevniRacuni = new ArrayList<>();
        zauzet = false;
    }

    private Sto(int stoID, double ukupanIznos, List<Racun> dnevniRacuni, boolean z) {
        this.stoID = stoID;
        this.ukupanDnevniIznos = ukupanIznos;
        this.dnevniRacuni = dnevniRacuni;
        zauzet = z;
    }

    @Override
    public String getTableName() {
        return "sto";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s', '%s'", stoID, ukupanDnevniIznos, zauzet);
    }

    @Override
    public String getPK() {
        return "stoID";
    }

    @Override
    public int getPKValue() {
        return stoID;
    }

    @Override
    public String getComKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbstractObject> RStoTabele(ResultSet rs) {
        List<AbstractObject> stolovi = new ArrayList<>();
        try {
            while (rs.next()) {
                int stoID = rs.getInt("stoID");
                double ukupanIznos = rs.getDouble("ukupanDnevniIznos");
                int i = rs.getInt("zauzet");
                boolean z;
                if(i == 1){
                    z = true;
                }else{
                    z = false;
                }
                List<Racun> dnevniRacuni = new ArrayList<>();

                Sto s = new Sto(stoID, ukupanIznos, dnevniRacuni, z);
                stolovi.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stolovi;
    }

    @Override
    public String getUpdate() {
        if(zauzet == true){
            return String.format("ukupanDnevniIznos = '%s', zauzet = '%s'", ukupanDnevniIznos, 1);
        }else{
            return String.format("ukupanDnevniIznos = '%s', zauzet = '%s'", ukupanDnevniIznos, 0);
        }
    }

    @Override
    public void setPKValue(String pk) {
        stoID = Integer.parseInt(pk);
    }

    public int getStoID() {
        return stoID;
    }

    public void setStoID(int stoID) {
        this.stoID = stoID;
    }

    public double getUkupanDnevniIznos() {
        return ukupanDnevniIznos;
    }

    public void setUkupanDnevniIznos(double ukupanDnevniIznos) {
        this.ukupanDnevniIznos = ukupanDnevniIznos;
    }

    public List<Racun> getDnevniRacuni() {
        return dnevniRacuni;
    }

    public void setDnevniRacuni(List<Racun> dnevniRacuni) {
        this.dnevniRacuni = dnevniRacuni;
    }

    public void addRacun(Racun r) {
        dnevniRacuni.add(r);
    }

    @Override
    public String getFK() {
        return "";
    }

    @Override
    public int getFKValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isZauzet() {
        return zauzet;
    }

    public void setZauzet(boolean zauzet) {
        this.zauzet = zauzet;
    }
    
}
