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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "FantasyUser.findByEmail", query = "SELECT usr FROM FantasyUser usr WHERE usr.email = :email"),
                @NamedQuery(name = "FantasyUser.findAllStartingWith", query = "SELECT user from FantasyUser user where user.email LIKE :email")})
public class FantasyUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userID;
    
    

    protected String email;
    protected String password;
    
    @OneToMany(mappedBy = "leagueOwner")
    private List<FantasyLeague> leaguesOwned;
    
    @OneToMany(mappedBy = "teamOwner")
    private List<FantasyTeam> ownedTeams;
    
    
    public FantasyUser(){    
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FantasyLeague> getLeaguesOwned() {
        return leaguesOwned;
    }

    public void setLeaguesOwned(List<FantasyLeague> leaguesOwned) {
        this.leaguesOwned = leaguesOwned;
    }

    public List<FantasyTeam> getOwnedTeams() {
        return ownedTeams;
    }

    public void setOwnedTeams(List<FantasyTeam> ownedTeams) {
        this.ownedTeams = ownedTeams;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FantasyUser)) {
            return false;
        }
        FantasyUser other = (FantasyUser) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.User[ id=" + userID + " ]";
    }
    
}
