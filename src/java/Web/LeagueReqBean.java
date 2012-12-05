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
    private String error;
    
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
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
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
        if(leagueName == null || leagueName.equals("") || teamName == null || teamName.equals("")) {
            error = "League and team name cannot be blank";
            return "create_league_error";
        }
        
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
    
    public String createSchedule(){
        List<FantasyTeam> teams = teamBean.findByLeague(sessionBean.getLeague());
        List<FantasyTeam> sideA, sideB;
        sideA = teams.subList(0, teams.size()/2-1);
        sideB = teams.subList(teams.size()/2, teams.size()-1);
        sideA.listIterator(1);
        sideB.listIterator(0);
        for(int i=0; i<14; i++)
        {
            /*create a match*/
            
            
            sideA.add(1, sideB.get(sideB.size()-1));
            sideB.add(0, sideA.get(sideA.size()-1));
            sideA.remove(sideA.size()-1);
            sideB.remove(sideB.size()-1);
        }
        
        return "schedule_created";
    }
       
}
