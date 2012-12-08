/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.NFLPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.json.JSONObject;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;


/**
 *
 * @author Greasy
 */
public class NFLData {
    static final String urlPrefix = "http://test.nfldata.com/api.svc/JSON/";
    static final String apiKey = "?key=c7fb0f65-ccb4-4965-bbb9-4cecd0890e10";
    
    private JSONObject getJSONObjFromURL(String urlString) throws JSONException{
        try {
            URL url = new URL(urlString);
            java.util.Scanner s = new java.util.Scanner(url.openConnection().getInputStream()).useDelimiter("\\A");
            String str = s.next();
            return new JSONObject(str);
        } catch (IOException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    private JSONArray getJSONArrFromURL(String urlString) throws JSONException{
        try {
            URL url = new URL(urlString);
            java.util.Scanner s = new java.util.Scanner(url.openConnection().getInputStream()).useDelimiter("\\A");
            String str = s.next();
            return new JSONArray(str);
        } catch (IOException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public int getSeasonPointsForPlayer(NFLPlayer nflp) throws JSONException{
        int points;
        
        long nfldataID = nflp.getNFLPlayerID();
        //if not defense
        if(nflp.getPosition() != 6){
            JSONObject jObj = getJSONObjFromURL(urlPrefix + "PlayerSeasonStatsByPlayerID/2011/" + nfldataID + apiKey);
            points = jObj.getInt("FantasyPoints");
        }else{
            JSONArray jArr = getJSONArrFromURL(urlPrefix + "FantasyDefenseBySeason/2011" + apiKey);
            String teamAbrv = nflp.getTeam();
            points = -1;
            for(int i = 0; i < jArr.length(); i++){
                JSONObject jObj = jArr.getJSONObject(i);
                if(jObj.getString("Team").equals(teamAbrv)){
                    points = jObj.getInt("FantasyPoints");
                    break;
                }
            }
        }
        return points;
    }        
    
    public int getWeekPointsForPlayer(NFLPlayer nflp, int week){
        int points;
        
        long nfldataID = nflp.getNFLPlayerID();
        //if not defense
        try {
            if(nflp.getPosition() != 6){
                JSONObject jObj = getJSONObjFromURL(urlPrefix + "PlayerGameStatsByPlayerID/2011/" + week + "/" + nfldataID + apiKey);
                points = jObj.getInt("FantasyPoints");
            }else{
                JSONArray jArr = getJSONArrFromURL(urlPrefix + "FantasyDefenseByGame/2011/" + week + apiKey);
                String teamAbrv = nflp.getTeam();
                points = -1;
                for(int i = 0; i < jArr.length(); i++){
                    JSONObject jObj = jArr.getJSONObject(i);
                    if(jObj.getString("Team").equals(teamAbrv)){
                        points = jObj.getInt("FantasyPoints");
                        break;
                    }
                }
            }
        }
        catch(JSONException ex){
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
            points = 0;
        }
        return points;        
    }
    
    public List<NFLPlayer> getAllPlayers(){
        List<NFLPlayer> result = new ArrayList<NFLPlayer>();
        try {
            JSONArray teamArr = getJSONArrFromURL(urlPrefix + "Teams/2011" + apiKey);
            for(int i = 0 ; i < teamArr.length(); i++){
                JSONObject teamObj = teamArr.getJSONObject(i);
                String teamFullName = teamObj.getString("FullName");
                String teamAbrv = teamObj.getString("Key"); 
                
                JSONArray pArr = getJSONArrFromURL(urlPrefix + "Players/" + teamAbrv + apiKey);
                for(int j = 0; j < pArr.length(); j++){
                    JSONObject pObj = pArr.getJSONObject(j);
                    if(! pObj.getString("PositionCategory").equals("DEF") ){
                        String playerName = pObj.getString("Name");
                        String position = pObj.getString("Position");
                        int NFLDataID = pObj.getInt("PlayerID");
                        
                        NFLPlayer nflp = new NFLPlayer();
                        nflp.setTeam(teamAbrv);
                        nflp.setName(playerName);
                        nflp.setNFLDataID(NFLDataID);
                        if(position.equals("QB")){
                            nflp.setPosition(1);
                        }if(position.equals("RB")){
                            nflp.setPosition(2);
                        }if(position.equals("WR")){
                            nflp.setPosition(3);
                        }if(position.equals("TE")){
                            nflp.setPosition(4);
                        }if(position.equals("K")){
                            nflp.setPosition(5);
                        }
                        result.add(nflp);
                    }
                }
                
                NFLPlayer def = new NFLPlayer();
                def.setName(teamFullName);
                def.setTeam(teamAbrv);
                def.setPosition(6);
                def.setNFLDataID(-1);
                result.add(def);
            }
            
            
        } catch (JSONException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
