/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.AbstractObject;
import domen.Racun;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author elezs
 */
public class BillsTableModel extends AbstractTableModel {

    private List<AbstractObject> bills;

    public BillsTableModel(List<AbstractObject> bills) {
        this.bills = bills;
    }
    
    @Override
    public int getRowCount() {
        return bills.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = (Racun) bills.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getRacunID();
            case 1:
                return r.getIznos();
            case 2:
                return r.getKonobar().getName();
            case 3:
                if(r.getPlacen() == 1){
                    return "Placen";
                }else{
                    return "Neplacen!";
                }
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Racun ID";
            case 1:
                return "Iznos";
            case 2:
                return "Konobar";
            case 3: 
                return "Placen";
            default:
                return "n/a";
        }
    }

    public List<AbstractObject> getBills() {
        return bills;
    }

    public void setBills(List<AbstractObject> bills) {
        this.bills = bills;
    }
    
}
