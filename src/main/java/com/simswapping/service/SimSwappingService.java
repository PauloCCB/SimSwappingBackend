package com.simswapping.service;


import com.simswapping.model.*;

import java.util.List;

public interface SimSwappingService {

    Integer registerAccount(BodyAccount bodyAccount);

    List<Usuario> login(BodyLogin bodyLogin) throws Exception;

    Integer createOperation(BodyOperation bodyOperation)throws Exception;

    Usuario getDataUsuario(Integer idUsuario) throws Exception;

    Cuenta getCuentaByUsuario(Integer idUsuario) throws Exception;

    Integer updateUserLocation(Integer idUsuario, double latitude, double longitude) throws Exception;
}
