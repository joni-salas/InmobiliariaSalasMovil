package com.example.inmobiliariajonathan.modelo;

import java.io.Serializable;

public class CambioClave implements Serializable {
    private String ClaveActual;
    private String ClaveNueva;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClaveActual() {
        return ClaveActual;
    }

    public void setClaveActual(String claveActual) {
        ClaveActual = claveActual;
    }

    public String getClaveNueva() {
        return ClaveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        ClaveNueva = claveNueva;
    }

    public CambioClave(String email, String claveNueva, String claveActual) {
        this.email = email;
        ClaveNueva = claveNueva;
        ClaveActual = claveActual;
    }

    public CambioClave() {
    }
}
