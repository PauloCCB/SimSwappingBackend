package com.simswapping.service.impl;

import com.simswapping.dao.SimSwappingDao;
import com.simswapping.model.BodyAccount;
import com.simswapping.model.BodyLogin;
import com.simswapping.model.ResponseLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simswapping.service.SimSwappingService;

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
    public Integer login(BodyLogin bodyLogin) {
        return simSwappingDao.login(bodyLogin);
    }

}
