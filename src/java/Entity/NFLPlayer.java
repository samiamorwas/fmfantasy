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
@NamedQueries( {
    @NamedQuery(name = "NFLPlayer.findByNameAndPos", query = 
                        "SELECT nflp FROM NFLPlayer nflp WHERE lower(nflp.name) = lower(:name) AND nflp.position = :pos ORDER BY nflp.seasonPoints DESC"),
    @NamedQuery(name = "NFLPlayer.findLikeNameAndPos", query = 
                        "SELECT nflp FROM NFLPlayer nflp WHERE lower(nflp.name) LIKE lower(:name) AND nflp.position = :pos ORDER BY nflp.seasonPoints DESC"),
                })

public class NFLPlayer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long NFLPlayerID;

    private long NFLDataID;
    
    private String name;
    private String team;
    
    private int passYards, passTD, passInt, rushYds, rushTD, recYds, recTD, fumTd, twoPt, fumLost, seasonPoints;
    
    
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

    public int getPassYards() {
        return passYards;
    }

    public void setPassYards(int passYards) {
        this.passYards = passYards;
    }

    public int getPassTD() {
        return passTD;
    }

    public void setPassTD(int passTD) {
        this.passTD = passTD;
    }

    public int getPassInt() {
        return passInt;
    }

    public void setPassInt(int passInt) {
        this.passInt = passInt;
    }

    public int getRushYds() {
        return rushYds;
    }

    public void setRushYds(int rushYds) {
        this.rushYds = rushYds;
    }

    public int getRushTD() {
        return rushTD;
    }

    public void setRushTD(int rushTD) {
        this.rushTD = rushTD;
    }

    public int getRecYds() {
        return recYds;
    }

    public void setRecYds(int recYds) {
        this.recYds = recYds;
    }

    public int getRecTD() {
        return recTD;
    }

    public void setRecTD(int recTD) {
        this.recTD = recTD;
    }

    public int getFumTd() {
        return fumTd;
    }

    public void setFumTd(int fumTd) {
        this.fumTd = fumTd;
    }

    public int getTwoPt() {
        return twoPt;
    }

    public void setTwoPt(int twoPt) {
        this.twoPt = twoPt;
    }

    public int getFumLost() {
        return fumLost;
    }

    public void setFumLost(int fumLost) {
        this.fumLost = fumLost;
    }

    
    public int getSeasonPoints() {
        return seasonPoints;
    }

    public void setSeasonPoints(int seasonPoints) {
        this.seasonPoints = seasonPoints;
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
