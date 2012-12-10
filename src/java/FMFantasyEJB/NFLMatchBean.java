/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.NFLMatch;
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
public class NFLMatchBean extends AbstractFacade<NFLMatch> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NFLMatchBean() {
        super(NFLMatch.class);
    }
    
    public List<NFLMatch> getMatchesByWeek(int week){
        Query createNamedQuery = em.createNamedQuery("NFLMatch.findByWeek");
        createNamedQuery.setParameter("week", week);
        
        List<NFLMatch> result = createNamedQuery.getResultList();
        int i = 0;
        while(i < result.size()){
            if(result.get(i).getAwayTeam().equals("BYE")){
                result.remove(i);
                continue;
            }                
            i++;
        }
        
        return result;
    }
    
}
