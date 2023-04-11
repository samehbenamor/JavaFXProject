/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggaming.services;

import ggaming.cnx.MyConnection;
import ggaming.entity.Equipe;
import ggaming.interfaces.equipeinterfaces;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dhia
 */
public class EquipeService implements equipeinterfaces <Equipe> {
 private static EquipeService instance;
    private Statement st;
    private ResultSet rs;

    public EquipeService() {
        MyConnection cs = MyConnection.getInstance();
        try {
            st = cs.getCnx().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static EquipeService getInstance() {
        if (instance == null) {
            instance = new EquipeService();
        }
        return instance;
    }
    @Override
    public void insert(Equipe o) {
       String req = "INSERT INTO Equipe (id, nom_equipe , description_equipe, nb_joueurs, logo_equipe, site_web ,date_creation ) VALUES ('" + o.getId() + "', '" + o.getNom_equipe() + "', '" + o.getDescription_equipe() + "', '" + o.getNb_joueurs() + "', '" + o.getLogo_equipe() + "', '" + o.getSite_web() + "', '" + o.getDate_creation() + "')";

        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(EquipeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Equipe> displayAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Equipe displayById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Equipe os) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
