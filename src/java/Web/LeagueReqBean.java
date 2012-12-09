/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyLeague;
import Entity.FantasyMatch;
import Entity.FantasyTeam;
import Entity.FantasyUser;
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
public class LeagueReqBean {
    @EJB
    private FMFantasyEJB.FantasyLeagueBean leagueBean;
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;
    @EJB
    private FMFantasyEJB.FantasyUserBean userBean;
    @EJB
    private FMFantasyEJB.InvitationBean invBean;
    @EJB
    private FMFantasyEJB.FantasyMatchBean matchBean;
    
    @Inject
    SessionBean sessionBean;
        
    private String leagueName;
    private String teamName;
    private String error;
    
    /**
     * Creates a new instance of LeagueReqBean
     */
    public LeagueReqBean() {
    }

    public String getLeagueName() {
        return leagueName;
    }
    
    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public List<FantasyLeague> getOwned(){
        FantasyUser luser = sessionBean.getUser();
        List<FantasyLeague> leagues = leagueBean.findByOwner(luser);
        
        return leagues;
    }
    public List<FantasyLeague> getMemberOf(){
        FantasyUser luser = sessionBean.getUser();
        List<FantasyLeague> leagues = leagueBean.findByMember(luser);
        
        return leagues;
    }
    
    public String viewLeague(FantasyLeague league){
        sessionBean.setLeague(league);
        return "view_league";
    }
    
    public String createLeague(){
        if(leagueName == null || leagueName.equals("") || teamName == null || teamName.equals("")) {
            error = "League and team name cannot be blank";
            return "create_league_error";
        }
        
        FantasyUser luser = sessionBean.getUser();
        
        FantasyLeague l = new FantasyLeague();
        l.setLeagueName(leagueName);
        l.setLeagueOwner(luser);
        l.setFinishedDraft(false);
        l.setDraftStarted(false);
        leagueBean.createLeague(l);
        
        FantasyTeam t = new FantasyTeam();        
        t.setTeamName(teamName);
        t.setLeague(l);
        t.setTeamOwner(luser);
        teamBean.createTeam(t);
        
        return "create_league_success";
    }
    
    public String finishDraft(){
        FantasyLeague league = sessionBean.getLeague();
        
        matchBean.createSchedule(league);        
        league.setFinishedDraft(true);
        leagueBean.edit(league);
        
        return "draft_complete";
    }
    
    public List<FantasyMatch> getMatchesInWeek(){
        List<FantasyMatch> results = matchBean.findByWeek(sessionBean.getLeagueWeek());
        return results;
    }
    public void decrementWeek()
    {
        int week = sessionBean.getLeagueWeek();
        // Can't go to previous week if it's Week 1
        if (week != 1)
        {
            sessionBean.setLeagueWeek(week - 1);
        }
    }
    
    public void incrementWeek()
    {
        int week = sessionBean.getLeagueWeek();
        // Can't go to next week if it's Week 14
        if (week != 14)
        {
            sessionBean.setLeagueWeek(week + 1);
        }
    }
 /* 
    public String createSeasonSchedule()
    {
        ArrayList<FantasyTeam> teamsInLeague = (ArrayList)teamBean.findByLeague(sessionBean.getLeague());
        List<FantasyTeam> firstHalfTeams;
        firstHalfTeams = new ArrayList();
        List<FantasyTeam> secondHalfTeams;
        secondHalfTeams = new ArrayList();
        int numTeams = teamsInLeague.size();
        int[] indexesSelected = new int[numTeams];
        int indexesSelectedIndex = 0;
        int indexToAssign;
        Boolean indexAssigned;
        FantasyMatch matchToAdd = new FantasyMatch();
        FantasyTeam tempRemoval;
        matchesInWeek = new ArrayList();
        
        // Randomly select index in teamsInLeague list of all teams in league and, after making sure 
        // that that index hasn't already been selected (by consulting indexesSelected array), 
        // assign team at that index to next available index in firstHalfTeams list
        for (int i = 0; i < numTeams/2; i++)
        {
            do
            {
                indexAssigned = false;
                indexToAssign = (int)(Math.random() * (numTeams - 1));
                for (int j = 0; j < j + i; j++)
                {
                    if (indexesSelected[j] == indexToAssign)
                    {
                        indexAssigned = true;
                        j = numTeams;
                    }
                }
            } while(indexAssigned);
            
            indexesSelected[indexesSelectedIndex] = indexToAssign;
            indexesSelectedIndex++;
            firstHalfTeams.add(i, teamsInLeague.get(indexToAssign));
        }
       
        // Randomly select index in teamsInLeague list of all teams in league and, after making sure 
        // that that index hasn't already been selected (by consulting indexesSelected array), 
        // assign team at that index to next available index in secondtHalfTeams list
        for (int i = 0; i < numTeams/2; i++)
        {
            do
            {
                indexAssigned = false;
                indexToAssign = (int)(Math.random() * (numTeams - 1));
                for (int j = 0; j < (j + (i + numTeams/2)); j++)
                {
                    if (indexesSelected[j] == indexToAssign)
                    {
                        indexAssigned = true;
                        j = numTeams;
                    }
                }
            } while(indexAssigned);
            
            indexesSelected[indexesSelectedIndex] = indexToAssign;
            indexesSelectedIndex++;
            
            secondHalfTeams.add(i, teamsInLeague.get(indexToAssign));
        }
       
        for (int i = 0; i < 14; i++)
        {
            for (int j = 0; j < numTeams/2; j++)
            {
                sessionBean.setLeagueWeek(i + 1);
                matchToAdd.setWeek(i + 1);
                matchToAdd.setTeam1(firstHalfTeams.get(j));
                matchToAdd.setTeam2(secondHalfTeams.get(j));
                matchesInSeason.add(matchToAdd);
            }
            
            // Round Robin-style rotation - Visualize lining two team lists up alongside each 
            // other vertically, where indexes increase as you go down the vertically-aligned lists. 
            // Keep team at index 0 of first list fixed throughout all 14 weeks. Move each team
            // other than team at index 0 of first list in clockwise direction, where team at index
            // 1 of first list moves to index 0 in second list and team at index numTeams/2 – 1 of 
            // second list moves to index numTeams/2 – 1 of first list. Temporarily remove team at
            // index 1 of first list to placeholder variable so that rotation can start by
            // overwriting team at index of 1 of first list without losing that team
            
            tempRemoval = firstHalfTeams.get(1);
            
            for (int k = 2; k < numTeams/2; k++)
            {
                firstHalfTeams.set((k - 1), firstHalfTeams.get(k));
            }
            
            firstHalfTeams.set((numTeams/2 - 1), (secondHalfTeams.get(numTeams/2 - 1))); 
            
            for (int k = 0; k < (numTeams/2 - 2); k++)
            {
                secondHalfTeams.set((k + 1), secondHalfTeams.get(k));
            }
            
            secondHalfTeams.set(0, tempRemoval);
        }
       
        // matchesInWeek initially set to first week of season
        matchesInWeek = matchBean.findByWeek(1);
        
        return "schedule_created";
    }
 */
}