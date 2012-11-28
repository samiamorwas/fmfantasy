package Entity;

import Entity.FantasyTeam;
import Entity.FantasyUser;
import Entity.RosterPlayer;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-11-27T21:37:32")
@StaticMetamodel(FantasyLeague.class)
public class FantasyLeague_ { 

    public static volatile ListAttribute<FantasyLeague, FantasyTeam> memberTeams;
    public static volatile SingularAttribute<FantasyLeague, Long> leagueID;
    public static volatile SingularAttribute<FantasyLeague, String> leagueName;
    public static volatile ListAttribute<FantasyLeague, RosterPlayer> rosterPlayers;
    public static volatile SingularAttribute<FantasyLeague, Boolean> finishedDraft;
    public static volatile SingularAttribute<FantasyLeague, FantasyUser> leagueOwner;

}