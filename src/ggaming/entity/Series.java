/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author dell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
 public class Series {
       // private int id;
       // private int league_id;
        private String name;
       // private String slug;
     //   private String season;
        private String full_name;
        private String begin_at;
        private String end_at;
        private int year;
       // private int winner_id;
        private String winner_type;

        // constructors
        public Series() {
        }

      @JsonCreator
public Series( 
              @JsonProperty("name") String name, 
              
              @JsonProperty("full_name") String full_name, 
              @JsonProperty("begin_at") String begin_at, 
              @JsonProperty("end_at") String end_at, 
              @JsonProperty("year") int year, 
               
              @JsonProperty("winner_type") String winner_type) {
 // this.id = id;
   // this.league_id = league_id;
    this.name = name;
   // this.slug = slug;
   // this.season = season;
    this.full_name = full_name;
    this.begin_at = begin_at;
    this.end_at = end_at;
    this.year = year;
   // this.winner_id = winner_id;
    this.winner_type = winner_type;
}

            // getters and setters
  
    

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    

    

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getBegin_at() {
        return begin_at;
    }

    public void setBegin_at(String begin_at) {
        this.begin_at = begin_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public void setEnd_at(String end_at) {
        this.end_at = end_at;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

   
    public String getWinner_type() {
        return winner_type;
    }

    public void setWinner_type(String winner_type) {
        this.winner_type = winner_type;
    }

    // toString method
    @Override
    public String toString() {
        return "Series{" +
               // "id=" + id +
              //  ", league_id=" + league_id +
                ", name='" + name + '\'' +
               // ", slug='" + slug + '\'' +
               // ", season='" + season + '\'' +
                ", full_name='" + full_name + '\'' +
                ", begin_at='" + begin_at + '\'' +
                ", end_at='" + end_at + '\'' +
                ", year=" + year +
               // ", winner_id=" + winner_id +
                ", winner_type='" + winner_type + '\'' +
                '}';
    }
}
