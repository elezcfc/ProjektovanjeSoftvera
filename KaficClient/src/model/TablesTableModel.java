/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.AbstractObject;
import domen.Sto;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elezs
 */
public class TablesTableModel extends AbstractTableModel {

    private List<AbstractObject> tables;

    public TablesTableModel(List<AbstractObject> tables) {
        this.tables = tables;
    }

    @Override
    public int getRowCount() {
        return tables.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sto s = (Sto) tables.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return s.getStoID();
            case 1:
                return s.getUkupanDnevniIznos();
            case 2:
                return s.isZauzet();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Sto ID";
            case 1:
                return "Ukupan dnevni iznos";
            case 2:
                return "Zauzet";
            default:
                return "N/A";
        }
    }

    public AbstractObject getTable(int index) {
        return tables.get(index);
    }

    public List<AbstractObject> getTables() {
        return tables;
    }

    public void setTables(List<AbstractObject> tables) {
        this.tables = tables;
    }

    public void removeTable(Sto s) {
        AbstractObject tableDel = null;
        for (AbstractObject table : tables) {
            if (s.getStoID() == table.getPKValue()) {
                tableDel = table;
            }
        }
        if (tableDel != null) {
            tables.remove(tableDel);
        } else {
            System.out.println("Greska pri brisanju stola iz tabele");
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 2 ? Boolean.class : super.getColumnClass(columnIndex);//To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return columnIndex == 2; //To change body of generated methods, choose Tools | Templates.
//    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue instanceof Boolean && columnIndex == 2) {
            Sto s = (Sto) tables.get(rowIndex);
            if(s.isZauzet()){
                s.setZauzet(false);
            }else{
                s.setZauzet(true);
            }
        }
    }

}
