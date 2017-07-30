/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.AbstractObject;
import domen.Konobar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elezs
 */
public class KonobariTableModel extends AbstractTableModel{

    private List<AbstractObject> listaKonobara;

    public KonobariTableModel(List<AbstractObject> listaKonobara) {
        this.listaKonobara = listaKonobara;
    }
    
    @Override
    public int getRowCount() {
        return listaKonobara.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Konobar k = (Konobar) listaKonobara.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return k.getKonobarID();
            case 1:
                return k.getJMBG();
            case 2:
                return k.getName();
            case 3:
                return k.getUsername();
            case 4:
                return k.getPassword();
            case 5: 
                return k.getStatusText();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID korisnika";
            case 1:
                return "JMBG";
            case 2:
                return "Ime";
            case 3:
                return "Korisnicko ime";
            case 4:
                return "Password";
            case 5:
                return "Status";
            default:
                return "n/a";
        }
    }

    public List<AbstractObject> getListaKonobara() {
        return listaKonobara;
    }

    public void setListaKonobara(List<AbstractObject> listaKonobara) {
        this.listaKonobara = listaKonobara;
    }
}
