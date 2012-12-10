/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FMFantasyEJB;

import Entity.FantasyLeague;
import Entity.FantasyMatch;
import Entity.FantasyTeam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import Admin.StartupConfig;
/**
 *
 * @author Greasy
 */
@Stateless
public class FantasyMatchBean extends AbstractFacade<FantasyMatch> {
    @EJB
    private FantasyTeamBean teamBean;
    @PersistenceContext(unitName = "WebApplication1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FantasyMatchBean() {
        super(FantasyMatch.class);
    }
    
    public void createSchedule(FantasyLeague league){
        List<FantasyTeam> teams = teamBean.findByLeague(league);
        List<FantasyTeam> sideA, sideB;
        sideA = new ArrayList(teams.subList(0, teams.size()/2));
        sideB = new ArrayList(teams.subList(teams.size()/2, teams.size()));
        Collections.reverse(sideB);
        
        for(int i = 0; i < 16; i++)
        {
            /*create a match*/
            if(i==14 && sideA.size()>1){
                week14PlayoffSchedule(league); 
                continue;
            }
            if(i==15 && sideA.size()>1){
                week15PlayoffSchedule(league); 
                continue;
            }
            for (int j = 0; j < sideA.size(); j++)
            {   
                FantasyMatch matchToAdd = new FantasyMatch();
                matchToAdd.setWeek(i + 1);
                matchToAdd.setLeague(league);
                matchToAdd.setTeam1(sideA.get(j));
                matchToAdd.setTeam2(sideB.get(j));
                create(matchToAdd);
            }
            if(sideA.size() > 1){
                sideA.add(1, sideB.remove(0));
                sideB.add(sideA.remove(sideA.size() - 1));
            }
                    
        }
        
    }
    
    public void week14PlayoffSchedule(FantasyLeague league){
       List<FantasyTeam> teams = teamBean.findByLeague(league);
        List<FantasyTeam> sideA, sideB;
        sideA = new ArrayList(teams.subList(0, 1));
        sideB = new ArrayList(teams.subList(2,3));
        sideB.add(0, sideB.get(1));
        sideB.remove(sideB.size()-1);
        
        for (int j = 0; j < sideA.size(); j++)
            {   
                FantasyMatch matchToAdd = new FantasyMatch();
                matchToAdd.setWeek(15);
                matchToAdd.setLeague(league);
                matchToAdd.setTeam1(sideA.get(j));
                matchToAdd.setTeam2(sideB.get(j));
                create(matchToAdd);
            }
        
    }
    
    public void week15PlayoffSchedule(FantasyLeague league){
       List<FantasyTeam> teams = teamBean.findByLeague(league);
        List<FantasyTeam> sideA, sideB;
        sideA = new ArrayList();
        sideB = new ArrayList();
        sideA.add(0, teams.get(0));
        sideB.add(0, teams.get(1));
        sideA.add(1, teams.get(2));
        sideB.add(1, teams.get(3));
        
        
        for (int j = 0; j < sideA.size(); j++)
            {   
                FantasyMatch matchToAdd = new FantasyMatch();
                matchToAdd.setWeek(16);
                matchToAdd.setLeague(league);
                matchToAdd.setTeam1(sideA.get(j));
                matchToAdd.setTeam2(sideB.get(j));
                create(matchToAdd);
            }
        
    }
    
    public List<FantasyMatch> findByLeagueAndWeek(FantasyLeague league, int week){
        Query createNamedQuery = em.createNamedQuery("FantasyMatch.findByLeagueAndWeek");
        createNamedQuery.setParameter("league", league);
        createNamedQuery.setParameter("week", week);
        
        return createNamedQuery.getResultList();    
    }
    public List<FantasyMatch> findByWeek(int week){
        Query createNamedQuery = em.createNamedQuery("FantasyMatch.findByWeek");
        createNamedQuery.setParameter("week", week);
        
        return createNamedQuery.getResultList();    
    }
    
    public List<FantasyMatch> findByCurrentTeam(int week, FantasyTeam team) {
        Query createNamedQuery = em.createNamedQuery("FantasyMatch.findCurrentForTeam");
        createNamedQuery.setParameter("week", week);
        createNamedQuery.setParameter("team", team);
        
        return createNamedQuery.getResultList();
    }
}
