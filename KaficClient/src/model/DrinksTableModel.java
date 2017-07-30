/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.AbstractObject;
import domen.Pice;
import exception.ValidationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import view.DrinksFrm;

/**
 *
 * @author elezs
 */
public class DrinksTableModel extends AbstractTableModel {

    private List<AbstractObject> listOfDrinks;
    DrinksFrm frame;

    public DrinksTableModel(List<AbstractObject> listOfDrinks) {
        this.listOfDrinks = listOfDrinks;
    }

    public DrinksTableModel(List<AbstractObject> listOfDrinks, DrinksFrm aThis) {
        this.listOfDrinks = listOfDrinks;
        frame = aThis;
    }

    @Override
    public int getRowCount() {
        return listOfDrinks.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pice pice = (Pice) listOfDrinks.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pice.getNazivPica();
            case 1:
                return pice.getCena();
            default:
                return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Naziv pica";
            case 1:
                return "Cena pica";
            default:
                return "N/A";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Pice p = (Pice) listOfDrinks.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    if (validirajNaziv(aValue)) {
                        p.setNazivPica(aValue.toString());
                        break;
                    }
                case 1:
                    if (validirajCenu(aValue)) {
                        p.setCena(Double.parseDouble(aValue.toString()));
                        break;
                    }
            }
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    private boolean validirajNaziv(Object aValue) throws ValidationException {
        if (!aValue.toString().equals("") || !aValue.toString().equals(" ")) {
            return true;
        } else {
            throw new ValidationException("Morate uneti korektan naziv pica");
        }
    }

    private boolean validirajCenu(Object aValue) throws ValidationException {
        try {
            double d = Double.parseDouble(aValue.toString());
            if (d != 0) {
                return true;
            }
        } catch (Exception e) {
            throw new ValidationException("Morate uneti korektnu cenu pica");
        }
        return false;
    }

    public List<AbstractObject> getListOfDrinks() {
        return listOfDrinks;
    }

    public void setListOfDrinks(List<AbstractObject> listOfDrinks) {
        this.listOfDrinks = listOfDrinks;
    }
}
