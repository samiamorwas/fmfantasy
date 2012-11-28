package Entity;

import Entity.FantasyLeague;
import Entity.FantasyUser;
import Entity.RosterPlayer;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-11-28T01:02:00")
@StaticMetamodel(FantasyTeam.class)
public class FantasyTeam_ { 

    public static volatile SingularAttribute<FantasyTeam, String> teamName;
    public static volatile SingularAttribute<FantasyTeam, Integer> rank;
    public static volatile SingularAttribute<FantasyTeam, Boolean> inPlayoffs;
    public static volatile ListAttribute<FantasyTeam, RosterPlayer> rosterPlayers;
    public static volatile SingularAttribute<FantasyTeam, Integer> previousRank;
    public static volatile SingularAttribute<FantasyTeam, Integer> pointsAgainst;
    public static volatile SingularAttribute<FantasyTeam, Integer> losses;
    public static volatile SingularAttribute<FantasyTeam, Integer> pointsFor;
    public static volatile SingularAttribute<FantasyTeam, Integer> draws;
    public static volatile SingularAttribute<FantasyTeam, Long> teamID;
    public static volatile SingularAttribute<FantasyTeam, FantasyLeague> league;
    public static volatile SingularAttribute<FantasyTeam, FantasyUser> teamOwner;
    public static volatile SingularAttribute<FantasyTeam, Integer> wins;

}