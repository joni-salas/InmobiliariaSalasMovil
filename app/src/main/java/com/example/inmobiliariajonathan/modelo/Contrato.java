package com.example.inmobiliariajonathan.modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private String fechaInicio;
    private String fechaFin;
    private String monto;
    private Inquilino inqui;
    private Inmueble inmu;

    public Contrato() {}
    public Contrato(int id, String fechaInicio, String fechaFin, String monto, Inquilino inqui, Inmueble inmu) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.monto = monto;
        this.inqui = inqui;
        this.inmu = inmu;
    }




    public int getIdContrato() {
        return id;
    }

    public void setIdContrato(int id) {
        this.id = id;
    }

    public String getFechaInicio() {
        String dia="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = dateFormat.parse(fechaInicio);

            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }

    public String getFechaFin() {
        String dia="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = dateFormat.parse(fechaFin);

            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMontoAlquiler() {
        return monto;
    }

    public void setMontoAlquiler(String monto) {
        this.monto = monto;
    }


    public Inquilino getInquilino() {
        return inqui;
    }

    public void setInquilino(Inquilino inqui) {
        this.inqui = inqui;
    }

    public Inmueble getInmueble() {
        return inmu;
    }

    public void setInmueble(Inmueble inmu) {
        this.inmu = inmu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
