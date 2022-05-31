package com.example.inmobiliariajonathan.modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pago implements Serializable {

    private int id;
    private int nPago;
    private Contrato cont;
    private float importe;
    private String fecha;

    public Pago() {}

    public Pago(int id, int nPago, Contrato cont, float importe, String fecha) {
        this.id = id;
        this.nPago = nPago;
        this.cont = cont;
        this.importe = importe;
        this.fecha = fecha;
    }

    public int getIdPago() {
        return id;
    }

    public void setIdPago(int id) {
        this.id = id;
    }

    public int getNumero() {
        return nPago;
    }

    public void setNumero(int nPago) {
        this.nPago = nPago;
    }

    public Contrato getContrato() {
        return cont;
    }

    public void setContrato(Contrato cont) {
        this.cont = cont;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getFechaDePago() {
        String dia="";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = dateFormat.parse(fecha);

            dia = formato.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dia;
    }

    public void setFechaDePago(String fecha) {
        this.fecha = fecha;
    }
}
