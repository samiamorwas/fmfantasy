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
    
    private FantasyUser user;
    private FantasyLeague league;
    private FantasyTeam team;
    
    private String agentTextEntry;
    private boolean QB,RB,WR,TE,KCK,DEF;
    
    private int NFLWeek;
    private int LeagueWeek = 1;
    private int previousLeagueWeek;
    private int nextLeagueWeek;
    
    public SessionBean(){
        
    }

    public String getAgentTextEntry() {
        return agentTextEntry;
    }

    public void setAgentTextEntry(String agentTextEntry) {
        this.agentTextEntry = agentTextEntry;
    }

    public boolean isQB() {
        return QB;
    }

    public void setQB(boolean QB) {
        this.QB = QB;
    }

    public boolean isRB() {
        return RB;
    }

    public void setRB(boolean RB) {
        this.RB = RB;
    }

    public boolean isWR() {
        return WR;
    }

    public void setWR(boolean WR) {
        this.WR = WR;
    }

    public boolean isTE() {
        return TE;
    }

    public void setTE(boolean TE) {
        this.TE = TE;
    }

    public boolean isKCK() {
        return KCK;
    }

    public void setKCK(boolean KCK) {
        this.KCK = KCK;
    }

    public boolean isDEF() {
        return DEF;
    }

    public void setDEF(boolean DEF) {
        this.DEF = DEF;
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
    
    public int getPreviousLeagueWeek() {
        return previousLeagueWeek;
    }

    public void setPreviousLeagueWeek(int previousLeagueWeek) {
        this.previousLeagueWeek = previousLeagueWeek;
    }

    public int getNextLeagueWeek() {
        return nextLeagueWeek;
    }

    public void setNextLeagueWeek(int nextLeagueWeek) {
        this.nextLeagueWeek = nextLeagueWeek;
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
    
    public boolean getDraftFinished() {
        return league.getFinishedDraft();
    }
    
    public boolean getIsOwner() {
        return (league.getLeagueOwner().equals(user));
    }
    
    public boolean getCanInvite() {
        boolean draftInProgress = (league.getDraftStarted() || league.getFinishedDraft());
        return (!draftInProgress) && getIsOwner();
    }
    
    public boolean getCanDraft() {
        return (!league.getFinishedDraft()) && getIsOwner();
    }
}
