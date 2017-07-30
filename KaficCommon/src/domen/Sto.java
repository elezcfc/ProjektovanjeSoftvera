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

    public Sto() {
    }

    public Sto(int stoID, double ukupanDnevniIznos, List<Racun> dnevniRacuni) {
        this.stoID = stoID;
        this.ukupanDnevniIznos = ukupanDnevniIznos;
        this.dnevniRacuni = dnevniRacuni;
    }

    public Sto(int stoID, double ukupanDnevniIznos) {
        this.stoID = stoID;
        this.ukupanDnevniIznos = ukupanDnevniIznos;
        dnevniRacuni = new ArrayList<>();
    }

    @Override
    public String getTableName() {
        return "sto";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s'", stoID, ukupanDnevniIznos);
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
                List<Racun> dnevniRacuni = new ArrayList<>();

                Sto s = new Sto(stoID, ukupanIznos, dnevniRacuni);
                stolovi.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stolovi;
    }

    @Override
    public String getUpdate() {
        return String.format("ukupanDnevniIznos = '%s'", ukupanDnevniIznos);
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
}
