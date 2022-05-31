package com.example.inmobiliariajonathan.modelo;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {

    private int id;
    private String direccion;
    private String superficie;
    private String tipo;
    private String ambientes;
    private String precio;
    private Propietario propietario;
    //En falso significa que el innmueble no está disponible por alguna falla en el mismo.
    private String estado;
    private String imagen;
    private String imgGuardar;

    public Inmueble(int id, String direccion, String tipo, String ambientes, String precio, Propietario propietario, String estado, String imagen, String superficie, String imgGuardar) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.ambientes = ambientes;
        this.precio = precio;
        this.propietario = propietario;
        this.estado = estado;
        this.imagen = imagen;
        this.superficie = superficie;
        this.imgGuardar = imgGuardar;
    }
    public Inmueble() {

    }

    public String getImgGuardar() {
        return imgGuardar;
    }

    public void setImgGuardar(String imgGuardar) {
        this.imgGuardar = imgGuardar;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public int getIdInmueble() {
        return id;
    }

    public void setIdInmueble(int idInmueble) {
        this.id = idInmueble;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(String ambientes) {
        this.ambientes = ambientes;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public String isEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
