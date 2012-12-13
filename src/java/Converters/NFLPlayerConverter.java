/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Converters;

import Entity.NFLPlayer;
import FMFantasyEJB.NFLPlayerBean;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Greasy
 */

@Named
@ApplicationScoped
public class NFLPlayerConverter implements Converter {
    
    @EJB
    NFLPlayerBean nflpb;

    /**
     * Creates a new instance of NFLPlayerConverter
     */
    public NFLPlayerConverter() {
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.equals("")){
            return null;
        }
        else{
            long id = Long.parseLong(value);
            NFLPlayer nflp = nflpb.find(id);
            return nflp;
        }            
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null)
            return "";
        else
            return ((NFLPlayer) value).getNFLPlayerID().toString();
    }
}
