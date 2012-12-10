/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.NFLPlayer;
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
public class NFLPlayerBean extends AbstractFacade<NFLPlayer> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NFLPlayerBean() {
        super(NFLPlayer.class);
    }
    
    public NFLPlayer getPlayerByNameAndPos(String name, int pos){
        Query createNamedQuery = em.createNamedQuery("NFLPlayer.findByNameAndPos");
        createNamedQuery.setParameter("name", name);
        createNamedQuery.setParameter("pos", pos);
        
        List<NFLPlayer> nflpList = createNamedQuery.getResultList();
        if(nflpList.isEmpty())
            return null;
        else
            return (NFLPlayer) nflpList.get(0);
    }
    
    public List<NFLPlayer> getPlayerLikeNameAndPos(String name, int pos){
        
        
        Query createNamedQuery = em.createNamedQuery("NFLPlayer.findLikeNameAndPos");
        name = name.concat("%");
        createNamedQuery.setParameter("name", name);
        createNamedQuery.setParameter("pos", pos);
        
        createNamedQuery.setMaxResults(10);
        
        List<NFLPlayer> result = createNamedQuery.getResultList();
        
        return result;
    }
    
}
