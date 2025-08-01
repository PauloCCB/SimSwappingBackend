package com.simswapping.dao;

import com.simswapping.model.*;

import java.util.List;

public interface SimSwappingDao {

    Integer registerAccount(BodyAccount bodyAccount); 

    List<Usuario> login(BodyLogin bodyLogin) throws Exception;

    Integer createOperation(BodyOperation bodyOperation) throws Exception;

    Usuario getDataUsuario(Integer idUsuario) throws Exception;

    Cuenta getCuentaByUsuario(Integer idUsuario) throws Exception;

    Integer updateUserLocation(Integer idUsuario, double latitud, double longitud) throws Exception;

    List<Ubicaciones> getLocations(Integer idUsuario) throws Exception;

    Integer registerLocation(Integer idUsuario, double latitud, double longitud) throws Exception;
}
