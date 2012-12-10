package Web;

import Admin.NFLData;
import Entity.FantasyMatch;
import Entity.NFLPlayer;
import Entity.NFLPlayerStats;
import FMFantasyEJB.NFLPlayerStatsBean;
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
    @EJB
    private NFLPlayerStatsBean nflpsBean;
    
    FantasyMatch match;
    
    public MatchReqBean() {
        match = null;
    }
    
    public String viewMatch(FantasyMatch fm){
        match = fm;
        
        return "gamecenter.xhtml";
    }
    public int getMatchWeek(){
        if(match != null) {
            return match.getWeek();
        }
        else {
            return -1;
        }
    }

    public List<NFLPlayerStats> getTeam1PlayerStats(){
        List<NFLPlayerStats> result = new ArrayList<NFLPlayerStats>();
        
        if(match==null) {
            return result;
        }
        if(match.getTeam2QB() == null) {
            return result;
        }
        
        int week = match.getWeek();
        
        NFLPlayerStats qb = nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1QB(), week);
        if(qb != null){
            result.add(qb);
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1RB1(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1RB2(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1WR1(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1WR2(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1WRRB(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1TE(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1K(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam1DEF(), week));
        }
        else{
            result.add(new NFLPlayerStats(match.getTeam1QB(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1RB1(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1RB2(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1WR1(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1WR2(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1WRRB(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1TE(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1K(),week,0));
            result.add(new NFLPlayerStats(match.getTeam1DEF(),week,0));
        }
        
        return result;
    }
    public List<NFLPlayerStats> getTeam2PlayerStats(){
        List<NFLPlayerStats> result = new ArrayList<NFLPlayerStats>();
        
        if(match == null) {
            return result;
        }
        
        if(match.getTeam2QB() == null) {
            return result;
        }
        
        int week = match.getWeek();
        
        NFLPlayerStats qb = nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2QB(), week);
        if(qb != null){
            result.add(qb);
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2RB1(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2RB2(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2WR1(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2WR2(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2WRRB(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2TE(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2K(), week));
            result.add(nflpsBean.getPlayerStatsByPlayerAndWeek(match.getTeam2DEF(), week));
        }
        else{
            result.add(new NFLPlayerStats(match.getTeam2QB(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2RB1(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2RB2(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2WR1(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2WR2(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2WRRB(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2TE(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2K(),week,0));
            result.add(new NFLPlayerStats(match.getTeam2DEF(),week,0));
        }        
        
        return result;
    }
    
    public int getPointsForPlayer(NFLPlayer player) {
        int week = match.getWeek();
        if(player != null) {
            return nfldata.getWeekPointsForPlayer(player, week);
        }
        else {
            return 0;
        }
    }
    
    public String getTeam1Name(){
        if(match != null) {
            return match.getTeam1().getTeamName();
        }
        else {
            return "";
        }
    }
    public String getTeam2Name(){
        if(match != null) {
            return match.getTeam2().getTeamName();
        }
        else {
            return "";
        }
    }
    public int getTeam1Total(){
        if(match != null) {
            return match.getTeam1Points();
        }
        else {
            return 0;
        }
    }
    public int getTeam2Total(){
        if(match != null) {
            return match.getTeam2Points();
        }
        else {
            return 0;
        }
    }
    
}
