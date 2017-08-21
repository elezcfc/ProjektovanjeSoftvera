/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package param.cfg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elezs
 */
public class ParamConfigurator {

    public static final String portFilePath = "D:\\Fax\\IV Godina\\Drugi semestar\\Projektovanje softvera\\Projekat - Elez Stefan\\KaficServer\\src\\params\\portCfg.properties";
    public static final String dbParamsFilePath = "D:\\Fax\\IV Godina\\Drugi semestar\\Projektovanje softvera\\Projekat - Elez Stefan\\KaficServer\\src\\params\\dbCfg.properties";

    private String port;
    private String dbPort;
    private String name;
    private String user;
    private String pass;

    public ParamConfigurator() {
    }

    public ParamConfigurator(String port) {
        this.port = port;
    }

    public ParamConfigurator(String dbPort, String name, String user, String pass) {
        this.dbPort = dbPort;
        this.name = name;
        this.user = user;
        this.pass = pass;
    }

    public int readPort() throws FileNotFoundException, IOException {
//        BufferedReader br = new BufferedReader(new FileReader(portFilePath));
//        String line = br.readLine();
//        String port = line.substring(7, 11);
//        System.out.println(port);
//        return Integer.parseInt(port);
        Properties prop = new Properties();
        InputStream input = new FileInputStream(portFilePath);
        prop.load(input);
        int port = Integer.parseInt(prop.getProperty("port"));
        return port;
    }

    public void writePort(String port) throws FileNotFoundException {
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream(portFilePath);
        prop.setProperty("port", port);
        try {
            prop.store(output, null);
        } catch (IOException ex) {
            Logger.getLogger(ParamConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> readDBParams() throws IOException {
        ArrayList<String> param = new ArrayList<>();
        Properties prop = new Properties();
        InputStream input = new FileInputStream(dbParamsFilePath);
        prop.load(input);
        param.add(prop.getProperty("user"));
        param.add(prop.getProperty("pass"));
        param.add(prop.getProperty("dbName"));
        param.add(prop.getProperty("dbPort"));
        return param;
    }

    public void writeDBParams(String dbPort, String name, String user, String pass) throws FileNotFoundException {
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream(dbParamsFilePath);
        prop.setProperty("user", user);
        prop.setProperty("pass", pass);
        prop.setProperty("dbName", name);
        prop.setProperty("dbPort", dbPort);
        
        try {
            prop.store(output, null);
        } catch (IOException ex) {
            Logger.getLogger(ParamConfigurator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
