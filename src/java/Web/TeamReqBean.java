/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import Entity.FantasyUser;
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
public class TeamReqBean {
    @EJB
    private FMFantasyEJB.FantasyLeagueBean leagueBean;
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;
       
    @Inject
    SessionBean sessionBean;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public TeamReqBean() {
    }
    
    public List<FantasyTeam> getOwned(){
        FantasyUser luser = sessionBean.getUser();
        
        List<FantasyTeam> teams = teamBean.findByOwner(luser);
        
        return teams;
    }
    public List<FantasyTeam> getInLeague(){
        FantasyLeague lleague = sessionBean.getLeague();
        
        List<FantasyTeam> teams = teamBean.findByLeague(lleague);
        
        return teams;
    }
    
    public String setTeam(FantasyTeam team) {
        sessionBean.setTeam(team);
        return "team_set";
    }
    
}
