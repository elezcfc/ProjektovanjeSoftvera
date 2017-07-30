/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author elezs
 */
public class ServerTransfer implements Serializable{
    private Object data;
    private int succesfull;
    private Exception exception;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getSuccesfull() {
        return succesfull;
    }

    public void setSuccesfull(int succesfull) {
        this.succesfull = succesfull;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
