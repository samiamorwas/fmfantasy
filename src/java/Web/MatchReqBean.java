package Web;

import Entity.FantasyMatch;
import Entity.FantasyTeam;
import FMFantasyEJB.FantasyMatchBean;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author mhaarhaus
 */
@Named
@Stateful
@RequestScoped
public class MatchReqBean {
    @EJB
    private FantasyMatchBean matchBean;
    
    @Inject
    SessionBean sessionBean;
    
    public MatchReqBean() {
    }
    
    public FantasyMatch getCurrentMatch(FantasyTeam team) {
        int week = sessionBean.getLeagueWeek();
        return matchBean.findByCurrentTeam(week, team);
    }
}
