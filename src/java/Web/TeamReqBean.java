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
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@SessionScoped
public class TeamReqBean {
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;

    @Inject
    UserReqBean uReq;
    @Inject
    LeagueReqBean lReq;
    
    private FantasyTeam teamViewed;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public TeamReqBean() {
        teamViewed = null;
    }
    
    public List<FantasyTeam> getOwned(){
        FantasyUser luser = uReq.getUser();
        
        List<FantasyTeam> teams = teamBean.findByOwner(luser);
        
        return teams;
    }
    public List<FantasyTeam> getInLeague(){
        FantasyLeague lleague = lReq.getLeague();
        
        List<FantasyTeam> teams = teamBean.findByLeague(lleague);
        
        return teams;
    }
    
    public String viewTeam(FantasyTeam team) {
        this.teamViewed = team;
        return "team_set";
    }
    
    public FantasyTeam getTeam(){
        return teamViewed;
    }
    
    public String setCurrentTeam() {
        this.teamViewed = teamBean.findByOwnerAndLeague(uReq.getUser(), lReq.getLeague());
        return "team_set";
    }
    
}
