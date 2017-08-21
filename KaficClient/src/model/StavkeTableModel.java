/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Konobar;
import domen.Pice;
import domen.Racun;
import domen.StavkaRacuna;
import domen.Sto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elezs
 */
public class StavkeTableModel extends AbstractTableModel {

    private List<StavkaRacuna> listStavki;
    int brStavke = 0;
    public StavkeTableModel(Racun racun) {
        this.listStavki = racun.getStavkeRacuna();
        brStavke = racun.getStavkeRacuna().size();
    }

    @Override
    public int getRowCount() {
        return listStavki.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRacuna sr = listStavki.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sr.getBrStavkeRacuna();
            case 1:
                return sr.getPice().getNazivPica();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Broj stavke racuna";
            case 1:
                return "Pice";   
            default:
                return "N/A";
        }
    }
    
    public void dodajStavkuRacuna(Racun racun, Pice p) {
        StavkaRacuna stavka = new StavkaRacuna(racun, p, brStavke);
        listStavki.add(stavka);
        brStavke++;
        fireTableDataChanged();
    }

    public void obrisiStavkuRacuna(int index) {
        listStavki.remove(index);
        brStavke--;
        fireTableDataChanged();
    }

    public List<StavkaRacuna> getListStavki() {
        return listStavki;
    }

}
