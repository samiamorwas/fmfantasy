/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import Entity.FantasyUser;
import java.io.Serializable;
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
public class UserReqBean implements Serializable{
    @EJB
    private FMFantasyEJB.FantasyUserBean fUserBean;

    
    //currently logged in user.
    private FantasyUser user;
    
    //info from reg/login forms
    private String email;
    private String password;
    private String error;
    
    public UserReqBean(){
        user = null;
        email = "";
        password = "";
        error = "";
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
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }

    public FantasyUser getUser() {
        return user;
    }

    public void setUser(FantasyUser user) {
        this.user = user;
    }
        
    public boolean isLogged(){
        return user != null;
    }
    
    public String register(){
        String result;
        if(email == null || email.equals("") || password == null || password.equals("")) {
            result = "no_such_username";
            error = "Username and password cannot be blank.";
            return result;
        }
        FantasyUser lookup = fUserBean.getUserByEmail(email);
        if(lookup != null){
            result = "user_already_exists";
            error = "User already exists.";
            return result;
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
        if(email == null || email.equals("") || password == null || password.equals("")) {
            result = "no_such_username";
            error = "Username and password cannot be blank.";
            return result;
        }
        FantasyUser lookup = fUserBean.getUserByEmail(email);
        if( lookup != null){
            if(lookup.getPassword().equals(password)){
                user = lookup;
                result = "login_success";
            } else{
                result = "wrong_password";
                error = "Password is incorrect.";
            }
        }else{
            result = "no_such_username";
            error = "User does not exist.";
        }
        
        clearInputs();
        error = "";
        return result;
    }
    
    public String logout(){
        String result;
        
        user = null;
        clearInputs();
        //do more stuff?
        result = "logout_success";
        
        return result;
    }
    
    private void clearInputs(){
        email = "";
        password = "";
    }
    
}
