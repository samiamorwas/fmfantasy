/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyLeague;
import Entity.FantasyUser;
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
public class FantasyLeagueBean extends AbstractFacade<FantasyLeague> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FantasyLeagueBean() {
        super(FantasyLeague.class);
    }
    public boolean createLeague(FantasyLeague l) {
        //some kind of check on l? idk
        super.create(l);
        return true;
    }
    
    public List<FantasyLeague> findByOwner(FantasyUser user){
        Query createNamedQuery = em.createNamedQuery("FantasyLeague.findByOwner");
        createNamedQuery.setParameter("owner", user);
        
        return createNamedQuery.getResultList();    
    }
    public List<FantasyLeague> findByMember(FantasyUser user){
        Query createNamedQuery = em.createNamedQuery("FantasyLeague.findByMember");
        createNamedQuery.setParameter("member", user);
        
        return createNamedQuery.getResultList();
    }
    
}
