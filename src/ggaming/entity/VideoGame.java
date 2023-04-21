/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoGame {
   // private int id;
    private String name;
   // private String currentVersion;
    private List<League> leagues;

    
    public VideoGame() {
      
    }
@JsonCreator
public VideoGame(/*@JsonProperty("id") int id, */@JsonProperty("name") String name,/* @JsonProperty("currentVersion") String currentVersion,*/ @JsonProperty("leagues") List<League> leagues) {
    //this.id = id;
    this.name = name;
   // this.currentVersion = currentVersion;
    this.leagues = leagues;
}

   

    public String getName() {
        return name;
    }

   

    public List<League> getLeagues() {
        return leagues;
    }

  
    public void setName(String name) {
        this.name = name;
    }

  

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
    public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("VideoGame { ");
    //sb.append("id: ").append(id).append(", ");
    sb.append("name: ").append(name).append(", ");
   // sb.append("currentVersion: ").append(currentVersion).append(", ");
    sb.append("leagues: [\n");
    for (League league : leagues) {
        sb.append(league.toString()).append(",\n");
    }
    sb.append("]}");
    return sb.toString();
}

}