/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system_operation;

import controller.ServerController;
import domen.AbstractObject;
import domen.Konobar;
import exception.ServerException;
import java.util.List;

/**
 *
 * @author elezs
 */
public class SOLogin extends AbstractSO {

    private AbstractObject paramKonobar;
    private Konobar loggedInUser;

    @Override
    protected void runSpecificOperation() throws ServerException {
        List<AbstractObject> userList = dBBroker.getAllObjects(new Konobar());
        Konobar current = (Konobar) paramKonobar;
        for (AbstractObject abstractObject : userList) {
            Konobar dbbKonobar = (Konobar) abstractObject;
            if (dbbKonobar.equals(current)) {
                loggedInUser = dbbKonobar;
                int index = ServerController.getServerController().getUserList().indexOf(dbbKonobar);
                Konobar k = (Konobar) ServerController.getServerController().getUserList().get(index);
                if (k.isLoggedIn()) {
                    throw new ServerException("Korisnik je vec ulogovan!");
                } else {
                    k.setLoggedIn(true);
                    loggedInUser.setLoggedIn(true);
                }
                System.out.println("Postavio korisnika da je ulogovan");
                return;
            }
        }
        throw new ServerException("Korisnik nije nadjen!");
    }

    public AbstractObject getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Konobar loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public AbstractObject getParamKonobar() {
        return paramKonobar;
    }

    public void setParamKonobar(AbstractObject paramKonobar) {
        this.paramKonobar = paramKonobar;
    }

}
