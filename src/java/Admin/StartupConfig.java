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
import Entity.NFLPlayerStats;
import Entity.RosterPlayer;
import FMFantasyEJB.FantasyLeagueBean;
import FMFantasyEJB.FantasyMatchBean;
import FMFantasyEJB.FantasyTeamBean;
import FMFantasyEJB.NFLMatchBean;
import FMFantasyEJB.NFLPlayerBean;
import FMFantasyEJB.NFLPlayerStatsBean;
import FMFantasyEJB.RosterPlayerBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
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
    @EJB
    private NFLPlayerStatsBean nflpsBean;
    
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
    public void advanceWeek(){
        for(int i = 0; i < 7; i++){
            advanceDay();
        }
    }
    public void advanceDay(){
        if(++worldDay > 7){
            worldDay = 1;
            worldWeek++;
        }
        if (worldWeek == 15){
            for(FantasyLeague l: flBean.findAll() ){
                 List<FantasyTeam> teams = ftBean.findByLeague(l);
                 List<FantasyTeam> sideA, sideB;
                 sideA = new ArrayList(teams.subList(0, 2));
                 sideB = new ArrayList(teams.subList(2,4));

                for (int j = 0; j < sideA.size(); j++)
                {   
                    FantasyMatch matchToAdd = new FantasyMatch();
                    matchToAdd.setWeek(15);
                    matchToAdd.setLeague(l);
                    matchToAdd.setTeam1(sideA.get(j));
                    matchToAdd.setTeam2(sideB.get(j));

                    fmatchBean.create(matchToAdd);
                }

            }
        }
        if (worldWeek == 16){            
             for(FantasyLeague l: flBean.findAll() ){
                List<FantasyMatch> week15Matches = fmatchBean.findByLeagueAndWeek(l,15);
                
                FantasyTeam winner1 = week15Matches.get(0).getWinningTeam();
                FantasyTeam winner2 = week15Matches.get(1).getWinningTeam();
                
                FantasyMatch finalMatch = new FantasyMatch();
                finalMatch.setTeam1(winner1);
                finalMatch.setTeam2(winner2);
                finalMatch.setLeague(l);
                finalMatch.setWeek(16);
                
                fmatchBean.create(finalMatch);        
            }
        }
        // if week > 0
        // if day == 1 - copy roster to current weeks matches
        // if day == 2 - get stats and put in matches. update w/l/d ratio, ranks, points for/against for all teams.
        if(worldWeek > 0){
            if(worldDay == 1){
                copyRostersToMatch(worldWeek);            
            }
            if(worldDay == 5){
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
            
            int WRnum = 0;
            int RBnum = 0;
            for(int j = 0; j < rosterA.size(); j++){
                RosterPlayer rp = rosterA.get(j);
                NFLPlayer nflp = rp.getNflPlayer();
                int pos = nflp.getPosition();
                
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
                        break;
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
                        break;
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
            WRnum = 0;
            RBnum = 0;
            for(int j = 0; j < rosterB.size(); j++){
                RosterPlayer rp = rosterB.get(j);
                NFLPlayer nflp = rp.getNflPlayer();
                int pos = nflp.getPosition();
                
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
                        break;
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
                        break;
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
            fmatchBean.edit(fm);
        }
        
    }
    
    public int retrievePoints(NFLPlayer nflp, int week){
        NFLPlayerStats nflps = nflpsBean.getPlayerStatsByPlayerAndWeek(nflp, week);
        if(nflps == null){
            nflps = new NFLPlayerStats(nflp, week, nfld.getWeekPointsForPlayer(nflp, week));
            nflpsBean.create(nflps);
        }
        return nflps.getFantasyPoints();
        
    }
    //for each match of the week
    //  for each nflplayer in match
    //      retreive stats of player for week
    //      assign points
    //      add points
    //  compute winner and points and whatever
    //for each league
    //  update ranks of teams
    private void doMatchesStats(int week){
        
        
        List<FantasyMatch> fMatches = fmatchBean.findByWeek(week);
        for(int i = 0; i < fMatches.size(); i++){
            FantasyMatch fm = fMatches.get(i);
            FantasyTeam team1 = fm.getTeam1();
            FantasyTeam team2 = fm.getTeam2();
            int team1Points = 0, team2Points = 0;
            
            team1Points += retrievePoints(fm.getTeam1QB(), week);
            team2Points += retrievePoints(fm.getTeam2QB(), week);
            
            team1Points += retrievePoints(fm.getTeam1RB1(), week);
            team2Points += retrievePoints(fm.getTeam2RB1(), week);

            team1Points += retrievePoints(fm.getTeam1RB2(), week);
            team2Points += retrievePoints(fm.getTeam2RB2(), week);

            team1Points += retrievePoints(fm.getTeam1WR1(), week);
            team2Points += retrievePoints(fm.getTeam2WR1(), week);

            team1Points += retrievePoints(fm.getTeam1WR2(), week);
            team2Points += retrievePoints(fm.getTeam2WR2(), week);

            team1Points += retrievePoints(fm.getTeam1WRRB(), week);
            team2Points += retrievePoints(fm.getTeam2WRRB(), week);

            team1Points += retrievePoints(fm.getTeam1TE(), week);
            team2Points += retrievePoints(fm.getTeam2TE(), week);

            team1Points += retrievePoints(fm.getTeam1K(), week);
            team2Points += retrievePoints(fm.getTeam2K(), week);

            team1Points += retrievePoints(fm.getTeam1DEF(), week);
            team2Points += retrievePoints(fm.getTeam2DEF(), week);
            
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
            
            fmatchBean.edit(fm);
            ftBean.edit(team1);
            ftBean.edit(team2);
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
