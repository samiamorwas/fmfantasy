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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {
    @NamedQuery(name = "NFLPlayerStats.findByPlayerAndWeek", query = 
                        "SELECT nflps FROM NFLPlayerStats nflps WHERE nflps.player = :player AND nflps.week = :week")
})
public class NFLPlayerStats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private NFLPlayer player;
    private int week;
    
    //possible todo: add _all_ statistics
    
    private int fantasyPoints;

    public NFLPlayerStats(){
        
    }
    public NFLPlayerStats(NFLPlayer player, int week, int fantasyPoints) {
        this.player = player;
        this.week = week;
        this.fantasyPoints = fantasyPoints;
    }

    public NFLPlayer getPlayer() {
        return player;
    }

    public void setPlayer(NFLPlayer player) {
        this.player = player;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getFantasyPoints() {
        return fantasyPoints;
    }

    public void setFantasyPoints(int fantasyPoints) {
        this.fantasyPoints = fantasyPoints;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NFLPlayerStats)) {
            return false;
        }
        NFLPlayerStats other = (NFLPlayerStats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.NFLPlayerStats[ id=" + id + " ]";
    }
    
}
