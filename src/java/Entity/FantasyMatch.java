/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "FantasyMatch.findByLeagueAndWeek", query = "SELECT match FROM FantasyMatch match WHERE match.league = :league AND match.week = :week"),
                @NamedQuery(name = "FantasyMatch.findByWeek", query = "SELECT match FROM FantasyMatch match WHERE match.week = :week"),
                @NamedQuery(name = "FantasyMatch.findCurrentForTeam", query = "SELECT match FROM FantasyMatch match WHERE match.week = :week AND (match.team1 = :team OR match.team2 = :team)")})
public class FantasyMatch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fMatchID;
    private int week;
    @OneToOne
    private FantasyLeague league;
    @OneToOne
    private FantasyTeam team1;
    @OneToOne
    private FantasyTeam team2;
    private int team1Points;
    private int team2Points;
    
    
    
    
    private NFLPlayer team1QB, team1RB1, team1RB2, team1WR1, team1WR2, team1WRRB, team1TE, team1K, team1DEF;    
    private NFLPlayer team2QB, team2RB1, team2RB2, team2WR1, team2WR2, team2WRRB, team2TE, team2K, team2DEF;
    
    public FantasyTeam getWinningTeam(){
        if(team1Points == 0 && team2Points == 0){
            return null;
        }
        //who cares about ties
        if(team1Points > team2Points)
            return team1;
        else
            return team2;
    }

    public NFLPlayer getTeam1DEF() {
        return team1DEF;
    }

    public void setTeam1DEF(NFLPlayer team1DEF) {
        this.team1DEF = team1DEF;
    }

    public NFLPlayer getTeam2DEF() {
        return team2DEF;
    }

    public void setTeam2DEF(NFLPlayer team2DEF) {
        this.team2DEF = team2DEF;
    }
    
    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public FantasyLeague getLeague() {
        return league;
    }

    public void setLeague(FantasyLeague league) {
        this.league = league;
    }

    public FantasyTeam getTeam1() {
        return team1;
    }

    public void setTeam1(FantasyTeam team1) {
        this.team1 = team1;
    }

    public FantasyTeam getTeam2() {
        return team2;
    }

    public void setTeam2(FantasyTeam team2) {
        this.team2 = team2;
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public void setTeam1Points(int team1Points) {
        this.team1Points = team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
    }

    public void setTeam2Points(int team2Points) {
        this.team2Points = team2Points;
    }

    public NFLPlayer getTeam1QB() {
        return team1QB;
    }

    public void setTeam1QB(NFLPlayer team1QB) {
        this.team1QB = team1QB;
    }

    public NFLPlayer getTeam2QB() {
        return team2QB;
    }

    public void setTeam2QB(NFLPlayer team2QB) {
        this.team2QB = team2QB;
    }

    public NFLPlayer getTeam1RB1() {
        return team1RB1;
    }

    public void setTeam1RB1(NFLPlayer team1RB1) {
        this.team1RB1 = team1RB1;
    }

    public NFLPlayer getTeam2RB1() {
        return team2RB1;
    }

    public void setTeam2RB1(NFLPlayer team2RB1) {
        this.team2RB1 = team2RB1;
    }

    public NFLPlayer getTeam1RB2() {
        return team1RB2;
    }

    public void setTeam1RB2(NFLPlayer team1RB2) {
        this.team1RB2 = team1RB2;
    }

    public NFLPlayer getTeam2RB2() {
        return team2RB2;
    }

    public void setTeam2RB2(NFLPlayer team2RB2) {
        this.team2RB2 = team2RB2;
    }

    public NFLPlayer getTeam1WR1() {
        return team1WR1;
    }

    public void setTeam1WR1(NFLPlayer team1WR1) {
        this.team1WR1 = team1WR1;
    }

    public NFLPlayer getTeam2WR1() {
        return team2WR1;
    }

    public void setTeam2WR1(NFLPlayer team2WR1) {
        this.team2WR1 = team2WR1;
    }

    public NFLPlayer getTeam1WR2() {
        return team1WR2;
    }

    public void setTeam1WR2(NFLPlayer team1WR2) {
        this.team1WR2 = team1WR2;
    }

    public NFLPlayer getTeam2WR2() {
        return team2WR2;
    }

    public void setTeam2WR2(NFLPlayer team2WR2) {
        this.team2WR2 = team2WR2;
    }

    public NFLPlayer getTeam1WRRB() {
        return team1WRRB;
    }

    public void setTeam1WRRB(NFLPlayer team1WRRB) {
        this.team1WRRB = team1WRRB;
    }

    public NFLPlayer getTeam2WRRB() {
        return team2WRRB;
    }

    public void setTeam2WRRB(NFLPlayer team2WRRB) {
        this.team2WRRB = team2WRRB;
    }

    public NFLPlayer getTeam1TE() {
        return team1TE;
    }

    public void setTeam1TE(NFLPlayer team1TE) {
        this.team1TE = team1TE;
    }

    public NFLPlayer getTeam2TE() {
        return team2TE;
    }

    public void setTeam2TE(NFLPlayer team2TE) {
        this.team2TE = team2TE;
    }

    public NFLPlayer getTeam1K() {
        return team1K;
    }

    public void setTeam1K(NFLPlayer team1K) {
        this.team1K = team1K;
    }

    public NFLPlayer getTeam2K() {
        return team2K;
    }

    public void setTeam2K(NFLPlayer team2K) {
        this.team2K = team2K;
    }
    
    
    
    public Long getfMatchID() {
        return fMatchID;
    }

    public void setfMatchID(Long fMatchID) {
        this.fMatchID = fMatchID;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fMatchID != null ? fMatchID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the fMatchID fields are not set
        if (!(object instanceof FantasyMatch)) {
            return false;
        }
        FantasyMatch other = (FantasyMatch) object;
        if ((this.fMatchID == null && other.fMatchID != null) || (this.fMatchID != null && !this.fMatchID.equals(other.fMatchID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FantasyMatch[ id=" + fMatchID + " ]";
    }
    
}
