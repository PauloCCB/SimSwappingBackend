package service.impl;

import dao.RegisterDao;
import model.BodyAccount;
import model.ResponseAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.RegisterService;

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
