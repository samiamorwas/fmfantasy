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
public class LeagueReqBean {
    @EJB
    private FMFantasyEJB.FantasyLeagueBean leagueBean;
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;
    @EJB
    private FMFantasyEJB.FantasyUserBean userBean;
    @EJB
    private FMFantasyEJB.InvitationBean invBean;
    
    @Inject
    SessionBean sessionBean;
        
    private String leagueName;
    private String teamName;
    
    /**
     * Creates a new instance of LeagueReqBean
     */
    public LeagueReqBean() {
    }

    public String getLeagueName() {
        return leagueName;
    }
    
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }    
    
    public List<FantasyLeague> getOwned(){
        FantasyUser luser = sessionBean.getUser();
        List<FantasyLeague> leagues = leagueBean.findByOwner(luser);
        
        return leagues;
    }
    public List<FantasyLeague> getMemberOf(){
        FantasyUser luser = sessionBean.getUser();
        List<FantasyLeague> leagues = leagueBean.findByMember(luser);
        
        return leagues;
    }
    
    public String viewLeague(FantasyLeague league){
        sessionBean.setLeague(league);
        return "view_league";
    }
    
    public String createLeague(){
        FantasyUser luser = sessionBean.getUser();
        
        FantasyLeague l = new FantasyLeague();
        l.setLeagueName(leagueName);
        l.setLeagueOwner(luser);
        l.setFinishedDraft(false);
        leagueBean.createLeague(l);
        
        FantasyTeam t = new FantasyTeam();
        t.setTeamName(teamName);
        t.setLeague(l);
        t.setTeamOwner(luser);
        teamBean.createTeam(t);
        
        return "create_league_success";
    }
       
}
