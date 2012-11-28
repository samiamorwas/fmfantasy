package Entity;

import Entity.FantasyLeague;
import Entity.FantasyTeam;
import Entity.NFLPlayer;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-11-28T01:02:00")
@StaticMetamodel(RosterPlayer.class)
public class RosterPlayer_ { 

    public static volatile SingularAttribute<RosterPlayer, FantasyTeam> team;
    public static volatile SingularAttribute<RosterPlayer, Long> rpID;
    public static volatile SingularAttribute<RosterPlayer, FantasyLeague> league;
    public static volatile SingularAttribute<RosterPlayer, NFLPlayer> nflPlayer;
    public static volatile SingularAttribute<RosterPlayer, Integer> rosterSlot;

}