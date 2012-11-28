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
    private boolean QB;
    private boolean RB;
    private boolean WR;
    private boolean TE;
    private boolean K;
    private boolean DEF;
    
       
    @Inject
    SessionBean sessionBean;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public AgentReqBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isK() {
        return K;
    }

    public void setK(boolean K) {
        this.K = K;
    }

    public boolean isDEF() {
        return DEF;
    }

    public void setDEF(boolean DEF) {
        this.DEF = DEF;
    }
    private List<NFLPlayer> getFiltered(){
        List<NFLPlayer> nflpList = new ArrayList<NFLPlayer>();
        if(QB){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 1);
            nflpList.add(nflp);
        }
        if(RB){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 2);
            nflpList.add(nflp);
        }
        if(WR){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 3);
            nflpList.add(nflp);
        }
        if(TE){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 4);
            nflpList.add(nflp);
        }
        if(K){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 5);
            nflpList.add(nflp);
        }
        if(DEF){
            NFLPlayer nflp = nflpBean.getPlayerByNameAndPos(name, 6);
            nflpList.add(nflp);
        } 
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
    public String DraftPlayer(FantasyTeam ft){
        String result;
        
        FantasyLeague leag = sessionBean.getLeague();
        
        RosterPlayer rp = new RosterPlayer();
        rp.setTeam(ft);
        rp.setLeague(leag);
        rp.setRosterSlot(0); //start benched
        
        
        //nfl stuff
        List<NFLPlayer> nflpList = getFreeAgentsLike();       
        rp.setNflPlayer(nflpList.get(0));        
        rpBean.create(rp);
        
        result = "player_drafted";
        
        return result;
    }
    
}
