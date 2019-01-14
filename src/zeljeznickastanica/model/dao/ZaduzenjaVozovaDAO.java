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
import zeljeznickastanica.controller.ZaduzenjaVozovaController;
import zeljeznickastanica.model.dto.ZaduzenjaVozova;

/**
 *
 * @author djord
 *///String jmbMasinovodje, String vozid, Date oD, Date dO)
public class ZaduzenjaVozovaDAO {

    public static void ubaciUTabeluZaduzenjaVozova() {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        ZaduzenjaVozova zv;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from zaduzenje_voza_info");
            while (rs.next()) {
                zv = new ZaduzenjaVozova(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(6), rs.getDate(4).toString(), rs.getDate(5).toString()); //,DO);
//, rs.getDate(5).toString());
                if (!ZaduzenjaVozovaController.zvObservaleList.contains(zv)) {
                    ZaduzenjaVozovaController.zvObservaleList.add(zv);
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

    public static void dodajZaduzenjeVoza(ZaduzenjaVozova zaduzenjaVozova) {

        Connection connection = null;
        CallableStatement callableStatement = null;
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call zaduzenje_voza(?,?,?,?)}");
            callableStatement.setString(1, zaduzenjaVozova.getJmbMasinovodje());
            callableStatement.setString(2, zaduzenjaVozova.getVozid());
            callableStatement.setString(3, zaduzenjaVozova.getOd());
            callableStatement.setString(4, zaduzenjaVozova.getDoo());

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

    public static List<ZaduzenjaVozova> pretraziZaduzenjaVozova(String jmb){
        Connection connection = null;
        CallableStatement callableStatement = null;
        ResultSet rs = null;
        List<ZaduzenjaVozova> zvList = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().checkOut();
            callableStatement = connection.prepareCall("{call pretrazi_zaduzenje_voza(?)}");
            callableStatement.setString(1, jmb);
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                //return 
                       zvList.add(new ZaduzenjaVozova(rs.getString("jmb"), rs.getString("ime"),rs.getString("prezime"),rs.getString("vozid")
                       ,rs.getString("od"),rs.getString("do")));                    
            }
            return zvList;

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
