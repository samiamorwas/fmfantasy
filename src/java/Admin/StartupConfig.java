/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.FantasyLeague;
import Entity.FantasyMatch;
import Entity.FantasyTeam;
import Entity.NFLMatch;
import Entity.NFLPlayer;
import Entity.RosterPlayer;
import FMFantasyEJB.FantasyLeagueBean;
import FMFantasyEJB.FantasyMatchBean;
import FMFantasyEJB.FantasyTeamBean;
import FMFantasyEJB.NFLMatchBean;
import FMFantasyEJB.NFLPlayerBean;
import FMFantasyEJB.RosterPlayerBean;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Greasy
 */
@Singleton
@Startup
public class StartupConfig {
    @EJB
    private NFLData nfld;
    @EJB
    private NFLPlayerBean nflpb;
    @EJB
    private NFLMatchBean nflmb;
    @EJB
    private FantasyMatchBean fmatchBean;
    @EJB
    private RosterPlayerBean rpBean;
    @EJB
    private FantasyTeamBean ftBean;
    @EJB
    private FantasyLeagueBean flBean;
    
    private int worldDay;
    private int worldWeek;
    
    public StartupConfig(){
        worldWeek = 0;
        worldDay = 1;        
    }
    public int getWorldWeek(){
        return worldWeek;
    }
    public int getWorldDay(){
        return worldDay;
    }
    public void advanceDay(){
        if(++worldDay > 7){
            worldDay = 1;
            worldWeek++;
        }
        
        // if week > 0
        // if day == 1 - copy roster to current weeks matches
        // if day == 2 - get stats and put in matches. update w/l/d ratio, ranks, points for/against for all teams.
        if(worldWeek > 0){
            if(worldDay == 1){
                copyRostersToMatch(worldWeek);            
            }
            if(worldDay == 2){
                doMatchesStats(worldWeek);
            }
        }
        
        
    }
    //for each match of the week
    //  get the rosters of two teams in match
    //  copy roster to match
    private void copyRostersToMatch(int week){
        List<FantasyMatch> fMatches = fmatchBean.findByWeek(week);
        for(int i = 0; i < fMatches.size(); i++){
            FantasyMatch fm = fMatches.get(i);
            List<RosterPlayer> rosterA = rpBean.getActiveByTeam(fm.getTeam1());
            List<RosterPlayer> rosterB = rpBean.getActiveByTeam(fm.getTeam2());
            
            for(int j = 0; j < rosterA.size(); j++){
                RosterPlayer rp = rosterA.get(i);
                NFLPlayer nflp = rp.getNflPlayer();
                int pos = nflp.getPosition();
                
                int WRnum = 0;
                int RBnum = 0;
                switch(pos){
                    case 1:
                        fm.setTeam1QB(nflp);
                        break;
                    case 2:
                        switch(RBnum){
                            case 0:
                                fm.setTeam1RB1(nflp);
                                RBnum++;
                                break;
                            case 1:
                                fm.setTeam1RB2(nflp);
                                RBnum++;
                                break;
                            case 2:
                                fm.setTeam1WRRB(nflp);
                                RBnum++;
                                break;
                        }
                    case 3:
                        switch(WRnum){
                            case 0:
                                fm.setTeam1WR1(nflp);
                                WRnum++;
                                break;
                            case 1:
                                fm.setTeam1WR2(nflp);
                                WRnum++;
                                break;
                            case 2:
                                fm.setTeam1WRRB(nflp);
                                WRnum++;
                                break;
                        }
                    case 4:
                        fm.setTeam1TE(nflp);
                        break;
                    case 5:
                        fm.setTeam1K(nflp);
                        break;
                    case 6:
                        fm.setTeam1DEF(nflp);
                        break;
                }
            }
            for(int j = 0; j < rosterB.size(); j++){
                RosterPlayer rp = rosterB.get(i);
                NFLPlayer nflp = rp.getNflPlayer();
                int pos = nflp.getPosition();
                
                int WRnum = 0;
                int RBnum = 0;
                switch(pos){
                    case 1:
                        fm.setTeam2QB(nflp);
                        break;
                    case 2:
                        switch(RBnum){
                            case 0:
                                fm.setTeam2RB1(nflp);
                                RBnum++;
                                break;
                            case 1:
                                fm.setTeam2RB2(nflp);
                                RBnum++;
                                break;
                            case 2:
                                fm.setTeam2WRRB(nflp);
                                RBnum++;
                                break;
                        }
                    case 3:
                        switch(WRnum){
                            case 0:
                                fm.setTeam2WR1(nflp);
                                WRnum++;
                                break;
                            case 1:
                                fm.setTeam2WR2(nflp);
                                WRnum++;
                                break;
                            case 2:
                                fm.setTeam2WRRB(nflp);
                                WRnum++;
                                break;
                        }
                    case 4:
                        fm.setTeam2TE(nflp);
                        break;
                    case 5:
                        fm.setTeam2K(nflp);
                        break;
                    case 6:
                        fm.setTeam2DEF(nflp);
                        break;
                }
            }            
            
        }
        
    }
    //for each match of the week
    //  for each nflplayer in match
    //      retreive stats of player for week
    //      assign points
    //      add points
    //  compute winner or something
    //  update points/rank/w/l/d of teams in match
    private void doMatchesStats(int week){
        
        
        List<FantasyMatch> fMatches = fmatchBean.findByWeek(week);
        for(int i = 0; i < fMatches.size(); i++){
            FantasyMatch fm = fMatches.get(i);
            FantasyTeam team1 = fm.getTeam1();
            FantasyTeam team2 = fm.getTeam2();
            int team1Points = 0, team2Points = 0;
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1QB(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2QB(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1RB1(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2RB1(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1RB2(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2RB2(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1WR1(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2WR1(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1WR2(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2WR2(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1WRRB(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2WRRB(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1TE(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2TE(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1K(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2K(), week);
            
            team1Points += nfld.getWeekPointsForPlayer(fm.getTeam1DEF(), week);
            team2Points += nfld.getWeekPointsForPlayer(fm.getTeam2DEF(), week);
            
            fm.setTeam1Points(team1Points);
            fm.setTeam2Points(team2Points);
            
            if(team1Points > team2Points){
                team1.setWins(team1.getWins() + 1);
                team2.setLosses(team2.getLosses() + 1);
            }
            if(team1Points == team2Points){
                team1.setDraws(team1.getDraws() + 1);
                team2.setDraws(team2.getDraws() + 1);
            }
            if(team1Points < team2Points){
                team1.setLosses(team1.getLosses() + 1);
                team2.setWins(team2.getWins() + 1);
            }
            
            team1.setPointsFor(team1.getPointsFor() + team1Points);
            team1.setPointsAgainst(team1.getPointsAgainst() + team2Points);
            
            team2.setPointsFor(team2.getPointsFor() + team2Points);
            team2.setPointsAgainst(team2.getPointsAgainst() + team1Points);            
        }
        
        List<FantasyLeague> leagues = flBean.findAll();
        for(int i = 0; i < leagues.size(); i++){
            List<FantasyTeam> teams = ftBean.findByLeague(leagues.get(i));
            
            //DO SORTING BY WINS
            Collections.sort(teams, new TeamWinComparator());
            
            for(int j = 0; j < teams.size(); j++){
                FantasyTeam team = teams.get(j);
                team.setPreviousRank(team.getRank());
                team.setRank(j + 1);                
            }
        }
        
    }
    public class TeamWinComparator implements Comparator<FantasyTeam>{
 
    @Override
    public int compare(FantasyTeam t1, FantasyTeam t2) {
        if(t1.getWins() > t2.getWins())
            return -1;
        if(t1.getWins() < t2.getWins())
            return 1;
        return 0;
    }
}
    
    @PostConstruct
    public void startupStuff(){
        
        List<NFLPlayer> allPlayers = nfld.getAllPlayers();        
        for(int i = 0; i < allPlayers.size(); i++){
            NFLPlayer nflp = allPlayers.get(i);            
            nflpb.create(nflp);
        }
        
        List<NFLMatch> allMatches = nfld.getNFLSchedule();        
        for(int i = 0; i < allMatches.size(); i++){
            NFLMatch nflm = allMatches.get(i);
            nflmb.create(nflm);
        }
    }
}
