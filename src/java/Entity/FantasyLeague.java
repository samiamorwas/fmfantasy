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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "FantasyLeague.findByOwner", query = "SELECT league FROM FantasyLeague league WHERE league.leagueOwner = :owner"),
                @NamedQuery(name = "FantasyLeague.findByMember", query = "SELECT team.league FROM FantasyTeam team WHERE team.teamOwner = :member")})
public class FantasyLeague implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long leagueID;
    
    private String leagueName;
    
    @ManyToOne    
    private FantasyUser leagueOwner;
    
    private boolean finishedDraft;
    
    private boolean startedDraft;
    
    @OneToMany(mappedBy = "league")
    private List<FantasyTeam> memberTeams;    

    @OneToMany(mappedBy = "league")
    private List<RosterPlayer> rosterPlayers;

    public Long getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(Long leagueID) {
        this.leagueID = leagueID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public FantasyUser getLeagueOwner() {
        return leagueOwner;
    }

    public void setLeagueOwner(FantasyUser leagueOwner) {
        this.leagueOwner = leagueOwner;
    }
    
    public boolean getDraftStarted() {
        return startedDraft;
    }
    
    public void setDraftStarted(boolean startedDraft) {
        this.startedDraft = startedDraft;
    }

    public boolean getFinishedDraft() {
        return finishedDraft;
    }

    public void setFinishedDraft(boolean finishedDraft) {
        this.finishedDraft = finishedDraft;
    }

    public List<FantasyTeam> getMemberTeams() {
        return memberTeams;
    }

    public void setMemberTeams(List<FantasyTeam> memberTeams) {
        this.memberTeams = memberTeams;
    }

    public List<RosterPlayer> getRosterPlayers() {
        return rosterPlayers;
    }

    public void setRosterPlayers(List<RosterPlayer> rosterPlayers) {
        this.rosterPlayers = rosterPlayers;
    }
     
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (leagueID != null ? leagueID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FantasyLeague)) {
            return false;
        }
        FantasyLeague other = (FantasyLeague) object;
        if ((this.leagueID == null && other.leagueID != null) || (this.leagueID != null && !this.leagueID.equals(other.leagueID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FantasyLeague[ id=" + leagueID + " ]";
    }
    
}
