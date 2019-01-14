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
public class Mjesto {

    private int postanskiBroj;
    private String naziv;
    private String drzava;

    public Mjesto(int postanskiBroj, String naziv, String drzava) {
        this.postanskiBroj = postanskiBroj;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public Mjesto() {
    }

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.postanskiBroj;
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
        final Mjesto other = (Mjesto) obj;
        if (this.postanskiBroj != other.postanskiBroj) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        if (!Objects.equals(this.drzava, other.drzava)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mjesto{" + "postanskiBroj=" + postanskiBroj + ", naziv=" + naziv + ", drzava=" + drzava + '}';
    }
    
    

}
