package Web;

import Admin.NFLData;
import Entity.FantasyMatch;
import Entity.NFLPlayer;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author mhaarhaus
 */
@Named
@Stateful
@SessionScoped
public class MatchReqBean {
    @EJB
    private NFLData nfldata;
    
    FantasyMatch match;
    
    public MatchReqBean() {
        match = null;
    }
    
    public String viewMatch(FantasyMatch fm){
        match = fm;
        
        return "gamecenter.xhtml";
    }
    public int getMatchWeek(){
        if(match != null)
            return match.getWeek();
        else
            return -1;
    }

    public List<NFLPlayer> getTeam1Players(){
        List<NFLPlayer> result = new ArrayList<NFLPlayer>();
        
        if(match==null)
            return result;
        
        result.add(match.getTeam1QB());
        result.add(match.getTeam1RB1());
        result.add(match.getTeam1RB2());
        result.add(match.getTeam1WR1());
        result.add(match.getTeam1WR2());
        result.add(match.getTeam1WRRB());
        result.add(match.getTeam1TE());
        result.add(match.getTeam1K());
        result.add(match.getTeam1DEF());
        
        return result;
    }
    public List<NFLPlayer> getTeam2Players(){
        List<NFLPlayer> result = new ArrayList<NFLPlayer>();
        
        if(match == null)
            return result;
        
        result.add(match.getTeam2QB());
        result.add(match.getTeam2RB1());
        result.add(match.getTeam2RB2());
        result.add(match.getTeam2WR1());
        result.add(match.getTeam2WR2());
        result.add(match.getTeam2WRRB());
        result.add(match.getTeam2TE());
        result.add(match.getTeam2K());
        result.add(match.getTeam2DEF());
        
        return result;
    }
    
    public int getPointsForPlayer(NFLPlayer player) {
        int week = match.getWeek();
        if(player != null)
            return nfldata.getWeekPointsForPlayer(player, week);
        else
            return 0;
    }
    
    public String getTeam1Name(){
        if(match != null)
            return match.getTeam1().getTeamName();
        else
            return "";
    }
    public String getTeam2Name(){
        if(match != null)
            return match.getTeam2().getTeamName();
        else
            return "";
    }
    public int getTeam1Total(){
        if(match != null)
            return match.getTeam1Points();
        else
            return 0;
    }
    public int getTeam2Total(){
        if(match != null)
            return match.getTeam2Points();
        else
            return 0;
    }
    
}
