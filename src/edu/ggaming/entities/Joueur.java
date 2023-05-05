package edu.ggaming.entities;

import java.util.Date;
import java.util.List;

public class Joueur {

    private int id;
    private String email;
    private String[] roles;
    private String password;
    private boolean is_verified;
    private String nom;
    private String prenom;
    private Date datenai;
    private String pprofile;
    private boolean is_banned;
    private String ign;
    private int wins;
    private int loses;
    private int roleJava_joueur_id;
    //private List<Blog> blog;
    

    public Joueur() {}

    public Joueur(String email, String[] roles, String password, boolean is_verified, String nom, String prenom, Date datenai, String pprofile, boolean is_banned, String ign, int wins, int loses) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.datenai = datenai;
        this.pprofile = pprofile;
        this.is_banned = is_banned;
        this.ign = ign;
        this.wins = wins;
        this.loses = loses;
    }
    

    public Joueur(String email, String password, String nom, String prenom, String ign) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.ign = ign;
    }

    public Joueur(int id, String email, boolean is_verified, String nom, String prenom, Date datenai, String pprofile, boolean is_banned, String ign, int wins, int loses, int roleJava_joueur_id) {
        this.id = id;
        this.email = email;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.datenai = datenai;
        this.pprofile = pprofile;
        this.is_banned = is_banned;
        this.ign = ign;
        this.wins = wins;
        this.loses = loses;
        this.roleJava_joueur_id = roleJava_joueur_id;
    }

    public Joueur(int id, String email, boolean is_verified, String nom, String prenom, Date datenai, String pprofile, boolean is_banned, String ign, int wins, int loses) {
        this.id = id;
        this.email = email;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.datenai = datenai;
        this.pprofile = pprofile;
        this.is_banned = is_banned;
        this.ign = ign;
        this.wins = wins;
        this.loses = loses;
    }

    public Joueur(String email, String password, boolean is_verified, String nom, String prenom, boolean is_banned, String ign) {
        this.email = email;
        this.password = password;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.is_banned = is_banned;
        this.ign = ign;
    }

    
    public Joueur(int id, String email, String[] roles, String password, boolean is_verified, String nom, String prenom, Date datenai, String pprofile, boolean is_banned, String ign, int wins, int loses) {
        this.id = id;
        this.email = email;
         this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.datenai = datenai;
        this.pprofile = pprofile;
        this.is_banned = is_banned;
        this.ign = ign;
        this.wins = wins;
        this.loses = loses;
        
    }
     public int getRoleJava_joueur_id() {
        return roleJava_joueur_id;
    }

    public void setRoleJava_joueur_id(int roleJava_joueur_id) {
        this.roleJava_joueur_id = roleJava_joueur_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return is_verified;
    }

    public void setVerified(boolean is_verified) {
        is_verified = is_verified;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDatenai() {
        return datenai;
    }

    public void setDatenai(Date datenai) {
        this.datenai = datenai;
    }

    public String getProfile() {
        return pprofile;
    }

    public void setProfile(String pprofile) {
        this.pprofile = pprofile;
    }

    public boolean isBanned() {
        return is_banned;
    }

    public void setBanned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public String getIgn() {
        return ign;
    }

    

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    
    
}
