/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyLeague;
import Entity.FantasyUser;
import Entity.Invitation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Greasy
 */
@Stateless
public class InvitationBean extends AbstractFacade<Invitation> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvitationBean() {
        super(Invitation.class);
    }
    
    public boolean createInvitation(Invitation inv){
        //checking
        super.create(inv);
        return true;
    }
    
    public List<Invitation> findBySender(FantasyUser user){
        Query createNamedQuery = em.createNamedQuery("Invitation.findBySender");
        createNamedQuery.setParameter("sender", user);
        
        return createNamedQuery.getResultList();   
    }
    public List<Invitation> findByReceiver(FantasyUser user){
        Query createNamedQuery = em.createNamedQuery("Invitation.findByReceiver");
        createNamedQuery.setParameter("receiver", user);
        
        return createNamedQuery.getResultList();   
    }
    public List<Invitation> findByLeague(FantasyLeague league){
        Query createNamedQuery = em.createNamedQuery("Invitation.findByLeague");
        createNamedQuery.setParameter("league", league);
        
        return createNamedQuery.getResultList();   
    }
    
}
