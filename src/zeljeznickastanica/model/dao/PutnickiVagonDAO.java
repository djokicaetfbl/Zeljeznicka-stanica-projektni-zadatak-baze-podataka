/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.controller.ZeljeznickaStanicaController;
import zeljeznickastanica.model.dto.PutnickiVagon;
import zeljeznickastanica.model.dto.TeretniVagon;

/**
 *
 * @author djord
 */
public class PutnickiVagonDAO {

    public static void ubaciUTabeluVagona() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        PutnickiVagon putnickiVagon;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from putnicki_vagon_info");
            while (rs.next()) {
                putnickiVagon = new PutnickiVagon(rs.getString("vagonid"), rs.getInt("brojmjesta"),
                        rs.getBoolean("lezajzaspavanje"), rs.getBoolean("toalet"), rs.getBoolean("bar"),
                        rs.getBoolean("klima"), rs.getBoolean("tv"), rs.getBoolean("internet"), rs.getBoolean("restoran"));
                if (!ZeljeznickaStanicaController.vagoniPutnickiObservableList.contains(putnickiVagon)) {
                    ZeljeznickaStanicaController.vagoniPutnickiObservableList.add(putnickiVagon);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LokomotivaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LokomotivaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void dodajPutnickiVagon(PutnickiVagon putnickiVagon) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_putnicki_vagon(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, putnickiVagon.getVagonId());
            callableStatement.setInt(2, putnickiVagon.getBrojMjesta());
            callableStatement.setBoolean(3, putnickiVagon.isLezajZaSpavanje());
            callableStatement.setBoolean(4, putnickiVagon.isToalet());
            callableStatement.setBoolean(5, putnickiVagon.isBar());
            callableStatement.setBoolean(6, putnickiVagon.isKlima());
            callableStatement.setBoolean(7, putnickiVagon.isTv());
            callableStatement.setBoolean(8, putnickiVagon.isInternet());
            callableStatement.setBoolean(9, putnickiVagon.isRestoran());

            callableStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void izmjeniPutnickiVagon(PutnickiVagon putnickiVagon) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_putnicki_vagon(?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, putnickiVagon.getVagonId());
            callableStatement.setInt(2, putnickiVagon.getBrojMjesta());
            callableStatement.setBoolean(3, putnickiVagon.isLezajZaSpavanje());
            callableStatement.setBoolean(4, putnickiVagon.isToalet());
            callableStatement.setBoolean(5, putnickiVagon.isBar());
            callableStatement.setBoolean(6, putnickiVagon.isKlima());
            callableStatement.setBoolean(7, putnickiVagon.isTv());
            callableStatement.setBoolean(8, putnickiVagon.isInternet());
            callableStatement.setBoolean(9, putnickiVagon.isRestoran());

            callableStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
        public static PutnickiVagon pretraziPutnickiVagon(String vagonId) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_putnicki_vagon(?)}");
            callableStatement.setString(1, vagonId);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new PutnickiVagon(rs.getString("vagonid"), rs.getInt("brojmjesta"), rs.getBoolean("lezajzaspavanje"),
                        rs.getBoolean("toalet"), rs.getBoolean("bar"),rs.getBoolean("klima"),rs.getBoolean("tv"),rs.getBoolean("internet")
                ,rs.getBoolean("restoran"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ZaposleniDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
        }
        return null;
    }
}
