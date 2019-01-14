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
import zeljeznickastanica.model.dto.Masina;

/**
 *
 * @author djord
 */
public class MasinaDAO {

    public static void ubaciUTabeluVozova() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        Masina masina;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from masina_info");
            while (rs.next()) {
                masina = new Masina(rs.getString("vozid"), rs.getString("vrstapogona"),
                        rs.getString("namjena"), rs.getDouble("sirinakolosjeka"), rs.getString("naziv"));
                if (!ZeljeznickaStanicaController.vozoviObservaleList.contains(masina)) {
                    ZeljeznickaStanicaController.vozoviObservaleList.add(masina);
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

    public static void dodajMasinu(Masina masina, String tipVoza) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_masina(?,?,?,?,?,?)}");
            callableStatement.setString(1, masina.getVozId());
            callableStatement.setString(2, masina.getVrstaPogona());
            callableStatement.setString(3, masina.getNamjena());
            callableStatement.setDouble(4, masina.getSirinaKolosjeka());
            callableStatement.setString(5, "Masina");
            callableStatement.setString(6, masina.getNaziv());

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

    public static void izmjeniMasinu(Masina masina) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_masina(?,?,?,?,?,?)}");
            callableStatement.setString(1, masina.getVozId());
            callableStatement.setString(2, masina.getVrstaPogona());
            callableStatement.setString(3, masina.getNamjena());
            callableStatement.setDouble(4, masina.getSirinaKolosjeka());
            callableStatement.setString(5, masina.getTipVoza());
            callableStatement.setString(6, masina.getNaziv());

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

    public static Masina pretraziVoz(String naziv) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_masinu(?)}");
            callableStatement.setString(1, naziv);
            rs = callableStatement.executeQuery();
            if (rs.next()) {
                return new Masina(rs.getString("vozid"), rs.getString("vrstapogona"), rs.getString("namjena"),
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
