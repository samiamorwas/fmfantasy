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

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "NFLMatch.findByWeek", query = "SELECT match FROM NFLMatch match WHERE match.week = :week") })
public class NFLMatch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long NFLMatchID;

    
    private int week;
    private String homeTeam;
    private String awayTeam;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Long getNFLMatchID() {
        return NFLMatchID;
    }

    public void setNFLMatchID(Long NFLMatchID) {
        this.NFLMatchID = NFLMatchID;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (NFLMatchID != null ? NFLMatchID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the NFLMatchID fields are not set
        if (!(object instanceof NFLMatch)) {
            return false;
        }
        NFLMatch other = (NFLMatch) object;
        if ((this.NFLMatchID == null && other.NFLMatchID != null) || (this.NFLMatchID != null && !this.NFLMatchID.equals(other.NFLMatchID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.NFLMatch[ id=" + NFLMatchID + " ]";
    }
    
}
