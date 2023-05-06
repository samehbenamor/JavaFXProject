/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ggaming.entity;

/**
 *
 * @author dell
 */
public class CategorieJeux {
    private int id;
    private String NomCat;
   
    public CategorieJeux(int id,String NomCat) {
        this.NomCat = NomCat; 
        this.id = id; 
    }
public  CategorieJeux(String NomCat) {
        this.NomCat = NomCat; 
    }

    public CategorieJeux() {
        
    }

public String getNomCat() {
        return NomCat;
    }

    public void setNomCat(String NomCat) {
        this.NomCat = NomCat;
    }
public int getId() {
        return id;
    }
public void setId(int id) {
        this.id = id;
    }
@Override
public String toString() {
	// TODO Auto-generated method stub
	return super.toString();
}
}
