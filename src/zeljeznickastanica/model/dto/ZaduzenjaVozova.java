/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.time.LocalDate;

/**
 *
 * @author djord
 */
public class ZaduzenjaVozova {

    private String jmbMasinovodje;
    private String ime;
    private String prezime;
    private String vozid;
    //private Date od;
    //private Date dO;
    private String od;
    private String doo;

    public ZaduzenjaVozova() {

    }

    public ZaduzenjaVozova(String jmbMasinovodje, String ime, String prezime, String vozid, String od, String doo) {
        this.jmbMasinovodje = jmbMasinovodje;
        this.ime = ime;
        this.prezime = prezime;
        this.vozid = vozid;
        this.od = od;
        this.doo = doo;
    }

    public String getJmbMasinovodje() {
        return jmbMasinovodje;
    }

    public void setJmbMasinovodje(String jmbMasinovodje) {
        this.jmbMasinovodje = jmbMasinovodje;
    }

    public String getVozid() {
        return vozid;
    }

    public void setVozid(String vozid) {
        this.vozid = vozid;
    }

    /* public Date getoD() {
     return od;
     }

     public void setoD(Date oD) {
     this.od = od;
     }

     public Date getdO() {
     return dO;
     }

     public void setdO(Date dO) {
     this.dO = dO;
     }
     */
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZaduzenjaVozova other = (ZaduzenjaVozova) obj;
        if (!Objects.equals(this.jmbMasinovodje, other.jmbMasinovodje)) {
            return false;
        }
        if (!Objects.equals(this.vozid, other.vozid)) {
            return false;
        }
        if (!Objects.equals(this.od, other.od)) {
            return false;
        }
        if (!Objects.equals(this.doo, other.doo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ZaduzenjaVozova{" + "jmbMasinovodje=" + jmbMasinovodje + ", ime=" + ime + ", prezime=" + prezime + ", vozid=" + vozid + ", oD=" + od + ", dO=" + doo + '}';
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }

    public String getDoo() {
        return doo;
    }

    public void setDoo(String doo) {
        this.doo = doo;
    }

}
