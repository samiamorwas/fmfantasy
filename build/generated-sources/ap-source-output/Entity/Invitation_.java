package Entity;

import Entity.FantasyLeague;
import Entity.FantasyUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-11-27T21:37:32")
@StaticMetamodel(Invitation.class)
public class Invitation_ { 

    public static volatile SingularAttribute<Invitation, Long> invitationID;
    public static volatile SingularAttribute<Invitation, FantasyUser> sender;
    public static volatile SingularAttribute<Invitation, FantasyUser> receiver;
    public static volatile SingularAttribute<Invitation, FantasyLeague> league;

}