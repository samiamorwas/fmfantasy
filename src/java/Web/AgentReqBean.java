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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@SessionScoped
public class AgentReqBean {
    @EJB
    private FMFantasyEJB.RosterPlayerBean rpBean;
    @EJB
    private FMFantasyEJB.NFLPlayerBean nflpBean;
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;
    
    private String error;
    
    @Inject
    UserReqBean uReq;
    @Inject
    LeagueReqBean lReq;
    
    private String agentTextEntry;
    private String draftTextEntry;
    private boolean QB,RB,WR,TE,KCK,DEF;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public AgentReqBean() {
        clearInputs();
        QB=RB=WR=TE=KCK=DEF=true;
    }
    private void clearInputs(){
        agentTextEntry="";
        draftTextEntry="";
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public boolean isPickable(NFLPlayer nflp){
        boolean result = false;
        
        FantasyUser user = uReq.getUser();
        FantasyLeague leag = lReq.getLeague();
        FantasyTeam team = teamBean.findByOwnerAndLeague(user, leag);
        List<RosterPlayer> allMembers = rpBean.getByTeam(team);
        
        //15 total players allowed? I don't remember
        if (allMembers.size() < 15) {
            result = true;
        }
        
        return result;
    }
    public void pickupPlayer(NFLPlayer nflp){
        if(!isPickable(nflp)) {
            return;
        }
        FantasyUser user = uReq.getUser();
        FantasyLeague leag = lReq.getLeague();
        FantasyTeam team = teamBean.findByOwnerAndLeague(user, leag);
        
        
        RosterPlayer rp = new RosterPlayer();
        rp.setTeam(team);
        rp.setLeague(leag);
        rp.setRosterSlot(0); //start benched
        rp.setNflPlayer(nflp);
        
        rpBean.create(rp);
        
        clearInputs();
    }
    public List<String> draftAutoComplete(String name){
        List<String> result = new ArrayList<String>();
  
        List<NFLPlayer> nflpResult = getFreeAgentsLike(name);
        for(int i = 0; i < nflpResult.size(); i++){
            result.add(nflpResult.get(i).getName());            
        }
        
        return result;
    }

    public class NFLPlayerComparable implements Comparator<NFLPlayer>{
        @Override
        public int compare(NFLPlayer o1, NFLPlayer o2) {
            int result = 0;

            if(o1.getSeasonPoints() > o2.getSeasonPoints()) {
                result = -1;
            }
            if(o1.getSeasonPoints() < o2.getSeasonPoints()) {
                result = 1;
            }
            return result;
        }
    }
    private List<NFLPlayer> getFiltered(String name){
        if (name == null) 
            name = "";

        List<NFLPlayer> nflpList = new ArrayList<NFLPlayer>();
        if(QB) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 1));
        }
        if(RB) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 2));
        }
        if(WR) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 3));
        }
        if(TE) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 4));
        }
        if(KCK) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 5));
        }
        if(DEF) {
            nflpList.addAll(nflpBean.getPlayerLikeNameAndPos(name, 6));
        }
        
        AgentReqBean.NFLPlayerComparable nflpc = new AgentReqBean.NFLPlayerComparable();
        Collections.sort(nflpList, nflpc);
        
        return nflpList;
    }
    public List<NFLPlayer> getFreeAgentsTable(){
        return getFreeAgentsLike(agentTextEntry);
    }
    public List<NFLPlayer> getFreeAgentsLike(String name){
        FantasyLeague leag = lReq.getLeague();
        
        List<NFLPlayer> nflpList = getFiltered(name);
        
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
        
        FantasyLeague leag = lReq.getLeague();
        
        RosterPlayer rp = new RosterPlayer();
        rp.setTeam(ft);
        rp.setLeague(leag);
        rp.setRosterSlot(0); //start benched
        
        //nfl stuff
        List<NFLPlayer> nflpList = getFreeAgentsLike(draftTextEntry);
        if(nflpList.isEmpty()){
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
        
        clearInputs();
        return result;
    }

    public String getAgentTextEntry() {
        return agentTextEntry;
    }

    public void setAgentTextEntry(String agentTextEntry) {
        this.agentTextEntry = agentTextEntry;
    }

    public boolean isQB() {
        return QB;
    }

    public void setQB(boolean QB) {
        this.QB = QB;
    }

    public boolean isRB() {
        return RB;
    }

    public void setRB(boolean RB) {
        this.RB = RB;
    }

    public boolean isWR() {
        return WR;
    }

    public void setWR(boolean WR) {
        this.WR = WR;
    }

    public boolean isTE() {
        return TE;
    }

    public void setTE(boolean TE) {
        this.TE = TE;
    }

    public boolean isKCK() {
        return KCK;
    }

    public void setKCK(boolean KCK) {
        this.KCK = KCK;
    }

    public boolean isDEF() {
        return DEF;
    }

    public void setDEF(boolean DEF) {
        this.DEF = DEF;
    }

    public String getDraftTextEntry() {
        return draftTextEntry;
    }

    public void setDraftTextEntry(String draftTextEntry) {
        this.draftTextEntry = draftTextEntry;
    }
    
}
