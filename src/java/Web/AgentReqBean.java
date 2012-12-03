/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import Entity.FantasyUser;
import Entity.NFLPlayer;
import Entity.RosterPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@RequestScoped
public class AgentReqBean {
    @EJB
    private FMFantasyEJB.RosterPlayerBean rpBean;
    @EJB
    private FMFantasyEJB.NFLPlayerBean nflpBean;
    
    private String name;    
    private String error;
    
       
    @Inject
    SessionBean sessionBean;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public AgentReqBean() {
    }
    
    public List<String> draftAutoComplete(String userText){
        List<String> result = new ArrayList<String>();
  
        name = userText;
        List<NFLPlayer> nflpResult = getFreeAgentsLike();
        for(int i = 0; i < nflpResult.size(); i++){
            result.add(nflpResult.get(i).getName());            
        }
        
        return result;
    }
    

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private List<NFLPlayer> getFiltered(){
        
        List<NFLPlayer> nflpList = new ArrayList<NFLPlayer>();
        if(sessionBean.isQB())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 1));
        if(sessionBean.isRB())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 2));
        if(sessionBean.isWR())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 3));
        if(sessionBean.isTE())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 4));
        if(sessionBean.isKCK())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 5));
        if(sessionBean.isDEF())
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 6));
        
        return nflpList;
    }
    public List<NFLPlayer> getFreeAgentsLike(){
        FantasyLeague leag = sessionBean.getLeague();
        
        List<NFLPlayer> nflpList = getFiltered();
        
        //remove players already picked up in league
        List<RosterPlayer> rpList = rpBean.getByLeague(leag);
        
        int i = 0;
        while(i < nflpList.size()){
            NFLPlayer nflp = nflpList.get(i);
            boolean found = false;
            
            Iterator it = rpList.iterator();
            while(it.hasNext()){
                RosterPlayer rp = (RosterPlayer) it.next();
                
                if( nflp.getNFLPlayerID() == rp.getNflPlayer().getNFLPlayerID()){
                    nflpList.remove(i);
                    found = true;
                    break;
                }
            }
            if(found) {
                continue;
            }
            
            i++;
        }
        return nflpList;
    }
    public String draftPlayer(FantasyTeam ft){
        String result;
        
        FantasyLeague leag = sessionBean.getLeague();
        
        RosterPlayer rp = new RosterPlayer();
        rp.setTeam(ft);
        rp.setLeague(leag);
        rp.setRosterSlot(0); //start benched
        
        //nfl stuff
        List<NFLPlayer> nflpList = getFreeAgentsLike();
        if(nflpList.size() == 0){
            error = "No Such Player!";
            return "no_such_player";
        }
        rp.setNflPlayer(nflpList.get(0));
        
        List<RosterPlayer> draftedPlayers = rpBean.getByLeague(leag);
        for(int i = 0; i < draftedPlayers.size(); i++){
            if(draftedPlayers.get(i).getNflPlayer().getNFLDataID() == rp.getNflPlayer().getNFLDataID()){
                error = "Player already drafted!";
                return "already_drafted";
            }            
        }        
        
        rpBean.create(rp);
        
        result = "player_drafted";
        
        return result;
    }
    
}
