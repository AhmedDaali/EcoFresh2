package com.example.ecofresh.modelo.entidad;

import java.util.HashMap;
import java.util.Map;

public class DireccionEnvio {

    private String calle, localidad, cp;

    Map<String, Object> direccionEnvio = new HashMap<>();

    public DireccionEnvio(String calle, String localidad, String cp) {
        this.calle = calle;
        this.localidad = localidad;
        this.cp = cp;
    }

    public DireccionEnvio() {
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Map<String, Object> getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(Map<String, Object> direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
}
