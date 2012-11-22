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
            return "non_exist_user";
        }
        
        Invitation inv = new Invitation();
        inv.setSender(luser);
        inv.setLeague(lleague);
        inv.setReceiver(recip);
        invBean.createInvitation(inv);
        
        return "invite_sent";
    }
    
    public String acceptInvite(Invitation inv){
        FantasyTeam team = new FantasyTeam();
        team.setTeamName(teamName);
        team.setTeamOwner(inv.getReceiver());
        team.setLeague(inv.getLeague());
        teamBean.createTeam(team);
        
        invBean.remove(inv);
        
        return "invite_accepted";
    }
    
}
