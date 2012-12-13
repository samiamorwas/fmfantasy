/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {
    @NamedQuery(name = "RosterPlayer.getByLeague", query = 
                        "SELECT rp FROM RosterPlayer rp WHERE rp.league = :league"),
    @NamedQuery(name = "RosterPlayer.getByTeam", query =
                        "SELECT rp FROM RosterPlayer rp WHERE rp.team = :team"),
    @NamedQuery(name = "RosterPlayer.getActiveByTeam", query =
                        "SELECT rp FROM RosterPlayer rp WHERE rp.team = :team AND rp.rosterSlot != 0"),
    @NamedQuery(name = "RosterPlayer.getBenchByTeam", query =
                        "SELECT rp FROM RosterPlayer rp WHERE rp.team = :team AND rp.rosterSlot = 0")       
} )
public class RosterPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rpID;
    
    @ManyToOne
    @JoinColumn
    private FantasyTeam team;
    
    @ManyToOne
    @JoinColumn
    private FantasyLeague league;
    
    @ManyToOne
    @JoinColumn
    private NFLPlayer nflPlayer;
    
    /*
     * 0 benched, 1-10 active positions? this could work
     * eg
     * 1 - QB
     * 2 - WR
     * 3 - RB
     * 4 - WR / RB
     * 5 - TE
     * 6 - K
     * 7 - DEF
    */
    @Column(name = "ROSTERSLOT")
    private int rosterSlot; //0 benched, 1-10 active positions? this could work
    
    public RosterPlayer(){}
    public RosterPlayer(FantasyTeam ft, FantasyLeague fl, NFLPlayer nflp){
        this.team = ft;
        this.league = fl;
        this.nflPlayer = nflp;
    }
    
    public String getPositionString(){
        return nflPlayer.getPositionString();        
    }
    public Long getRpID() {
        return rpID;
    }

    public void setRpID(Long rpID) {
        this.rpID = rpID;
    }

    public FantasyTeam getTeam() {
        return team;
    }

    public void setTeam(FantasyTeam team) {
        this.team = team;
    }

    public FantasyLeague getLeague() {
        return league;
    }

    public void setLeague(FantasyLeague league) {
        this.league = league;
    }

    public NFLPlayer getNflPlayer() {
        return nflPlayer;
    }

    public void setNflPlayer(NFLPlayer nflPlayer) {
        this.nflPlayer = nflPlayer;
    }

    public int getRosterSlot() {
        return rosterSlot;
    }

    public void setRosterSlot(int rosterSlot) {
        this.rosterSlot = rosterSlot;
    }
    
    



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rpID != null ? rpID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rpID fields are not set
        if (!(object instanceof RosterPlayer)) {
            return false;
        }
        RosterPlayer other = (RosterPlayer) object;
        if ((this.rpID == null && other.rpID != null) || (this.rpID != null && !this.rpID.equals(other.rpID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.RosterPlayer[ id=" + rpID + " ]";
    }
    
}
