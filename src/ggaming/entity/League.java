/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author dell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class League {
  //  private int id;
    private String name;
    private String imageUrl;
   // private String slug;
   // private String url;
   // private List<Series> series;
   

    public League() {
       
    }
  @JsonCreator
    public League(/*@JsonProperty("id") int id,*/
                  @JsonProperty("name") String name,
                  @JsonProperty("imageUrl") String imageUrl
                 /* @JsonProperty("slug") String slug,
                  @JsonProperty("url") String url,
                  @JsonProperty("series") List<Series> series*/) {
       // this.id = id
        this.name = name;
       // this.imageUrl = imageUrl;
       // this.slug = slug;
       // this.url = url;
       // this.series = series;
    }
   

    public String getName() {
        return name;
    }

 
  

    

    public void setName(String name) {
        this.name = name;
    }

   
   

  public String getImageUrl() {
        return imageUrl;
    }
public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
    public String toString() {
    return "League{" +
          //  "id=" + id +
            ", name='" + name + '\'' +
           ", imageUrl='" + imageUrl + '\'' +
           // ", slug='" + slug + '\'' +
         //   ", url='" + url + '\'' +
           // ", series=" + series +
            '}';
}

}
