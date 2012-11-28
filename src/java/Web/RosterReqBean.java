/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyTeam;
import Entity.RosterPlayer;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@RequestScoped
public class RosterReqBean {

    @EJB
    private FMFantasyEJB.RosterPlayerBean rosterBean;
    @Inject
    SessionBean sessionBean;

    /**
     * Creates a new instance of FantasyLeagueController
     */
    public RosterReqBean() {
    }

    public List<RosterPlayer> getTeam() {
        return rosterBean.getByTeam(sessionBean.getTeam());
    }
}