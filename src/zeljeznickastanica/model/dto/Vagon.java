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
public class Vagon {

    private String vagonId;

    public Vagon() {

    }

    public Vagon(String vagonId) {
        this.vagonId = vagonId;
    }

    public String getVagonId() {
        return vagonId;
    }

    public void setVagonId(String vagonId) {
        this.vagonId = vagonId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.vagonId);
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
        final Vagon other = (Vagon) obj;
        if (!Objects.equals(this.vagonId, other.vagonId)) {
            return false;
        }
        return true;
    }

}
