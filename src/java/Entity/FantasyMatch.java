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

/**
 *
 * @author Greasy
 */
@Entity
public class FantasyMatch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fMatchID;

    /*
     * todo: everything
     */

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
