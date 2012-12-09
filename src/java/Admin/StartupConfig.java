/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.FantasyMatch;
import Entity.NFLMatch;
import Entity.NFLPlayer;
import Entity.RosterPlayer;
import FMFantasyEJB.FantasyMatchBean;
import FMFantasyEJB.NFLMatchBean;
import FMFantasyEJB.NFLPlayerBean;
import FMFantasyEJB.RosterPlayerBean;
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
    NFLPlayerBean nflpb;
    @EJB
    NFLMatchBean nflmb;
    @EJB
    FantasyMatchBean fmatchBean;
    @EJB
    RosterPlayerBean rpBean;
    
    private int worldDay;
    private int worldWeek;
    
    public void setWeeksStart(){
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
        
    }
    
    @PostConstruct
    public void startupStuff(){
        setWeeksStart();
        NFLData nfld = new NFLData();
        
        
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
