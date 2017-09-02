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
    int brStavke = 1;
    private Racun racun;
    public StavkeTableModel(Racun racun) {
        this.racun = racun;
        this.listStavki = racun.getStavkeRacuna();
//        brStavke = racun.getStavkeRacuna().size();
        brStavke = listStavki.size();
    }

    @Override
    public int getRowCount() {
        return listStavki.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRacuna sr = listStavki.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sr.getBrStavkeRacuna();
            case 1:
                return sr.getPice().getNazivPica();
            case 2:
                return sr.getKolicina();
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
            case 2:
                return "Kolicina";
            default:
                return "N/A";
        }
    }
    
    public void dodajStavkuRacuna(Pice p) {
        StavkaRacuna s = null;
        StavkaRacuna novaSR = null;
        for(StavkaRacuna sr: racun.getStavkeRacuna()){
            if(sr.getPice().equals(p)){
                s = sr;
                break;
            }
        }
        if(s == null){
            novaSR = new StavkaRacuna();
            novaSR.setRacun(racun);
            novaSR.setBrStavkeRacuna(racun.getStavkeRacuna().size()+1);
            novaSR.setPice(p);
            System.out.println(brStavke);
            System.out.println(racun.getStavkeRacuna().size()+1);
            novaSR.setKolicina(1);
//            racun.getStavkeRacuna().add(novaSR);
            listStavki.add(novaSR);
        }else{
            s.setKolicina(s.getKolicina()+1);
        }
        brStavke++;
        if(novaSR != null){
            racun.setIznos(racun.getIznos()+p.getCena());
        }
        if(s != null){
            racun.setIznos(racun.getIznos()+p.getCena());
        }
        fireTableDataChanged();
    }

    public void obrisiStavkuRacuna(int index) {
        StavkaRacuna s = listStavki.get(index);
        if(s.getKolicina() > 1){
            int kol = s.getKolicina();
            listStavki.get(index).setKolicina(--kol);
            racun.setIznos(racun.getIznos()- s.getPice().getCena());
        }else if (s.getKolicina() == 1){
            listStavki.remove(index);
        }
        brStavke--;
        fireTableDataChanged();
    }

    public List<StavkaRacuna> getListStavki() {
        return listStavki;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

}
