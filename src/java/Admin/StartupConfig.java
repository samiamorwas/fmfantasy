/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.NFLMatch;
import Entity.NFLPlayer;
import FMFantasyEJB.NFLMatchBean;
import FMFantasyEJB.NFLPlayerBean;
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
