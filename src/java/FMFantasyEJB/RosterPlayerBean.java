/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import Entity.RosterPlayer;
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
public class RosterPlayerBean extends AbstractFacade<RosterPlayer> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RosterPlayerBean() {
        super(RosterPlayer.class);
    }
    
    public List<RosterPlayer> getByLeague(FantasyLeague leag){
        Query createNamedQuery = em.createNamedQuery("RosterPlayer.getByLeague");
        createNamedQuery.setParameter("league", leag);
        
        return createNamedQuery.getResultList();      
    }
    
    public List<RosterPlayer> getByTeam(FantasyTeam team) {
        Query createNamedQuery = em.createNamedQuery("RosterPlayer.getByTeam");
        createNamedQuery.setParameter("team", team);
        return createNamedQuery.getResultList();
    }
}
