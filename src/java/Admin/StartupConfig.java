/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.NFLPlayer;
import FMFantasyEJB.NFLPlayerBean;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Greasy
 */
@Singleton
@Startup
public class StartupConfig {
    @EJB
    NFLPlayerBean nflpb;
    
    @PostConstruct
    public void startupStuff(){
        
        
        NFLData nfld = new NFLData();
        List<NFLPlayer> allPlayers = nfld.getAllPlayers();
        
        for(int i = 0; i < allPlayers.size(); i++){
            NFLPlayer nflp = allPlayers.get(i);            
            nflpb.create(nflp);
        }
    }
}
