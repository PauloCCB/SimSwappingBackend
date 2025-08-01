package com.simswapping.service.impl;

import com.simswapping.dao.SimSwappingDao;
import com.simswapping.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simswapping.service.SimSwappingService;

import java.util.List;

@Service
public class SimSwappingServiceImpl implements SimSwappingService {

    @Autowired
    SimSwappingDao simSwappingDao;

    @Transactional
    @Override
    public Integer registerAccount(BodyAccount bodyAccount){
        return simSwappingDao.registerAccount(bodyAccount);
    }

    @Override
    public List<Usuario> login(BodyLogin bodyLogin) throws Exception{
        return simSwappingDao.login(bodyLogin);
    }

    @Override
    public Integer createOperation(BodyOperation bodyOperation) throws Exception{
        return simSwappingDao.createOperation(bodyOperation);
    }

    @Override
    public Usuario getDataUsuario(Integer idUsuario) throws Exception {
        return simSwappingDao.getDataUsuario(idUsuario);
    }

    @Override
    public Cuenta getCuentaByUsuario(Integer idUsuario) throws Exception {
        return simSwappingDao.getCuentaByUsuario(idUsuario);
    }

    @Override
    public Integer updateUserLocation(Integer idUsuario, double latitud, double longitud) throws Exception {
        return simSwappingDao.updateUserLocation(idUsuario,latitud,longitud);
    }

    @Override
    public List<Ubicaciones> getLocations(Integer idUsuario) throws Exception {
        return simSwappingDao.getLocations(idUsuario);
    }

    @Override
    public Integer registerLocation(Integer idUsuario, double latitud, double longitud) throws Exception {
        return simSwappingDao.registerLocation(idUsuario,latitud,longitud);
    }


}
