package Entity;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-11-28T15:10:19")
@StaticMetamodel(FantasyUser.class)
public class FantasyUser_ { 

    public static volatile SingularAttribute<FantasyUser, Long> userID;
    public static volatile SingularAttribute<FantasyUser, String> email;
    public static volatile ListAttribute<FantasyUser, FantasyTeam> ownedTeams;
    public static volatile SingularAttribute<FantasyUser, String> password;
    public static volatile ListAttribute<FantasyUser, FantasyLeague> leaguesOwned;

}