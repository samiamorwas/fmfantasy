/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyUser;
import java.io.Serializable;
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
public class UserReqBean implements Serializable{
    @EJB
    private FMFantasyEJB.FantasyUserBean fUserBean;
    
    @Inject
    SessionBean sessionBean;
    
    //info from reg/login forms
    private String email;
    private String password;
    
    public UserReqBean(){
        
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }        
        
    public boolean isLogged(){
        return sessionBean.getUser() != null;
    }
    
    public String register(){
        String result;
        
        FantasyUser lookup = fUserBean.getUserByEmail(email);
        if(lookup != null){
            result = "user_already_exists";
        } else{
            FantasyUser newUser = new FantasyUser();
            newUser.setEmail(email);
            newUser.setPassword(password);
            fUserBean.createUser(newUser);
            
            result = "register_success";
        }
        
        login();
        
        return result;
    }
    
    public String login(){
        String result;
        
        FantasyUser lookup = fUserBean.getUserByEmail(email);
        if( lookup != null){
            if(lookup.getPassword().equals(password)){
                sessionBean.setUser(lookup);
                result = "login_success";
            } else{
                result = "wrong_password";
            }
        }else{
            result = "no_such_username";
        }
        
        return result;
    }
    
    public String logout(){
        String result;
        
        sessionBean.setUser(null);
        //do more stuff?
        result = "logout_success";
        
        return result;
    }
    
}
