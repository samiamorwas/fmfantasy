/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Greasy
 */
@Stateless
public class FantasyUserBean extends AbstractFacade<FantasyUser> {
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FantasyUserBean() {
        super(FantasyUser.class);
    }
    
    public boolean createUser(FantasyUser u) {
        if (getUserByEmail(u.getEmail()) == null) {
            super.create(u);
            return true;
        } else {
            return false;
        }
    }

    public FantasyUser getUserByEmail(String email) {
        Query createNamedQuery = em.createNamedQuery("FantasyUser.findByEmail");
        createNamedQuery.setParameter("email", email);

        if (createNamedQuery.getResultList().size() > 0) {
            return (FantasyUser) createNamedQuery.getSingleResult();
        } else {
            return null;
        }
    }
    
}
