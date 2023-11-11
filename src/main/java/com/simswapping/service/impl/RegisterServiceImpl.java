package com.simswapping.service.impl;

import com.simswapping.dao.RegisterDao;
import com.simswapping.model.BodyAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.simswapping.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    RegisterDao registerDao;

    @Transactional
    @Override
    public Integer registerAccount(BodyAccount bodyAccount){
        return registerDao.registerAccount(bodyAccount);
    }

}
