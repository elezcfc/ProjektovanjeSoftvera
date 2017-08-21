/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import com.sun.javafx.scene.control.skin.VirtualFlow;
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
public class Racun extends AbstractObject {

    private int racunID;
    private double iznos;
    private Konobar konobar;
    private Sto sto;
    private List<StavkaRacuna> stavkeRacuna;
    private int placen;

    public Racun() {
    }

    public Racun(int racunID, double iznos, Konobar konobar, Sto sto, List<StavkaRacuna> stavkeRacuna, int placen) {
        this.racunID = racunID;
        this.iznos = iznos;
        this.konobar = konobar;
        this.sto = sto;
        this.stavkeRacuna = stavkeRacuna;
        this.placen = placen;
    }

    @Override
    public String getTableName() {
        return "racun";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s', '%s', '%s', '%s'",racunID, iznos, konobar.getKonobarID(), sto.getStoID(), placen);
    }

    @Override
    public String getPK() {
        return "racunID";
    }

    @Override
    public int getPKValue() {
        return racunID;
    }

    @Override
    public String getComKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AbstractObject> RStoTabele(ResultSet rs) {
        List<AbstractObject> racuni = new ArrayList<>();
        try {
            while (rs.next()) {
                int racunID = rs.getInt("racunID");
                double iznos = rs.getDouble("iznos");
                int konobarID = rs.getInt("konobarID");
                int stoID = rs.getInt("stoID");
                int placen = rs.getInt("placen");
                Racun r = new Racun(racunID, iznos, new Konobar(konobarID, null, null, null, null, false), new Sto(stoID, 0, new ArrayList<Racun>()), new ArrayList<StavkaRacuna>(), placen);
                racuni.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
        }
        return racuni;
    }

    @Override
    public String getUpdate() {
        return String.format("iznos='%s', placen='%s'", iznos, placen);
    }

    @Override
    public void setPKValue(String pk) {
        racunID = Integer.parseInt(pk);
    }

    public int getRacunID() {
        return racunID;
    }

    public void setRacunID(int racunID) {
        this.racunID = racunID;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Konobar getKonobar() {
        return konobar;
    }

    public void setKonobar(Konobar konobar) {
        this.konobar = konobar;
    }

    public Sto getSto() {
        return sto;
    }

    public void setSto(Sto sto) {
        this.sto = sto;
    }

    public List<StavkaRacuna> getStavkeRacuna() {
        return stavkeRacuna;
    }

    public void setStavkeRacuna(List<StavkaRacuna> stavkeRacuna) {
        this.stavkeRacuna = stavkeRacuna;
    }

    @Override
    public String getFK() {
        return "stoID";
    }

    @Override
    public String toString() {
        return "Racun{" + "racunID=" + racunID + ", iznos=" + iznos + ", konobar=" + konobar + ", sto=" + sto + ", stavkeRacuna=" + stavkeRacuna + '}';
    }

    @Override
    public int getFKValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getPlacen() {
        return placen;
    }

    public void setPlacen(int placen) {
        this.placen = placen;
    }
}
