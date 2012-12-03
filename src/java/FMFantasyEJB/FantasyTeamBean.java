/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
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
public class FantasyTeamBean extends AbstractFacade<FantasyTeam> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FantasyTeamBean() {
        super(FantasyTeam.class);
    }
    
    public boolean createTeam(FantasyTeam t) {
        // should do some kind of check that team doesnt exist.
        // ie: user doesnt already have team in league
        //if (getTeamByID(t.get) == null) {
            super.create(t);
            return true;
        //} else {
        //    return false;
        //}
    }
    
    public List<FantasyTeam> findByLeague(FantasyLeague l){
        Query createNamedQuery = em.createNamedQuery("FantasyTeam.findByLeague");
        createNamedQuery.setParameter("league", l);
        
        return createNamedQuery.getResultList();
    }
    
    public List<FantasyTeam> findByOwner(FantasyUser u){
        Query createNamedQuery = em.createNamedQuery("FantasyTeam.findByOwner");
        createNamedQuery.setParameter("owner", u);
        
        return createNamedQuery.getResultList();        
    }
    
    public FantasyTeam findByOwnerAndLeague(FantasyUser u, FantasyLeague l){
        Query createNamedQuery = em.createNamedQuery("FantasyTeam.findByOwnerAndLeague");
        createNamedQuery.setParameter("owner", u);
        createNamedQuery.setParameter("league", l);
        
        return (FantasyTeam) createNamedQuery.getSingleResult();        
    }
    
}
