/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyTeam;
import Entity.RosterPlayer;
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
public class RosterReqBean {
    @EJB
    private FMFantasyEJB.RosterPlayerBean rosterBean;
    
    @Inject 
    TeamReqBean tReq;

    /**
     * Creates a new instance of FantasyLeagueController
     */
    public RosterReqBean() {
    }

    public List<RosterPlayer> getTeam() {
        FantasyTeam ft = tReq.getTeam();
        return rosterBean.getByTeam(ft);
    }
    public List<RosterPlayer> getActive() {
        FantasyTeam ft = tReq.getTeam();
        return rosterBean.getActiveByTeam(ft);
    }
    public List<RosterPlayer> getBench() {
        FantasyTeam ft = tReq.getTeam();
        return rosterBean.getBenchByTeam(ft);
    }
    
    public void drop(RosterPlayer rp){
        rosterBean.remove(rp);        
    }
    public void demote(RosterPlayer rp){
        rp.setRosterSlot(0);
        rosterBean.edit(rp);
    }
    public void promote(RosterPlayer rp){
        //need some logic here
        if(isPromotable(rp))
        rp.setRosterSlot(rp.getNflPlayer().getPosition());
        rosterBean.edit(rp);
    }
    public boolean isPromotable(RosterPlayer rp){
        int pos = rp.getNflPlayer().getPosition();
        List<RosterPlayer> activeList = getActive();
        
        int numOfThisPos = 0;
        int numWR = 0;
        int numRB = 0;
        for(int i = 0; i < activeList.size(); i++){
            int rpPos = activeList.get(i).getNflPlayer().getPosition();
            
            if(rpPos == pos)
                numOfThisPos++;            
            if(rpPos == 2)
                numRB++;
            if(rpPos == 3)
                numWR++;
            
        }
        
        switch(pos){
            case 1:
            case 4:
            case 5:
            case 6:                
                if( numOfThisPos == 0)
                    return true;
                else
                    return false;
            case 2:
            case 3:
                if(numOfThisPos < 2)
                    return true;
                if(numOfThisPos == 3)
                    return false;
                if(numWR == 3 || numRB == 3)
                    return false;
                else
                    return true;                
            default:
                return false;
        }
    }
}