/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static zeljeznickastanica.controller.MjestaController.mjestaObservaleList;
import zeljeznickastanica.model.dto.Mjesto;
import zeljeznickastanica.model.dto.VrstaTeretnogVagona;

/**
 *
 * @author djord
 */
public class MjestoDAO {

    public static void dodajMjesto(Mjesto mjesto) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_mjesto(?,?,?)}");
            callableStatement.setInt(1, mjesto.getPostanskiBroj());
            callableStatement.setString(2, mjesto.getNaziv());
            callableStatement.setString(3, mjesto.getDrzava());

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

    public static void izmjeniMjesto(Mjesto mjesto) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_mjesto(?,?,?)}");
            callableStatement.setInt(1, mjesto.getPostanskiBroj());
            callableStatement.setString(2, mjesto.getNaziv());
            callableStatement.setString(3, mjesto.getDrzava());

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

    public static void ubaciUTabeluMjesta() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Mjesto mjesto;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from mjesto_info");
            while (rs.next()) {
                mjesto = new Mjesto(rs.getInt(1),
                        rs.getString(2), rs.getString(3));
                if (!mjestaObservaleList.contains(mjesto)) {
                    mjestaObservaleList.add(mjesto);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(MjestoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MjestoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static Mjesto pretraziMjesto(String naziv) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_mjesto(?)}");
            callableStatement.setString(1, naziv);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new Mjesto(rs.getInt("postanskibroj"), rs.getString("naziv"), rs.getString("drzava"));
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
