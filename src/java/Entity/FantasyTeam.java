/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "FantasyTeam.findByOwner", query = "SELECT team FROM FantasyTeam team WHERE team.teamOwner = :owner"),
                @NamedQuery(name = "FantasyTeam.findByLeague", query = "SELECT team FROM FantasyTeam team WHERE team.league = :league ORDER BY team.rank"),
                @NamedQuery(name = "FantasyTeam.findByOwnerAndLeague", query = "Select team FROM FantasyTeam team WHERE team.league = :league AND team.teamOwner = :owner")})
public class FantasyTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teamID;
    
    private String teamName;
    
    @ManyToOne    
    @JoinColumn
    private FantasyUser teamOwner;
    
    @ManyToOne    
    @JoinColumn
    private FantasyLeague league;
    
    @OneToMany(mappedBy = "team")
    private List<RosterPlayer> rosterPlayers;
    
    
    private int wins;
    private int losses;
    private int draws;
    private int pointsFor;
    private int pointsAgainst;
    private int rank;
    private int previousRank;
    
    private boolean inPlayoffs;

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public FantasyUser getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(FantasyUser teamOwner) {
        this.teamOwner = teamOwner;
    }

    public FantasyLeague getLeague() {
        return league;
    }

    public void setLeague(FantasyLeague league) {
        this.league = league;
    }

    public List<RosterPlayer> getRosterPlayers() {
        return rosterPlayers;
    }

    public void setRosterPlayers(List<RosterPlayer> rosterPlayers) {
        this.rosterPlayers = rosterPlayers;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getPointsFor() {
        return pointsFor;
    }

    public void setPointsFor(int pointsFor) {
        this.pointsFor = pointsFor;
    }

    public int getPointsAgainst() {
        return pointsAgainst;
    }

    public void setPointsAgainst(int pointsAgainst) {
        this.pointsAgainst = pointsAgainst;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPreviousRank() {
        return previousRank;
    }

    public void setPreviousRank(int previousRank) {
        this.previousRank = previousRank;
    }

    public boolean isInPlayoffs() {
        return inPlayoffs;
    }

    public void setInPlayoffs(boolean inPlayoffs) {
        this.inPlayoffs = inPlayoffs;
    }
    



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamID != null ? teamID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the teamID fields are not set
        if (!(object instanceof FantasyTeam)) {
            return false;
        }
        FantasyTeam other = (FantasyTeam) object;
        if ((this.teamID == null && other.teamID != null) || (this.teamID != null && !this.teamID.equals(other.teamID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FantasyTeam[ id=" + teamID + " ]";
    }
    
}
