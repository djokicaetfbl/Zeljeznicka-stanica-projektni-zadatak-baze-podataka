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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import zeljeznickastanica.controller.VrstaTeretnogVagonaController;
import zeljeznickastanica.model.dto.VrstaTeretnogVagona;

/**
 *
 * @author djord
 */
public class VrstaTeretnogVagonaDAO {

    public static void ubaciUTabeluVTV() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        VrstaTeretnogVagona vtv;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from vtv_info");
            while (rs.next()) {
                vtv = new VrstaTeretnogVagona(rs.getString(1), rs.getString(2));

                if (!VrstaTeretnogVagonaController.vtvObservaleList.contains(vtv)) {
                    VrstaTeretnogVagonaController.vtvObservaleList.add(vtv);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(VrstaTeretnogVagonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().checkIn(connection);
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(VrstaTeretnogVagonaDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void dodajVTV(VrstaTeretnogVagona vtv) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call insert_into_tvt(?,?)}");
            callableStatement.setString(1, vtv.getTip());
            callableStatement.setString(2, vtv.getNaziv());

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

    public static void izmjeniVTV(VrstaTeretnogVagona vtv) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call update_tvt(?,?)}");
            callableStatement.setString(1, vtv.getTip());
            callableStatement.setString(2, vtv.getNaziv());

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

    // doradi kao niz
    //public static VrstaTeretnogVagona[] pretraziVTV(String naziv) {
    public static List<VrstaTeretnogVagona> pretraziVTV(String naziv) {
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        List<VrstaTeretnogVagona> vtvList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_vtv(?)}");
            callableStatement.setString(1, naziv);
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                //return 
                vtvList.add(new VrstaTeretnogVagona(rs.getString("tip"), rs.getString("naziv")));
            }
            return vtvList;

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
