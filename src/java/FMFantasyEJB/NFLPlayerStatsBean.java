/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Admin.NFLData;
import Entity.NFLPlayer;
import Entity.NFLPlayerStats;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Greasy
 */
@Stateless
public class NFLPlayerStatsBean extends AbstractFacade<NFLPlayerStats> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;
    
    @Inject
    NFLData nfld;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NFLPlayerStatsBean() {
        super(NFLPlayerStats.class);
    }
    
    public NFLPlayerStats getPlayerStatsByPlayerAndWeek(NFLPlayer nflp, int week){
        Query createNamedQuery = em.createNamedQuery("NFLPlayerStats.findByPlayerAndWeek");
        createNamedQuery.setParameter("player", nflp);
        createNamedQuery.setParameter("week", week);
        
        List<NFLPlayerStats> nflpsList = createNamedQuery.getResultList();
        if(nflpsList.isEmpty()){
            return null;
        }
        else
            return (NFLPlayerStats) nflpsList.get(0);
    }
    
}
