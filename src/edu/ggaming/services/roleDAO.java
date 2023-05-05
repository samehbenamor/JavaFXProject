/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ggaming.services;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import edu.ggaming.utils.MyConnection;
import edu.ggaming.entities.RoleJava;
/**
 *
 * @author DELL
 */
public class roleDAO {
    private final Connection cnx;

    public roleDAO() {
        this.cnx = MyConnection.getInstance().getCnx();
    }
    public RoleJava create(RoleJava role) throws SQLException {
        String sql = "INSERT INTO roleJava (name) VALUES (?)";
        PreparedStatement statement = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, role.getRoleName());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating role failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                role.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating role failed, no ID obtained.");
            }
        }
        return role;
    }

    public RoleJava read(int id) throws SQLException {
        String sql = "SELECT name FROM roleJava WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            return new RoleJava(id, name);
        } else {
            return null;
        }
    }
    public String getRoleNameByJoueurId(int joueurId) {
    String roleName = null;
    String query = "SELECT r.name FROM joueur j JOIN roleJava r ON j.roleJava_joueur_id = r.id WHERE j.id = ?";
    try (PreparedStatement stmt = cnx.prepareStatement(query)) {
        stmt.setInt(1, joueurId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            roleName = rs.getString("name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return roleName;
}

    public List<RoleJava> readAll() throws SQLException {
        List<RoleJava> roles = new ArrayList<>();
        String sql = "SELECT id, name FROM roleJava";
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            RoleJava role = new RoleJava(id, name);
            roles.add(role);
        }
        return roles;
    }

    public void update(RoleJava role) throws SQLException {
        String sql = "UPDATE roleJava SET name = ? WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, role.getRoleName());
        statement.setInt(2, role.getId());
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating role failed, no rows affected.");
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM roleJava WHERE id = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, id);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting role failed, no rows affected.");
        }
    }
}
