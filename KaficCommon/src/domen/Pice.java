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
public class Pice extends AbstractObject {

    private int piceID;
    private String nazivPica;
    private double cena;

    public Pice() {
    }

    public Pice(int piceID, String nazivPica, double cena) {
        this.piceID = piceID;
        this.nazivPica = nazivPica;
        this.cena = cena;
    }

    @Override
    public String getTableName() {
        return "pice";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s', '%s'", piceID, nazivPica, cena);
    }

    @Override
    public String getPK() {
        return "piceID";
    }

    @Override
    public int getPKValue() {
        return piceID;
    }

    @Override
    public String getComKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbstractObject> RStoTabele(ResultSet rs) {
        List<AbstractObject> pica = new ArrayList<>();
        try {
            while (rs.next()) {
                int piceID = rs.getInt("piceID");
                String naziv = rs.getString("nazivPica");
                double cena = rs.getDouble("cena");
                Pice p = new Pice(piceID, naziv, cena);
                pica.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pice.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Greska pri citanju RSPice!!");
        }
        return pica;
    }

    @Override
    public String getUpdate() {
        return String.format("nazivPica = '%s', cena = '%s'", nazivPica, cena);
    }

    @Override
    public void setPKValue(String pk) {
        piceID = Integer.parseInt(pk);
    }

    public int getPiceID() {
        return piceID;
    }

    public void setPiceID(int piceID) {
        this.piceID = piceID;
    }

    public String getNazivPica() {
        return nazivPica;
    }

    public void setNazivPica(String nazivPica) {
        this.nazivPica = nazivPica;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public String getFK() {
        return "";
    }

    @Override
    public String toString() {
        return nazivPica+" : "+cena;
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
        final Pice other = (Pice) obj;
        if (!Objects.equals(this.nazivPica, other.getNazivPica())) {
            return false;
        }
        if (!Objects.equals(this.cena, other.getCena())) {
            return false;
        }
        return true;
    }

    @Override
    public int getFKValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
