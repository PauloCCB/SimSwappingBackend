package com.simswapping.model;

import java.util.List;

public class ResponseUbicaciones {

    private List<Ubicaciones> lstUbicaciones;
    private Boolean success;
    private String message;

    public List<Ubicaciones> getLstUbicaciones() {
        return lstUbicaciones;
    }

    public void setLstUbicaciones(List<Ubicaciones> lstUbicaciones) {
        this.lstUbicaciones = lstUbicaciones;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
