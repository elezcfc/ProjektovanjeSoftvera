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
public class Konobar extends AbstractObject {

    private int konobarID;
    private String JMBG;
    private String name;
    private String username;
    private String password;
    private boolean loggedIn;

    public Konobar(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getFK() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFKValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public enum tipKonobara {
        GLAVNI, POMOCNI
    }

    public Konobar() {
    }

    public Konobar(String JMBG, String name, String username, String password, boolean loggedIn) {
        this.JMBG = JMBG;
        this.name = name;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    
    public Konobar(int kononbarID, String JMBG, String firstName) {
        this.konobarID = kononbarID;
        this.JMBG = JMBG;
        this.name = firstName;
    }

    public Konobar(int kononbarID, String JMBG, String name, String username, String password, boolean loggedIn) {
        this.konobarID = kononbarID;
        this.JMBG = JMBG;
        this.name = name;
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    @Override
    public String getTableName() {
        return "konobar";
    }

    @Override
    public String getParams() {
        return String.format("'%s', '%s', '%s','%s','%s'",konobarID, JMBG, name, username, password);
    }

    @Override
    public String getPK() {
        return "konobarID";
    }

    @Override
    public int getPKValue() {
        return konobarID;
    }

    @Override
    public String getComKey() {
        return "";
    }

    @Override
    public List<AbstractObject> RStoTabele(ResultSet rs) {
        List<AbstractObject> konobari = new ArrayList<>();
        try {
            while (rs.next()) {
                int konobarID1 = rs.getInt("konobarID");
                String JMBG1 = rs.getString("jmbg");
                String name1 = rs.getString("name");
                String username1 = rs.getString("username");
                String password1 = rs.getString("password");
                boolean loggedIn = false;

                Konobar k = new Konobar(konobarID1, JMBG1, name1, username1, password1, loggedIn);
                konobari.add(k);
            }
        } catch (SQLException ex) {
            System.out.println("Greska pri citanju RSKonobar!");
            Logger.getLogger(Konobar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return konobari;
    }

    @Override
    public String getUpdate() {
        return String.format("jmbg = '%s', name = '%s', username = '%s', password = '%s'", JMBG, name, username, password);
    }

    @Override
    public void setPKValue(String pk) {
        konobarID = Integer.parseInt(pk);
    }

    public String getStatusText() {
        if (loggedIn == false) {
            return "Nije ulogovan";
        } else {
            return "Ulogovan";
        }
    }

    public int getKonobarID() {
        return konobarID;
    }

    public void setKonobarID(int konobarID) {
        this.konobarID = konobarID;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
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
        final Konobar other = (Konobar) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
