/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entity.NFLPlayer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Greasy
 */
public class NFLData {
    
    
    public List<NFLPlayer> getAllPlayers(){
        List<NFLPlayer> result = new ArrayList<NFLPlayer>();
        
        Document teamsDoc = httpGet("http://test.nfldata.com/api.svc/xml/Teams/2011?key=c7fb0f65-ccb4-4965-bbb9-4cecd0890e10");
        Element teamsRoot = teamsDoc.getDocumentElement();
        NodeList teams = teamsRoot.getElementsByTagName("Team");
        for(int i = 0; i < teams.getLength(); i++){
            Element team = (Element) teams.item(i);
            Element teamKey = (Element) team.getElementsByTagName("Key").item(0);
            Element teamFullName = (Element) team.getElementsByTagName("FullName").item(0);
            
            String key = teamKey.getFirstChild().getNodeValue(); //atl, nyj, etc
            String fullName = teamFullName.getFirstChild().getNodeValue();
            
            
            //get all players
            String playersReq = "http://test.nfldata.com/api.svc/xml/Players/" + key + "?key=c7fb0f65-ccb4-4965-bbb9-4cecd0890e10";
            Document playersDoc = httpGet(playersReq);
            Element playersRoot = playersDoc.getDocumentElement();
            NodeList players = playersRoot.getElementsByTagName("Player");
            for(int j = 0; j < players.getLength(); j++){
                Element player = (Element) players.item(j);
                Element e_name = (Element) player.getElementsByTagName("Name").item(0);
                Element e_nflDataID = (Element) player.getElementsByTagName("PlayerID").item(0);
                Element e_position = (Element) player.getElementsByTagName("Position").item(0);
                Element e_posCat = (Element) player.getElementsByTagName("PositionCategory").item(0);
                
                String s_Name = e_name.getFirstChild().getNodeValue();
                String s_nflDataID = e_nflDataID.getFirstChild().getNodeValue();
                String s_position = e_position.getFirstChild().getNodeValue();
                String s_posCat = e_posCat.getFirstChild().getNodeValue();
                
                if(s_posCat.equalsIgnoreCase("DEF")) {
                    continue;
                }
                
                long l_nflDataID = Long.parseLong(s_nflDataID);
                
                int i_position = 0;
                if(s_position.equals("QB")){
                    i_position = 1;
                }
                if(s_position.equals("RB")){
                    i_position = 2;
                }
                if(s_position.equals("WR")){
                    i_position = 3;
                }
                if(s_position.equals("TE")){
                    i_position = 4;
                }
                if(s_position.equals("K")){
                    i_position = 5;
                }
                
                NFLPlayer nflp = new NFLPlayer();
                nflp.setName(s_Name);
                nflp.setNFLDataID(l_nflDataID);
                nflp.setPosition(i_position);
                nflp.setTeam(fullName);
                
                result.add(nflp);
            }
            
            //make defense
            NFLPlayer def = new NFLPlayer();
            def.setName(fullName);
            def.setNFLDataID(0);
            def.setPosition(6);
            def.setTeam(fullName);
            
            result.add(def);
        }
        
        
        
        
        return result;
    }
    
    public Document httpGet(String urlStr) {
        Document dom = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != 200) {
              throw new IOException(conn.getResponseMessage());
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(conn.getInputStream());

            
        } catch (IOException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(NFLData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dom;
    }
}
