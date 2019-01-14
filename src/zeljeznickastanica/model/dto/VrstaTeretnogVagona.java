/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeljeznickastanica.model.dto;

import java.util.Objects;

/**
 *
 * @author djord
 */
public class VrstaTeretnogVagona {

    private String tip;
    private String naziv;

    public VrstaTeretnogVagona() {

    }

    public VrstaTeretnogVagona(String tip, String naziv) {
        this.tip = tip;
        this.naziv = naziv;

    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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
        final VrstaTeretnogVagona other = (VrstaTeretnogVagona) obj;
        if (!Objects.equals(this.tip, other.tip)) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VrstaTeretnogVagona{" + "tip=" + tip + ", naziv=" + naziv + '}';
    }

}
