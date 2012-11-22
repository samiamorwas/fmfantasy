/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Greasy
 */
@Entity
@NamedQueries( {@NamedQuery(name = "Invitation.findBySender", query = "SELECT invite FROM Invitation invite WHERE invite.sender = :sender"),
                @NamedQuery(name = "Invitation.findByReceiver", query = "SELECT invite FROM Invitation invite WHERE invite.receiver = :receiver"),
                @NamedQuery(name = "Invitation.findByLeague", query = "SELECT invite FROM Invitation invite WHERE invite.league = :league")})
public class Invitation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invitationID;
    
    @ManyToOne
    @JoinColumn
    private FantasyUser sender;
    
    @ManyToOne
    @JoinColumn
    private FantasyUser receiver;
    
    @ManyToOne
    @JoinColumn
    private FantasyLeague league;

    public Long getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(Long invitationID) {
        this.invitationID = invitationID;
    }

    public FantasyUser getSender() {
        return sender;
    }

    public void setSender(FantasyUser sender) {
        this.sender = sender;
    }

    public FantasyUser getReceiver() {
        return receiver;
    }

    public void setReceiver(FantasyUser receiver) {
        this.receiver = receiver;
    }

    public FantasyLeague getLeague() {
        return league;
    }

    public void setLeague(FantasyLeague league) {
        this.league = league;
    }
    
    
    
    



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invitationID != null ? invitationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the invitationID fields are not set
        if (!(object instanceof Invitation)) {
            return false;
        }
        Invitation other = (Invitation) object;
        if ((this.invitationID == null && other.invitationID != null) || (this.invitationID != null && !this.invitationID.equals(other.invitationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Invitation[ id=" + invitationID + " ]";
    }
    
}
