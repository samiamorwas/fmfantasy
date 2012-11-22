/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyLeague;
import Entity.FantasyMatch;
import Entity.FantasyTeam;
import Entity.FantasyUser;
import java.io.Serializable;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@SessionScoped
public class SessionBean implements Serializable{
    
    //the currently logged in user? i think?
    private FantasyUser user;
    private FantasyLeague league;
    private FantasyTeam team;
    private FantasyMatch match;
    
    private int NFLWeek;
    private int LeagueWeek;
    
    public SessionBean(){
        
    }

    public int getNFLWeek() {
        return NFLWeek;
    }

    public void setNFLWeek(int NFLWeek) {
        this.NFLWeek = NFLWeek;
    }

    public int getLeagueWeek() {
        return LeagueWeek;
    }

    public void setLeagueWeek(int LeagueWeek) {
        this.LeagueWeek = LeagueWeek;
    }

    public FantasyMatch getMatch() {
        return match;
    }

    public void setMatch(FantasyMatch match) {
        this.match = match;
    }

    public FantasyUser getUser() {
        return user;
    }

    public void setUser(FantasyUser user) {
        this.user = user;
    }

    public FantasyLeague getLeague() {
        return league;
    }

    public void setLeague(FantasyLeague league) {
        this.league = league;
    }

    public FantasyTeam getTeam() {
        return team;
    }

    public void setTeam(FantasyTeam team) {
        this.team = team;
    }

       
}
