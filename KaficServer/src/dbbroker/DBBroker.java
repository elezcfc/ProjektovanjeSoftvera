/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbbroker;

import domen.AbstractObject;
import domen.Konobar;
import domen.StavkaRacuna;
import exception.ServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import param.cfg.ParamConfigurator;

/**
 *
 * @author elezs
 */
public class DBBroker {

    private Connection connection;

    public DBBroker() {
    }

    public void openConnection() throws ServerException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver baze ucitan!");
//            String url = "jdbc:mysql://localhost:3306/baza_kafic";
//            String user = "root";
//            String password = "";
//            connection = DriverManager.getConnection(url, user, password);
            startConnection();
            connection.setAutoCommit(false);
            System.out.println("Uspesno uspostavljanje konekcije!");
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("Konekcija na bazu nije uspela! ");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("Driver nije uspesno ucitan!");
        } catch (IOException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void commitTransaction() throws ServerException {
        try {
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new ServerException("Neuspesan commit");
        }
    }

    public void closeConnection() throws ServerException {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("Konekcija neuspesno zatvorena");
        }
    }

    public List<AbstractObject> getAllObjects(AbstractObject o) throws ServerException {
        try {
            String upit = "SELECT * FROM " + o.getTableName();
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery(upit);
            List<AbstractObject> listaObjekata = o.RStoTabele(rs);
            s.close();
            System.out.println("Uspesno izvrsen SELECT");
//            for (AbstractObject a : listaObjekata) {
//                System.out.println(a.toString());
//            }
            return listaObjekata;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("SELECT neuspesan!");
        }
    }

    public AbstractObject saveOrUpdateObject(AbstractObject object) throws ServerException {
        try {
            List<AbstractObject> lista = getAllObjects(object);
            String upit = "";
            String tipUpita = "";
            if (lista.size() == 0) {
                upit = String.format("INSERT INTO %s VALUES (%s)", object.getTableName(), object.getParams());
            }
            for (AbstractObject abstractObject : lista) {
                if (object instanceof StavkaRacuna) {
                    if (abstractObject.getFKValue() == object.getFKValue() && abstractObject.getPKValue() == object.getPKValue()) {
                        upit = String.format("UPDATE %s SET %s WHERE %s=%s AND %s=%s", object.getTableName(), object.getUpdate(), object.getPK(), object.getPKValue(), object.getFK(), object.getFKValue());
                        break;
                    }else{
                        if(!doubleCheckList(lista, object)){
                            saveObject(object);
                            return object;
                        }else{
                            continue;
                        }
                    }
                } else if (abstractObject.getPKValue() == object.getPKValue()) {
                    System.out.println(abstractObject.getPKValue());
                    System.out.println(object.getPKValue());
                    tipUpita = "UPDATE";
                    if (object.getPK() != null) {
                        upit = String.format("UPDATE %s SET %s WHERE %s = %s", object.getTableName(), object.getUpdate(), object.getPK(), object.getPKValue());
                        break;
                    } else {
                        upit = String.format("UPDATE %s SET %s WHERE %s", object.getTableName(), object.getUpdate(), object.getComKey());
                        break;
                    }
                } else {
                    tipUpita = "INSERT";
                    upit = String.format("INSERT INTO %s VALUES (%s)", object.getTableName(), object.getParams());
                }
            }
            System.out.println(upit);
            Statement s = connection.createStatement();
            s.execute(upit);
//            PreparedStatement s = connection.prepareCall(upit);
//            s.executeUpdate();
            if (tipUpita.equals("INSERT") || tipUpita.equals("")) {
                ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID() as last_id from " + object.getTableName());
                while (rs.next()) {
                    String lastid = rs.getString("last_id");
                    System.out.println("Last ID: " + lastid);
                    object.setPKValue(lastid);
                    break;
                }

            }
            s.close();
            return object;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    public void deleteObject(AbstractObject object) throws ServerException {
        try {
            String upit;
            if(object instanceof StavkaRacuna){
                upit = String.format("DELETE FROM %s WHERE %s=%s AND %s=%s", object.getTableName(), object.getPK(), object.getPKValue(), object.getFK(), object.getFKValue());
            }else{
                upit = String.format("DELETE FROM %s WHERE %s = %s", object.getTableName(), object.getPK(), object.getPKValue());
                Statement s = connection.createStatement();
                System.out.println(upit);
                s.executeUpdate(upit);
                commitTransaction();
                s.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    public List<AbstractObject> getObjectByPK(AbstractObject o, String ID, int tip) throws ServerException {
        String upit;
        if (o.getPK() != null && tip == 1) {
            upit = "SELECT * FROM " + o.getTableName() + " WHERE " + o.getPK() + "=" + ID;
        } else {
            upit = "SELECT * FROM " + o.getTableName() + " WHERE " + o.getFK() + "=" + ID;
        }
        System.out.println(upit);
        try (Statement s = connection.createStatement();) {
            ResultSet rs = s.executeQuery(upit);
            List<AbstractObject> listaObjekata = o.RStoTabele(rs);
            s.close();
            System.out.println("Uspesno izvrsen mini SELECT");
            return listaObjekata;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    public List<AbstractObject> getObjectByFK(AbstractObject o, String ID) throws ServerException {
        String upit;
        if (o.getPK() != null) {
            upit = "SELECT * FROM " + o.getTableName() + " WHERE " + o.getFK() + "=" + ID;
            System.out.println(upit);
        } else {
            upit = "SELECT * FROM " + o.getTableName() + " WHERE " + o.getComKey();
        }
        try (Statement s = connection.createStatement();) {
            ResultSet rs = s.executeQuery(upit);
            List<AbstractObject> listaObjekata = o.RStoTabele(rs);
            s.close();
            System.out.println("Uspesno izvrsen mini SELECT");
            return listaObjekata;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    public AbstractObject saveObject(AbstractObject o) throws ServerException {
        try {

            String tipUpita = "INSERT";
            String upit = String.format("INSERT INTO %s VALUES (%s)", o.getTableName(), o.getParams());

            System.out.println(upit);
            Statement s = connection.createStatement();
            s.executeUpdate(upit);
            ResultSet rs = s.executeQuery("SELECT LAST_INSERT_ID() as last_id from " + o.getTableName());
            while (rs.next()) {
                String lastid = rs.getString("last_id");
                System.out.println(lastid);
                o.setPKValue(lastid);
                break;
            }

            s.close();
            return o;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    private void startConnection() throws IOException, SQLException {
        ParamConfigurator pc = new ParamConfigurator();
        List<String> list = pc.readDBParams();
        String user = list.get(0);
        String pass = list.get(1);
        String url = "jdbc:mysql://localhost:" + list.get(3) + "/" + list.get(2); //+port/baza
        System.out.println(user);
        System.out.println(pass);
        System.out.println(url);
        connection = DriverManager.getConnection(url, user, pass);
    }

    private boolean doubleCheckList(List<AbstractObject> lista, AbstractObject object) {
        for (AbstractObject abstractObject : lista) {
            if(lista.contains(object))return true;
        }
        return false;
    }
}
