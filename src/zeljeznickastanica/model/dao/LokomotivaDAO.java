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
import zeljeznickastanica.model.dto.Lokomotiva;

/**
 *
 * @author djord
 */
public class LokomotivaDAO {

    public static void ubaciUTabeluVozova() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Lokomotiva lokomotiva;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from lokomotiva_info");
            while (rs.next()) {
                lokomotiva = new Lokomotiva(rs.getString("vozid"), rs.getString("vrstapogona"),
                        rs.getString("namjena"), rs.getDouble("sirinakolosjeka"), rs.getString("naziv"));
                if (!ZeljeznickaStanicaController.vozoviObservaleList.contains(lokomotiva)) {
                    ZeljeznickaStanicaController.vozoviObservaleList.add(lokomotiva);
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

    public static void dodajLokomotivu(Lokomotiva lokomotiva, String tipVoza) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_lokomotiva(?,?,?,?,?,?)}");
            callableStatement.setString(1, lokomotiva.getVozId());
            callableStatement.setString(2, lokomotiva.getVrstaPogona());
            callableStatement.setString(3, lokomotiva.getNamjena());
            callableStatement.setDouble(4, lokomotiva.getSirinaKolosjeka());
            callableStatement.setString(5, "Lokomotiva");
            callableStatement.setString(6, lokomotiva.getNaziv());

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

    public static void izmjeniLokomotivu(Lokomotiva lokomotiva) {
        
        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_lokomotiva(?,?,?,?,?,?)}");
            callableStatement.setString(1, lokomotiva.getVozId());
            callableStatement.setString(2, lokomotiva.getVrstaPogona());
            callableStatement.setString(3, lokomotiva.getNamjena());
            callableStatement.setDouble(4, lokomotiva.getSirinaKolosjeka());
            callableStatement.setString(5, lokomotiva.getTipVoza());
            callableStatement.setString(6, lokomotiva.getNaziv());

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

    public static Lokomotiva pretraziVoz(String naziv) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_lokomotivu(?)}");
            callableStatement.setString(1, naziv);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new Lokomotiva(rs.getString("vozid"), rs.getString("vrstapogona"), rs.getString("namjena"),
                        rs.getDouble("sirinakolosjeka"), rs.getString("naziv"));
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
