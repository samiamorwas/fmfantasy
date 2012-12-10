/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.NFLMatch;
import FMFantasyEJB.NFLMatchBean;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */
@Named
@Stateful
@SessionScoped
public class NflSchedReqBean {

    @EJB
    NFLMatchBean nflMatchBean;
    
    private int nflWeek;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public NflSchedReqBean() {
        nflWeek = 1;
    }

    public int getNflWeek() {
        return nflWeek;
    }

    public void setNflWeek(int nflWeek) {
        this.nflWeek = nflWeek;
    }

    public List<NFLMatch> getNflMatchesForWeek() {
        
        List<NFLMatch> nflMatchesForWeek = nflMatchBean.getMatchesByWeek(nflWeek);
        
        for (int i = 0; i < nflMatchesForWeek.size(); i++)
        {
            if (i < 6)
            {
                nflMatchesForWeek.get(i).setDayOfWeek("Sun");
            }
            else if (i < 11)
            {
                nflMatchesForWeek.get(i).setDayOfWeek("Mon");
            }
            else
            {
                nflMatchesForWeek.get(i).setDayOfWeek("Thu"); 
            }
        }
    
        return nflMatchesForWeek;
    }
    
    public void decrementNFLWeek()
    {
        // Can't go to previous week if it's Week 1
        if (nflWeek != 1)
        {
            nflWeek--;
        }
    }
    
    public void incrementNFLWeek()
    {
        // Can't go to next week if it's Week 14
        if (nflWeek != 14)
        {
            nflWeek++;
        }
    }
}