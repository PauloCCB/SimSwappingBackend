package com.simswapping.model;

import java.io.Serializable;

public class Operation implements Serializable {

    private String id_operation;
    private String id_usuario;
    private String monto;
    private String cuenta_destino;
    private String cuenta_origen;
    private String estado;

    public String getId_operation() {
        return id_operation;
    }

    public void setId_operation(String id_operation) {
        this.id_operation = id_operation;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getCuenta_destino() {
        return cuenta_destino;
    }

    public void setCuenta_destino(String cuenta_destino) {
        this.cuenta_destino = cuenta_destino;
    }

    public String getCuenta_origen() {
        return cuenta_origen;
    }

    public void setCuenta_origen(String cuenta_origen) {
        this.cuenta_origen = cuenta_origen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
