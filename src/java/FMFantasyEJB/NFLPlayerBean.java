/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.NFLPlayer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
