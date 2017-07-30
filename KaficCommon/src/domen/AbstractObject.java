/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author elezs
 */
public abstract class AbstractObject implements Serializable{
    public abstract String getTableName();
    public abstract String getParams();
    public abstract String getPK();
    public abstract int getPKValue();
    public abstract String getComKey();
    public abstract List<AbstractObject> RStoTabele(ResultSet rs);
    public abstract String getUpdate();
    public abstract void setPKValue(String pk);
    public abstract String getFK();
    public abstract int getFKValue();
    
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
