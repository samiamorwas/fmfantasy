/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyMatch;
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
public class FantasyMatchBean extends AbstractFacade<FantasyMatch> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FantasyMatchBean() {
        super(FantasyMatch.class);
    }
    
    public List<FantasyMatch> findByWeek(int w){
        Query createNamedQuery = em.createNamedQuery("FantasyMatch.findByWeek");
        createNamedQuery.setParameter("week", w);
        
        return createNamedQuery.getResultList();    
    }
}
