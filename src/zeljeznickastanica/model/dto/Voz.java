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
public class Voz {

    private String vozId;
    private String vrstaPogona;
    private String namjena;
    private Double sirinaKolosjeka;
    protected String tipVoza;
    private String naziv;
    private boolean status = true;

    public Voz() {

    }

    public Voz(String vozId, String vrstaPogona, String namjena, Double sirinaKolosjeka,String naziv,boolean status) {
        this.vozId = vozId;
        this.vrstaPogona = vrstaPogona;
        this.namjena = namjena;
        this.sirinaKolosjeka = sirinaKolosjeka;
        this.naziv = naziv;
        this.status = status;
    }
    

    public String getVozId() {
        return vozId;
    }

    public void setVozId(String vozId) {
        this.vozId = vozId;
    }

    public String getVrstaPogona() {
        return vrstaPogona;
    }

    public void setVrstaPogona(String vrstaPogona) {
        this.vrstaPogona = vrstaPogona;
    }

    public String getNamjena() {
        return namjena;
    }

    public void setNamjena(String namjena) {
        this.namjena = namjena;
    }

    public Double getSirinaKolosjeka() {
        return sirinaKolosjeka;
    }

    public void setSirinaKolosjeka(Double sirinaKolosjeka) {
        this.sirinaKolosjeka = sirinaKolosjeka;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Voz other = (Voz) obj;
        if (!Objects.equals(this.vozId, other.vozId)) {
            return false;
        }
        if (!Objects.equals(this.vrstaPogona, other.vrstaPogona)) {
            return false;
        }
        if (!Objects.equals(this.namjena, other.namjena)) {
            return false;
        }
        if (!Objects.equals(this.sirinaKolosjeka, other.sirinaKolosjeka)) {
            return false;
        }
        return true;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
    

    @Override
    public String toString() {
        return "Voz{"+ naziv + "vozId=" + vozId + ", vrstaPogona=" + vrstaPogona + ", namjena=" + namjena + ", sirinaKolosjeka=" + sirinaKolosjeka + '}';
    }

}
