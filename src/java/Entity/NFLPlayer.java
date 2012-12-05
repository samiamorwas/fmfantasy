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
    @NamedQuery(name = "NFLPlayer.findByNameAndPos", query = 
                        "SELECT nflp FROM NFLPlayer nflp WHERE lower(nflp.name) = lower(:name) AND nflp.position = :pos"),
     @NamedQuery(name = "NFLPlayer.findLikeNameAndPos", query = 
                        "SELECT nflp FROM NFLPlayer nflp WHERE lower(nflp.name) LIKE lower(:name) AND nflp.position = :pos"),
                })

public class NFLPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long NFLPlayerID;

    private long NFLDataID;
    
    private String name;
    private String team;
    // This might get confusing in addition to the String 'team' field, but that
    // needs to stay since its used in the NFLData class, and this needs to be
    // here for use in the FantasyMatch entity -- Jess
    private NFLTeam nflTeam;
    
    
    /*
     * lets say 1 - qb, etc
     * 2 - RB
     * 3 - WR
     * 4 - TE
     * 5 - K
     * 6 - D
     */
    private int position;
    
    public String getPositionString(){
        switch(position){
            case 1:
                return "QB";
            case 2:
                return "RB";
            case 3:
                return "WR";
            case 4:
                return "TE";
            case 5:
                return "K";
            case 6:
                return "DEF";
            default:
                return "Uh oh";
        }
    }
    public Long getNFLPlayerID() {
        return NFLPlayerID;
    }

    public void setNFLPlayerID(Long NFLPlayerID) {
        this.NFLPlayerID = NFLPlayerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getNFLDataID() {
        return NFLDataID;
    }

    public void setNFLDataID(long NFLDataID) {
        this.NFLDataID = NFLDataID;
    }
    
    
    
    /*
     * todo: api stuff
     */
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (NFLPlayerID != null ? NFLPlayerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the NFLPlayerID fields are not set
        if (!(object instanceof NFLPlayer)) {
            return false;
        }
        NFLPlayer other = (NFLPlayer) object;
        if ((this.NFLPlayerID == null && other.NFLPlayerID != null) || (this.NFLPlayerID != null && !this.NFLPlayerID.equals(other.NFLPlayerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.NFLPlayer[ id=" + NFLPlayerID + " ]";
    }
    
}
