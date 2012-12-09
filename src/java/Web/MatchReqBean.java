package Web;

import Admin.NFLData;
import Entity.FantasyMatch;
import Entity.FantasyTeam;
import Entity.NFLPlayer;
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
    private NFLData nfldata;
    
    @Inject
    SessionBean sessionBean;
    
    public MatchReqBean() {
    }
    
    public FantasyMatch getCurrentMatch(FantasyTeam team) {
        int week = sessionBean.getLeagueWeek();
        return matchBean.findByCurrentTeam(week, team);
    }
    
    public int getPointsForPlayer(NFLPlayer player) {
        int week = sessionBean.getLeagueWeek();
        return nfldata.getWeekPointsForPlayer(player, week);
    }
}
