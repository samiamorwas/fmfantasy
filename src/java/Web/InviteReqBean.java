/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import Entity.FantasyUser;
import Entity.Invitation;
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
public class InviteReqBean {
    @EJB
    private FMFantasyEJB.FantasyUserBean userBean;
    @EJB
    private FMFantasyEJB.InvitationBean invBean;
    @EJB
    private FMFantasyEJB.FantasyTeamBean teamBean;
    
    @Inject
    SessionBean sessionBean;
    
        
    private String recipEmail;
    private String teamName;
    private String error;
    
    /**
     * Creates a new instance of FantasyLeagueController
     */
    public InviteReqBean() {
    }   

    public String getRecipEmail() {
        return recipEmail;
    }

    public void setRecipEmail(String recipEmail) {
        this.recipEmail = recipEmail;
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
    
    public List<Invitation> getInvitesToYou(){
        FantasyUser luser = sessionBean.getUser();
        
        List<Invitation> invs = invBean.findByReceiver(luser);
        
        return invs;
    }
    public List<Invitation> getInvitesFromYou(){
        FantasyUser luser = sessionBean.getUser();
        
        List<Invitation> invs = invBean.findBySender(luser);
        
        return invs;
    }
    public List<Invitation> getInvitesByLeague(){
        FantasyLeague lleague = sessionBean.getLeague();
        
        List<Invitation> invs = invBean.findByLeague(lleague);
        
        return invs;
    }
    
    public String sendInvite(){
        FantasyUser luser = sessionBean.getUser();
        FantasyLeague lleague = sessionBean.getLeague();
        
        FantasyUser recip = userBean.getUserByEmail(recipEmail);
        if(recip == null){
            error = "User does not exist.";
            return "non_exist_user";
        }
        
        if(recip.getEmail().equals(luser.getEmail())) {
            error = "Cannot invite yourself.";
            return "non_exist_user";
        }
        
        List<Invitation> testInvs = invBean.findByReceiver(recip);
        for(Invitation inv : testInvs) {
            if(inv.getSender().getEmail().equals(luser.getEmail())) {
                error = "Invitation already sent.";
                return "non_exist_user";
            }
        }
        
        Invitation inv = new Invitation();
        inv.setSender(luser);
        inv.setLeague(lleague);
        inv.setReceiver(recip);
        invBean.createInvitation(inv);
        error = "Invitation sent.";
        return "invite_sent";
    }
    
    public String acceptInvite(Invitation inv){
        if(teamName == null || teamName.equals("")) {
            error = "Team name cannot be blank.";
            return "invite_error";
        }
        
        List<FantasyTeam> teams = teamBean.findByLeague(inv.getLeague());
        for(FantasyTeam team : teams) {
            if(team.getTeamName().equals(teamName)) {
                error = "Team name is taken.";
                return "invite_error";
            }
        }
        
        FantasyTeam team = new FantasyTeam();
        team.setTeamName(teamName);
        team.setTeamOwner(inv.getReceiver());
        team.setLeague(inv.getLeague());
        teamBean.createTeam(team);
        
        invBean.remove(inv);
        error = "Invite accepted.";
        return "invite_accepted";
    }
    
}
